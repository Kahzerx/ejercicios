docker run -d --name db1 -e "MYSQL_ROOT_PASSWORD=1234" mysql:5.7

docker logs -f db1

docker ps -f name=db1 -q

docker inspect db1

# Extraemos la IPAddress.
# Esto es para acceder si abrir puertos por medio de la IP creada.
mysql -u root -h 172.17.0.2 -p1234

# Para mayor comodidad, abrir el puerto.
# En la documentación de MySQL podemos encontrar todas estas variables de entorno que se pueden usar al crear el container.
docker run -d -p 3333:3306 --name db1 -e "MYSQL_ROOT_PASSWORD=1234" -e "MYSQL_DATABASE=docker" -e "MYSQL_USER=docker" -e "MYSQL_PASSWORD=4321" mysql:5.7

mysql -u docker -p4321 -h 127.0.0.1 --port 3333

docker rm -fv $(docker ps -aq)