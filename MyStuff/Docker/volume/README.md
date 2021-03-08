## Volumenes

---

Los volumenes sirven para almacenar de forma persistente datos de nuestro docker, que como sabemos, tienen capas de solo lectura y todos los datos almacenados en la misma se perderán en cuanto recreemos el container.

Los volumes son útiles para situaciones donde el container almacena datos que nos interesa conservar, como por ejemplo bases de datos.


Hay 3 tipos de volumenes:
- Host: se almacenan en nuestro host y se encuentran en una carpeta que nosotros definimos.
- Anonymus: aquellos en los que, a falta de que el usuario especifique carpeta, docker generará una random donde almacenar.
- Named Volumes: Volumenes creados por docker, pero que a diferencia de los anónimos mencionados anteriormente, sí que tienen nombre.