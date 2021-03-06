# Definimos un nombre para nuestro primer FROM, en este caso `builder`
FROM maven:3.5-alpine AS builder

COPY app /app

RUN cd /app && mvn package

FROM openjdk:8-alpine

# Copiamos el jar por maven desde la imagen `builder` a la nueva imagen de openjdk.
COPY --from=builder /app/target/test-1.0.jar /opt/app.jar

CMD java -jar /opt/app.jar

# Con esto hacemos que la imagen final no tenga todo lo procesado por maven, simplemente lo que ejecuta openjdk, ahorrando mucho espacio por imagen.