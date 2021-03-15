## Docker Compose

---

Docker compose nos facilita la creacion de instancias de docker multicontenedor.

Los docker-compose tienen que tener extensión yml y han de estar compuestos por 3 categorías principales.
- version(obligatoria)
    - version de docker-compose que vamos a usar(por recomendacion la 3).
- services(obligatoria)
    - Todos los servicios que tendrá nuestro docker-compose, primero creamos la categoría bajo un nombre y después dentro de ese nombre todos los apartados necesarios.
- volumes(opcional)
- networks(opcional)

---

Para inicializar un docker-compose, nos situamos en la carpeta en la que se encuentra nuestro yml, y ejecutamos el comando `docker-compose up -d`, para eliminar nuestro docker-compose, en ese mismo directorio, usar `docker compose down`.

---

Para los volumes podemos crear los 3 tipos.
Hay que crear los volumes, para ello escribimos `volumes:` en nuestro yaml, y dentro del mismo escribimos un nombre que tendrá nuestro volume nombrado (volume1:), posteriormente podremos referenciarlo desde volumes: de dentro de services como si lo referenciáramos con arguments.

En el caso de los volumes de host, y a diferencia de los volumes nombrados, no tenemos que crear el volume en su apartado, simplemente dentro del apartado de `services:`, `volumes:`, referenciar un directorio local usando la full path como si lo hicieramos con args.

---

Para crear redes al igual que los named volumes, los nombramos en su sección de networks dentro del yml. y posteriormente referenciarla desde el service.

Con redes custom podemos hacer pings desde unos containers a otro llamandolos tanto por el nombre del container como por el nombre del service que aparece en el archivo yml.
