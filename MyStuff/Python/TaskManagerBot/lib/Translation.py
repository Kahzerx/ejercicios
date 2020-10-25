from googletrans import Translator
import random

translator = Translator()

languages = ['es', 'fr', 'en', 'de', 'it', 'pl', 'pt']


def getModified(content):
    random.shuffle(languages)
    sourceLang = translator.translate(content, dest='es').src
    for lang in languages:
        content = translator.translate(content, dest=lang).text
    return translator.translate(content, dest=sourceLang).text
