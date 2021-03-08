## Lista de comandos de docker para la terminal

---

> ***Tag***: las **_tag_** son como branches de github, se suelen usar para tener otras versiones del mismo programa.

> Puedes ver el nombre de los containers con el comando `docker ps`.

> Diferenciar entre ***nombreImagen***, y ***nombreContainer***, los containers son instancias de las imágenes, puede haber varios containers de la misma imagen.

> Recordar que tras cada cambio que se haga sobre el dockerfile, quieres hacer el build de nuevo para aplicar los cambios sobre la imagen, para poder hacer el `docker run` con los nuevos cambios.

> Al crear la imagen, docker envia todo el contenido del directorio al builder.

> Para evitar esto, creamos un archivo .dockerignore en el que especificaremos qué archivos ignorar. Funciona igual que el .gitignore, no necesita escribir guia, simplemente escribes lo que no quieres dentro del archivo, ya sean directorios, archivos...

> Las capas de las imágenes son de solo lectura, por lo que al generar una imagen con el mismo nombre y tag pero con algún cambio en alguna capa que una ya previamente creada, esta será desreferenciada para ser reemplazada por la nueva, dejando a la antigua huérfana, es por esto que al ejecutar `docker images` vemos algunas imágenes con \<none\>, esto es conocido como `dangling images` y se puede solucionar con las **_tags_**.

> Al igual existen `dangling volumes`.

> Puede haber varios FROM dentro de un Dockerfile como podemos ver en ./multi-stage, el último FROM siempre será el válido, todos los anteriores serán olvidados por la imagen final.

> docker rm -fv $(docker ps -aq) elimina todo lo que haya en `docker ps`.

> Cambiar el Document root de docker: `nano /lib/systemd/system/docker.service` y modificar la línea `ExecStart=/usr/bin/dockerd` añadiendo al final `--data-root /opt/docker`, por lo que ahora nuestra instalación de docker estará en `/opt/docker`, ahora recargamos el daemon con el comando `systemctl daemon-reload` y reiniciamos docker `systemctl restart docker`. Para restaurar nuestra información anterior, simplemente movemos los archivos `mv /var/lib/docker /opt/`.

---

