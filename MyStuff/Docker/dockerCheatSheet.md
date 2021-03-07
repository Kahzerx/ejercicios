## Lista de comandos de docker para la terminal

---

> ***Tag***: las **_tag_** son como branches de github, se suelen usar para tener otras versiones del mismo programa.

> Puedes ver el nombre de los containers con el comando `docker ps`.

> Diferenciar entre ***nombreImagen***, y ***nombreContainer***, los containers son instancias de las imágenes, puede haber varios containers de la misma imagen.

> Recordar que tras cada cambio que se haga sobre el dockerfile, quieres hacer el build de nuevo para aplicar los cambios sobre la imagen, para poder hacer el `docker run` con los nuevos cambios.

> Al crear la imagen, docker envia todo el contenido del directorio al builder.

> Para evitar esto, creamos un archivo .dockerignore en el que especificaremos qué archivos ignorar. Funciona igual que el .gitignore, no necesita escribir guia, simplemente escribes lo que no quieres dentro del archivo, ya sean directorios, archivos...

> Las capas de las imágenes son de solo lectura, por lo que al generar una imagen con el mismo nombre y tag pero con algún cambio en alguna capa que una ya previamente creada, esta será desreferenciada para ser reemplazada por la nueva, dejando a la antigua huérfana, es por esto que al ejecutar `docker images` vemos algunas imágenes con \<none\>, esto es conocido como `dangling images` y se puede solucionar con las **_tags_**.

> Puede haber varios FROM dentro de un Dockerfile como podemos ver en ./multi-stage, el último FROM siempre será el válido, todos los anteriores serán olvidados por la imagen final.

> docker rm -fv $(docker ps -aq) Elimina todo lo que haya en `docker ps`.
---

- docker
    - build
        - -t
            - **_nombreImagen_**
                - . (o ruta)
                    - Crea la imagen con el **_nombreImagen_** especificado tomando el `dockerfile` en esa ruta.
            - **_nombreImagen_**
                - -f
                    - **_filename_**
                        - . (o ruta)
                            - Crea la imagen con el **_nombreImagen_** especificado tomando el `dockerfile` en esa ruta y tomando como referencia el archivo especificado en -f en reemplazo del default `Dockerfile`.
            - **_nombreImagen_**:**_tag_**
                - . (o ruta)
                    - Crea la imágen mencionada previamente pero con otra **_tag_** name.
    - ps
        - Lista todos los containers que docker está ejecutando.
        - -a
            - Lista todos los containers incluyendo los que han muerto.
        - --no-trunc
            - Al listar el contenido puede estar recortado para que quede mejor en la consola, este arg muestra el contenido completo.
    - rm
        - -fv
            - **_nombreContainer_** or **_idContainer_**
                - Elimina el container con el nombre especificado (debe ser el nombre o ID que aparezca en `docker ps`)
    - images
        - Lista todas las imágenes que tenemos instaladas en nuestro pc.
        - -f
            - dangling=true
                - Buscar entre las imágenes aplicando el filtro de que solo aparezcan las imágenes huérfanas.
    - image
        - rm
            - **_image\_id_**
                - Elimina la imagen con el ID especificado, podemos sacar los IDs del comando `docker images`.
        - prune
            - Elimina todas las imágenes que no están asociadas con nada, que son versiones antiguas (dangling).
            - -a
                - Elimina tanto las imágenes no asociadas a nada como las imágenes que no están siendo usadas.
    - history
        - -H
        - **_nombreImagen_**:**_tag_**
            - Visualizar las capas de nuestra imagen.
        - --no-trunc
            - Al listar el contenido puede estar recortado para que quede mejor en la consola, este arg muestra el contenido completo.
    - run
        - -d(ti?) (ti puede dejar el OS ejecutándose para que podamos acceder.)
            - -e
                - "key=value"
                    - Declarar variables de entorno al crear container.
            - --name **_nombreContainer_**
                - Aplicar **_nombreImagen_** con el nombre especificado.
                se aplicará un nombre random si no se aplica este arg que puedes ver con el comando `docker ps`.
            - -p (puerto de tu máquina):(puerto del docker) | (80:80)
                - Vinculando el puerto del docker con el puerto de tu máquina, esto funciona igual que abrir puertos en tu router.
            - **_nombreImagen_**
                - Crear y ejecutar un container (o instancia) de **_nombreImagen_**.
    - logs
        - -f
            - **_nombreContainer_**
                - Mostrar todos los logs generados por el proceso "attached" al container, es decir, por el proceso ejecutado por `CMD` que mantiene vivo el container.
    - rename
        - **_nombreContainerAntiguo_**
            - **_nombreContainerNuevo_**
                - Renombrar un docker ya en ejecución.
    - stop
        - **_nombreContainer_** or **_idContainer_**
            - Detener el container con ese id o nombre.
    - start
        - **_nombreContainer_** or **_idContainer_**
            - Iniciar el container con ese id o nombre.
    - restart
        - **_nombreContainer_** or **_idContainer_**
            - Reiniciar el container con ese id o nombre.
    - exec
        - -u
            - **_userName_**
                - Usar el user especificado.
        - -ti
            - terminal interactiva
        - **_nombreContainer_** or **_idContainer_**
            - bash
                - Entrar en la consola del contenedor.
    - pull
        - **_nombreImagen_**:**_tag_**
            - Descargar desde el repositorio de docker la imagen seleccionada.
    - inspect
        - **_nombreContainer_** or **_idContainer_**
            - información adicional sobre nuestro contenedor como dirección IP, variables de entorno, etc.
    - stats
        - **_nombreContainer_** or **_idContainer_**
            - Recursos que está usando nuestro contenedor.