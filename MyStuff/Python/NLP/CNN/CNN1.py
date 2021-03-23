import numpy as np
import math
import re
import pandas as pd
from bs4 import BeautifulSoup

import tensorflow as tf
from tensorflow.keras import layers
import tensorflow_datasets as tfds

# Usar el csv de aquí para el entrenamiento: http://help.sentiment140.com/for-students

# Cargamos el csv de entrenamiento y test, los csv no tienen
# header así que los cargamos a mano para que no identifique
# la primera row como header.

cols = ["sentiment", "id", "date", "query", "user", "text"]

train_data = pd.read_csv(
    "/home/kahzerx/Documents/ejercicios/MyStuff/Python/NLP/training/training.csv",
    header=None,
    names=cols,
    engine="python",
    encoding="latin1"
)

test_data = pd.read_csv(
    "/home/kahzerx/Documents/ejercicios/MyStuff/Python/NLP/training/testing.csv",
    header=None,
    names=cols,
    engine="python",
    encoding="latin1"
)


class DCNN(tf.keras.Model):
    def __init__(self,
                 vocab_size,  # Tamaño del vocabulario (nos lo da el tokenizer).
                 emb_dim=128,  # Espacio vectorial (128 números).
                 nb_filters=50,  # Número de filtros de cada tipo (teníamos filtros de 2 3 y 4, pues 50 de cada).
                 ffn_units=512,  # Neuronas de la capa oculta.
                 nb_classes=2,  # 2 categorías de clasificación (porque el tweet es positivo o negativo).
                 dropout_rate=0.1,  # Desactivar ciertas neuronas para evitar over-fitting.
                 training=False,  # toggle para saber si tiene que entrenar o predecir.
                 name="dcnn"):  # IDK un nombre.
        super(DCNN, self).__init__(name=name)

        # Transformación a nuestro espacio vectorial definido.
        self.embedding = layers.Embedding(vocab_size, emb_dim)

        # Definimos los filtros:
        # Filtros que analizan 2 palabras
        self.bigram = layers.Conv1D(filters=nb_filters, kernel_size=2, padding="valid", activation="relu")
        # Filtros que analizan 3 palabras
        self.trigram = layers.Conv1D(filters=nb_filters, kernel_size=3, padding="valid", activation="relu")
        # Filtros que analizan 4 palabras
        self.fourgram = layers.Conv1D(filters=nb_filters, kernel_size=4, padding="valid", activation="relu")


def progress_bar(progress, progress_max):
    max_length = 70
    filled = int(round(max_length * progress / float(progress_max)))
    percentage = round(100.0 * progress / float(progress_max), 1)

    print(f"[{'=' * filled}{'-' * (max_length - filled)}] {percentage}%", end="\r")


def csv_cleanup():
    """
    Eliminar las columnas con información inútil y eliminar todos los caracteres especiales.
    """
    train_data.drop(["id", "date", "query", "user"], axis=1, inplace=True)

    n_lines = len(train_data)
    clean_data = []

    for index, tweet in enumerate(train_data.text):
        clean_data.append(tweet_cleanup(tweet))
        progress_bar(index, n_lines)

    return clean_data


def tweet_cleanup(tweet):
    """
    Limpieza de caracteres.

    :param tweet: Cada texto del tweet.
    """
    tweet = BeautifulSoup(tweet, "lxml").get_text()
    # Eliminamos mención.
    tweet = re.sub(r"@[A-Za-z0-9]+", " ", tweet)
    # Eliminamos links de URLs.
    tweet = re.sub(r"https?://[A-Za-z0-9./]+", " ", tweet)
    # Limpiamos lo que no sean caracteres.
    tweet = re.sub(r"[^a-zA-Z.!?']", " ", tweet)
    # Eliminamos espacios.
    tweet = re.sub(r" +", " ", tweet)
    return tweet.strip()


if __name__ == "__main__":
    print("Starting preprocessing.")
    print("Starting CSV cleanup.")
    data = csv_cleanup()
    print("\nCSV cleanup finished.")

    # Transformamos las frases con palabras a listas de números con cada número referenciando a una palabra
    # de forma única.
    print("Starting tokenizer.")
    # Establecemos también un máximo de vocabulario único de 2^16.
    # Esto encontrará las 65536 palabras más comunes, haciéndolo más eficiente, anulando posibles typos y relacionando
    # palabras más comunes.
    tokenizer = tfds.deprecated.text.SubwordTextEncoder.build_from_corpus(data, target_vocab_size=2**16)
    lines = len(data)
    data_inputs = []
    for i, sentence in enumerate(data):
        data_inputs.append(tokenizer.encode(sentence))
        progress_bar(i, lines)
    print("\nTokenizer finished.")

    print("Starting padding.")
    # Sacamos la length más larga ya que todas deben tener la misma longitud.
    # El entrenamiento se hace por bloques por lo que tienen que formar un rectángulo.
    # Cada elemento de la array final resulta en arrays de números de igual length, y con 0 al final de aquellas
    # frases que no tuvieran palabras suficientes para llegar al max_len.
    MAX_LEN = max([len(sentence) for sentence in data_inputs])
    # Para compensar añadimos 0 al final de las que no lleguen a la max_len.
    # Añadimos 0 ya que es un valor que el tokenizer NO utiliza, es un valor que no corresponde con ninguna palabra.
    data_inputs = tf.keras.preprocessing.sequence.pad_sequences(data_inputs, value=0, padding="post", maxlen=MAX_LEN)
    # Insertamos 0 al "post" (final) hasta alcanzar la max_len.
    print("Padding finished.")

    # Ahora tenemos que dividir por bloques para evitar over-fitting y hacerlo más eficiente en comparación
    # al entrenamiento de elemento a elemento, evitando así que aprenda demasiado de 1 determinada frase.

    # En nuestro dataset la primera mitad son tweets negativos y la segunda mitad son positivos... por lo que
    # voy a sacar 8000 de la primera mitad y otros 8000 de la segunda mitad.

    # Elijo 8000 números random entre 0 y 800_000 (estos son los tweets negativos)
    test_idx = np.random.randint(0, 800_000, 8000)
    # Como ya tengo 8000 ids entre 0 y 800_000, y ahora necesito los de la siguiente mitad, tomo los primeros
    # y les sumo 800_000 para que corresponda con la siguiente mitad.
    test_idx = np.concatenate((test_idx, test_idx + 800_000))

    # Por algún motivo tenemos los valores 0 y 4, lo paso a 0 y 1 por comodidad.
    data_labels = train_data.sentiment.values
    data_labels[data_labels == 4] = 1

    # Creamos las listas que se usarán para test cuando la red esté entrenada. 8000 sobre 800_000, el 1%.
    # Creamos las listas de textos tokenized y padded con la lista random de valores.
    test_inputs = data_inputs[test_idx]
    # Lo mismo pero con las labels de 0 y 1 para saber el resultado.
    test_labels = data_labels[test_idx]

    # Creamos las listas que se usarán para el entrenamiento.
    # Eliminamos las que vamos a usar para testing y dejamos el resto, el 99%.
    train_inputs = np.delete(data_inputs, test_idx, axis=0)
    train_labels = np.delete(data_labels, test_idx)
    print("preprocessing finished.")
