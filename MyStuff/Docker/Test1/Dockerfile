# docker build -t apache-centos . | para crear la imagen.
# docker build -t apache-centos:first . | creará la misma imagen con otra tag name.

# Sobre qué se va a ejecutar el docker.
FROM centos

# Instalamos apache.
RUN yum -y install httpd

# docker ps -a | para saber los procesos ejecutándose desde docker.
# docker rm -fv <name> | para eliminar.

# docker images | para saber qué imágenes tenemos instaladas en nuestro pc.

# docker history -H apache-centos:latest | para ver las capas de la imagen.

# docker run -d --name apache apache-centos | ejecutar la imagen
# Si se ejecuta lo anterior, docker iniciará y morirá al momento ya que no tiene nada que hacer.
# Tenemos que definir un CMD para que funcione. Ha de haber un proceso attached a la consola, en primer plano.

# Ejecutamos el servicio de apache en primer plano.
CMD apachectl -DFOREGROUND
# Obviamente hay que hacer el build de nuevo para renovar la imagen tras hacer cambios.

# docker run -d --name apache -p 80:80 apache-centos | esto conecta el puerto 80 del docker con el puerto 80 de nuestra máquina, en este caso para ver apache ejecutándose en nuestro navegador.
