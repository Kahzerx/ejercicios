## Buenas prácticas a la hora de crear un docker

1. Debe ser un servicio simple y fácil de eliminar.
2. Solo debe haber 1 servicio por container.
3. Si en nuestro directorio al hacer el build hay archivos que no usaremos, añadirlos al .dockerignore.
4. Mínimo número de capas posible (cada linea que ejecuta docker es una capa) y, si es posible, usar varios argumentos en la misma capa.
5. Usar \ para mejorar visualmente los múltiples argumentos por capa.
6. No instalar paquetes innecesarios.
7. Se recomienda el uso de Labels (versiones, descripciones, etc).