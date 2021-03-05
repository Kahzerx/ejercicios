# Sobre qué se va a ejecutar el docker.
FROM centos

# Instalamos apache.
RUN yum -y install httpd

# Si se ejecuta lo anterior, docker iniciará y morirá al momento ya que no tiene nada que hacer.
# Tenemos que definir un CMD para que funcione. Ha de haber un proceso attached a la consola, en primer plano.

# Ejecutamos el servicio de apache en primer plano.
CMD apachectl -DFOREGROUND
# Obviamente hay que hacer el build de nuevo para renovar la imagen tras hacer cambios.