- docker
    - build
        - -t
            - **_nombreImagen_**:**_tag_**
                - -f
                    - **_filename_**
                        - Usar el Dockerfile que especificamos con este arg, si no se usa, por defecto usará el archivo `Dockerfile`.
                - . (o ruta)
                    - Crea la imagen con el **_nombreImagen_** especificado a partir del `Dockerfile`.
    - commit
        - **_nombreContainer_** or **_idContainer_**
            - **_nombreImagenResultante_**
                - Esto va a crear una imagen con el estado actual del container que se está ejecutando, de esta forma podemos guardar los cambios realizados, ya que los containers son totalmente temporales, esto crea una imagen a partir de un container, guardará todo a excepción de lo guardado dentro de un volume.
    - cp
        - **_localPath_**
            - **_nombreContainer_**:**_containerPath_**
                - Copia lo seleccionado en local hacia la ruta introducida del container `docker cp index.html apache:/tmp/index.html`.
        - **_nombreContainer_**:**_containerPath_**
            - **_localPath_**
                - Copia lo seleccionado desde dentro del container hacia nuestra máquina `docker cp apache:/tmp/index.html .`.
    - exec
        - -u
            - **_userName_**
                - Usar el user especificado para loguear.
        - -ti
            - terminal interactiva
        - **_nombreContainer_** or **_idContainer_**
            - bash
                - Entrar en la consola del contenedor.
        - -c "\<comando\>"
            - puedes ejecutar comandos desde tu máquina al container sin acceder a ella con el arg `-ti` de esta forma.
    - history
        - -H
        - **_nombreImagen_**:**_tag_**
            - Visualizar las capas de nuestra imagen.
        - --no-trunc
            - Al listar el contenido puede estar recortado para que quede mejor en la consola, este arg muestra el contenido completo.
    - image
        - rm
            - **_image\_id_**
                - Elimina la imagen con el ID especificado, podemos sacar los IDs del comando `docker images`.
        - prune
            - Elimina todas las imágenes que no están asociadas con nada, que son versiones antiguas (dangling).
            - -a
                - Elimina tanto las imágenes no asociadas a nada como las imágenes que no están siendo usadas.
    - images
        - Lista todas las imágenes que tenemos instaladas en nuestro pc.
        - -f
            - dangling=true
                - Buscar entre las imágenes aplicando el filtro de que solo aparezcan las imágenes huérfanas.
    - inspect
        - **_nombreContainer_** or **_idContainer_**
            - información adicional sobre nuestro contenedor como dirección IP, variables de entorno, etc.
    - logs
        - -f
            - **_nombreContainer_**
                - Mostrar todos los logs generados por el proceso "attached" al container, es decir, por el proceso ejecutado por `CMD` que mantiene vivo el container.
    - ps
        - Lista todos los containers que docker está ejecutando.
        - -l
            - Lista el último contenedor creado.
        - -a
            - Lista todos los containers incluyendo los que han muerto.
        - --no-trunc
            - Al listar el contenido puede estar recortado para que quede mejor en la consola, este arg muestra el contenido completo.
    - pull
        - **_nombreImagen_**:**_tag_**
            - Descargar desde el repositorio de docker la imagen seleccionada.
    - rename
        - **_nombreContainerAntiguo_**
            - **_nombreContainerNuevo_**
                - Renombrar un docker ya en ejecución.
    - restart
        - **_nombreContainer_** or **_idContainer_**
            - Reiniciar el container con ese id o nombre.
    - rm
        - -fv
            - **_nombreContainer_** or **_idContainer_**
                - Elimina el container con el nombre especificado (debe ser el nombre o ID que aparezca en `docker ps`)
    - run
        - --rm (reemplazar esto por -d)
            - Indicas que tras salir del contenedor, quieres que este se autodestruya. Al ejecutar el comando entra en el container automáticamente como si hicieramos un exec, al hacer `exit` se eliminará.
        - -d(ti?) (ti puede dejar el OS ejecutándose para que podamos acceder).
            - -m
                - "500mb"
                    - El container tendrá un límite de 500MB de ram asociada, también puedes usar gb...
            - --cpuset-cpus
                - 0-1
                    - Ceder al container la cpu 0 y la cpu 1. Para ver cuántas cpus tienes usa el comando `grep "model name" /proc/cpuinfo | wc -l`.
            - -e
                - "key=value"
                    - Declarar variables de entorno al crear container.
            - --name **_nombreContainer_**
                - Aplicar **_nombreImagen_** con el nombre especificado.
                se aplicará un nombre random si no se aplica este arg que puedes ver con el comando `docker ps`.
            - -p (puerto de tu máquina):(puerto del docker) | (80:80)
                - Vinculando el puerto del docker con el puerto de tu máquina, esto funciona igual que abrir puertos en tu router.
            - -v
                - **_AbsoluteLocalDir_**:**_containerDir_**
                    - Crea un volume en la ruta local especificada para almacenar los datos del directorio del container indicado en local para así añadir persistencia, esto es un volume de host. NO se elimina el volume al hacer rm -f**v**.
                - **_containerDir_**
                    - También puedes especificar la ruta del container que quieres hacer persistente pero quieres hacer un volume anonimus, en este caso docker creará una carpeta dentro del directorio `volumes` dentro del Docker Root `(docker info | grep -i root)`.
                    - Importante no usar docker rm -f**v**, ya que la v lo que hace es eliminar el volume anonimo asociado, si nos interesa conservar los datos, usar rm -f.
                    - Los volumes anonimos no son muy recomendables, el nombre que se les asocia está basado en el hash, que es único en cada container por lo que crear otro container con el mismo comando no restaurará los datos, simplemente creará otra carpeta, aunque los datos son guardados así que si queremos restaurar esos datos en otro container tendríamos que usar este nombre(estaría bien renombrar) y asociarlo en la creación de otro docker convirtiendolo así en un volume de host.
                - **_nombreVolume_**:**_containerDir_**
                    - Enlaza un named volume que habremos creado previamente con la ruta del contenedor que nos interesa hacer persistente, es un término medio entre los volumes de host y un anonimus volume. NO se elimina el volume al hacer rm -f**v**.
            - **_nombreImagen_**
                - Crear y ejecutar un container (o instancia) de **_nombreImagen_**.
            - Todo lo posterior a **_nombreImagen_** será considerado argumento que sobreescribe a CMD, por ejemplo, si nuestra imagen es centos, y después escribimos `echo hola` el contenedor escribirá eso en la consola en lugar del `/bin/bash` que tiene por defecto. `docker run -dti centos echo hola`, util para cosas como `docker run -d -p 8080:8080 centos python -m SimpleHTTPServer 8080`
    - start
        - **_nombreContainer_** or **_idContainer_**
            - Iniciar el container con ese id o nombre.
    - stats
        - **_nombreContainer_** or **_idContainer_**
            - Recursos que está usando nuestro contenedor.
    - stop
        - **_nombreContainer_** or **_idContainer_**
            - Detener el container con ese id o nombre.
    - volume
        - ls
            - Lista todos los volumes creados (por lo general, los anonimos, los VOLUME en los Dockerfile son anonimos).
        - prune
            - Elimina todos los volumes que no están siendo usados por un container.
        - create
            - **_nombreVolume_**
                - Crea un volume con el nombre especificado, esto se usa para los named volumes.
        - -f
            - dangling=true
                - Buscar entre los volumes aplicando el filtro de que solo aparezcan los volumes huérfanos.