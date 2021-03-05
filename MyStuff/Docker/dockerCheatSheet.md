## Lista de comandos de docker para la terminal

---

> ***Tag***: las **_tag_** son como branches de github, se suelen usar para tener otras versiones del mismo programa.

> Puedes ver el nombre de los containers con el comando `docker ps`.

> Diferenciar entre ***nombreImagen***, y ***nombreContainer***, los containers son instancias de las imágenes, puede haber varios containers de la misma imagen.

> Recordar que tras cada cambio que se haga sobre el dockerfile, quieres hacer el build de nuevo para aplicar los cambios sobre la imagen, para poder hacer el `docker run` con los nuevos cambios.

> Al crear la imagen, docker envia todo el contenido del directorio al builder.

> Para evitar esto, creamos un archivo .dockerignore en el que especificaremos qué archivos ignorar. Funciona igual que el .gitignore, no necesita escribir guia, simplemente escribes lo que no quieres dentro del archivo, ya sean directorios, archivos...

---

- docker
    - build
        - -t
            - **_nombreImagen_**
                - . (o ruta)
                    - Crea la imagen con el **_nombreImagen_** especificado tomando el `dockerfile` en esa ruta.
            - **_nombreImagen_**:**_tag_**
                - . (o ruta)
                    - Crea la imágen mencionada previamente pero con otra **_tag_** name.
    - [^ps]: ps
        - Lista todos los containers que docker está ejecutando.
        - -a
            - Lista todos los containers incluyendo los que han muerto.
        - --no-trunc
            - Al listar el contenido puede estar recortado para que quede mejor en la consola, este arg muestra el contenido completo.
    - rm
        - -fv
            - **_nombreContainer_**
                - Elimina el container con el **_nombreContainer_** especificado (debe ser el **_nombreContainer_** que aparezca en `docker ps`[^ps])
    - images
        - Lista todas las imágenes que tenemos instaladas en nuestro pc.
    - history
        - -H
            - **_nombreImagen_**:**_tag_**
                - Visualizar las capas de nuestra imagen
    - run
        - -d
            - **_nombreImagen_**
                - Crear y ejecutar un container (o instancia) de **_nombreImagen_**, se aplicará un nombre random que puedes ver con el comando `docker ps`.
            - --name **_nombreContainer_**
                - **_nombreImagen_**
                    - Crear y ejecutar un container (o instancia) de **_nombreImagen_** con el nombre especificado.
                - -p (puerto de tu máquina):(puerto del docker) | (80:80)
                    - **_nombreImagen_**
                        - Crear y ejecutar un container (o instancia) de **_nombreImagen_** con el nombre especificado, vinculando el puerto del docker con el puerto de tu máquina, esto funciona igual que abrir puertos en tu router.
    - logs
        - f
            - **_nombreContainer_**
                - Mostrar todos los logs generados por el proceso "attached" al container, es decir, por el proceso ejecutado por `CMD` que mantiene vivo el container.