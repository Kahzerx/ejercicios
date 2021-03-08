## COMANDOS PARA EL DOCKERFILE

- CMD
    - Comando para ejecutar un servicio en primer plano.
    - El servicio tiene que mantenerse vivo y attached al primer plano, de otra forma, el container morirá.
    - Si el proceso finaliza, el container también morirá.
    - Es lo que se encarga de mantener con vida el container.

        ```docker
        # Iniciamos el servicio apache en primer plano.
        CMD apachectl -DFOREGROUND
        ```
    - De igual forma puede ser un script.

        ```docker
        # Copiamos el archivo run.sh a la imagen.
        COPY run.sh /run.sh
        # Ejecutamos el script.
        CMD sh /run.sh
        ```

- COPY/ADD
    - Copiar archivos desde nuestra máquina a la imagen.

        ```docker
        # Copiamos cualquier archivo local
        # a la imagen que estamos creando.
        COPY myweb html
        # La ruta introducida es la default en centos que ejecutará apache.
        # Recordar que previamente ya nos habíamos situado en
        # /var/www con el comando WORKDIR.
        # La ruta completa de la imagen sería /var/www/html.
        ```
    - Puede ser tanto archivos como directorios.
    - La principal diferencia entre `COPY` y `ADD` es que `COPY` debería usarse para **copiar** archivos locales de tu máquina a la imagen, mientras que `ADD` sirve para reemplazar esa ruta local por una **URL**, `ADD` se encarga de **descargar** el contenido de esa **URL** y pegarla en la ruta de la imagen (Aunque también cumple la función de `COPY`).

- ENV
    - Variables de entorno.

        ```docker
        # Creamos la variable contenido con su valor prueba.
        ENV contenido prueba
        # Para demostrar su funcionamiento podemos ejecutar un echo desde RUN
        # Y escribir el contenido en un archivo.
        RUN echo "$contenido" > /var/www/html/prueba.html
        ```

- EXPOSE
    - Ejecutar en un puerto distinto al default.
        
        ```docker
        # Indicamos que el container debe escuchar en el puerto 8080.
        EXPOSE 8080
        # Para que funcione correctamente habría que configurar apache
        # de forma que sepa escuchar en el puerto 8080.
        ```

- FROM
    - Normalmente el dockerfile inicia con esto.
    - Especificamos qué sistema operativo queremos para nuestra imagen, o incluso especificar una imagen misma desde la que queramos iniciar.

        ```docker
        # Esto descarga la última versión de centos.
        FROM centos
        ```
    - Para definir otra versión de `centos`, especificar una tag de las que salgan aquí: https://hub.docker.com/_/centos/, de forma que si queremos centos 7, escribiremos `FROM centos:7`

- LABEL
    - LABEL puede ir en cualquier parte del docker file, normalmente va al principio.
    - Suele servir para dar metadatos a la imagen que estamos creando.

        ```docker
        LABEL version=1.0
        # Usar "" cuando hay espacios para evitar problemas.
        LABEL description="This is an apache image"
        ```

- RUN
    - Instrucciones que se pueden ejecutar en la terminal. (cualquier comando que se pueda ejecutar en linux).

        ```docker
        # Instalamos httpd con -y, siempre comandos que
        # no requieran confirmación humana.
        RUN yum install httpd -y
        ```

- USER
    - Especificar el usuario que ejecuta las tareas.

        ```docker
        # Para saber quién es el usuario actual, por defecto es root.
        RUN echo "$(whoami)" > /var/www/html/user1.html
        # Añadimos nuestro usuario.
        RUN useradd kahzerx
        # Hacemos que a partir de ahora, el usuario que ejecuta las tareas
        # sea el especificado con el comando USER.
        USER kahzerx
        # Todo lo anterior habrá sido ejecutado por root.
        RUN echo "$(whoami)" > /tmp/user2.html
        # Volvemos al usuario root.
        USER root
        # Movemos el archivo creado por el usuario anterior a la web.
        RUN cp /tmp/user2.html /var/www/html/user2.html
        # Esto lo hacemos para evitar problemas de permisos de escritura
        # del nuevo usuario.
        # Hay 1000 formas de hacer esto.
        ```

- VOLUME
    - Hacer que cuando el contenedor se elimine o muera, los datos guardados aquí se queden almacenados en nuestra máquina y no se pierdan.

        ```docker
        # Se guardará lo especificado en esa ruta.
        VOLUME /var/www/html
        ```

- WORKDIR
    - Especificar la ruta en la que queremos que nuestro docker esté situado para trabajar.
    
        ```docker
        WORKDIR /var/www
        ```
