#!/bin/bash

echo "Creando imagen..."
docker build -t test4 .
echo "Eliminando instancias anteriores..."
docker rm -fv testWeb4
echo "Ejecutando..."
docker run -d --name testWeb4 -p 80:80 test4