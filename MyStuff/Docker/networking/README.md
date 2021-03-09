## Docker network

---

Al instalar docker, se nos creará una nueva interfaz de red la cual podemos comprobar mediante el comando `ip a | grep docker`.
En mi caso me aparece esto `inet 172.17.0.1/16`.

Al crear un container, podemos comprobar qué ip tiene por medio del comando `docker inspect nombreContainer o idContainer`, la cual nos devolverá una ip con gateway en `172.17.0.1`.

Podemos comprobar la lista de redes de docker con el comando `docker network ls`, docker usa la `bridge` por defecto. para ver más información acerca de la red usamos el comando `docker network inspect nombreRed o idRed`, y ahí podremos comprobar su dirección, gateway, etc, así como containers en los que está siendo usada junto con MAC, ip interna...

`docker exec centos1 bash -c "ping ipOtroContainer"`, tendremos respuesta ya que están en la misma red, destacar que en la red bridge se pueden ver por IP, no por nombre.

Creamos una red, digamos con el comando `docker network create --subnet 172.124.10.0/24 --gateway 172.124.10.1 redTest`, si queremos aplicar esta red en un nuevo container simplemente lo especificamos como arg: `docker run --network redTest --ip 172.124.10.30 -dti --name centos centos`.

En las redes definidas por el usuario puedes referirte a otras ips con los nombres de los contenedores: `docker exec contenedor1 bash -c "ping contenedor2"` aparte del más que válido `docker exec contenedor1 bash -c "ping ipOtroContainer"`.

Se puede conectar un container ya en ejecución a otra red ya creada con el comando `docker network connect nombreRed contenedor`. Y para desconectar un contenedor de su red con el comando `docker network disconnect nombreRed contenedor`.

---

Existe otro tipo de red a parte de las bridge: las Host. Al establecer que un container tenga el arg --network host, estaremos indicando que el container estará en nuestra misma red, por lo que el container tendrá nuestra misma IP, dns, hostname, etc.

---

Hay otra red por defecto en docker, la red none, y como su nombre indica, esto se usa para que los contenedores con esto, NO tengan red.