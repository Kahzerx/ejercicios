# Creamos una base de datos MySQL.
docker run -d -p 3333:3306 --name db -e "MYSQL_ROOT_PASSWORD=1234" -e "MYSQL_DATABASE=docker" -e "MYSQL_USER=docker" -e "MYSQL_PASSWORD=4321" mysql:5.7
# Esperamos a que termine de iniciarse.
docker logs -f db
# Accedemos al servicio.
mysql -u root -h 127.0.0.1 -p1234 --port 3333 # Para probar que funcione.
# Exportamos una base de datos que ya esté creada.
mysqldump -u root -h 127.0.0.1 -p1234 --port 3333 sys > dump.sql # de sys por ejemplo
# La importamos a nuestra base de datos creada en nuestro container.
mysql -u root -h 127.0.0.1 -p1234 --port 3333 docker < dump.sql
# Accedemos a nuestra base de datos de forma normal.
mysql -u root -h 127.0.0.1 -p1234 --port 3333 docker
# El comando show tables; nos muestra lo que acabamos de importar.

# Al eliminar y crear el container de nuevo, habremos perdido todo :(
# Por ello vamos a crear un volume para hacer que nuestra base de datos sea persistente.

# Creamos el container especificando el volume
docker run -d -p 3333:3306 --name db -e "MYSQL_ROOT_PASSWORD=1234" -e "MYSQL_DATABASE=docker" -v $PWD/mysqlstuff/:/var/lib/mysql mysql:5.7

mysql -u root -h 127.0.0.1 -p1234 --port 3333
CREATE DATABASE test1;
# Ahora al cerrar y recrear un container usará lo almacenado en la carpeta especificada con -v
# Es como tener una carpeta compartida que el container no es capaz de eliminar.

# ---

# El arg del volume tiene esta estructura:
# -v $PWD/mysqlstuff/:/var/lib/mysql
# si no especificamos ruta local, docker creará una en la ruta default de docker, en la carpeta ya creada de volumes
# -v /var/lib/mysql
# Para ver la ruta principal de docker usar el comando:
docker info | grep -i root

# ---

# Existe un tercer tipo, los named volumes, estos son una combinación de los 2 anteriores.
# primero creas el volume con el comando:
docker volume create mysql-data
# Para mapear el nuevo volume a un container simplemente:
# -v mysql-data:/var/lib/mysql <- Es por esto que no se pueden poner paths relativas, solo absolutas al crear host volume.
# enlazamos el nombre del volume que acabamos de crear con el directorio que queremos guardar.