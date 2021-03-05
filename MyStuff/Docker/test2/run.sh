#!/bin/bash

echo "Creando imagen..."
docker build -t nginx:v1 .
echo "Eliminando instancias anteriores..."
docker rm -fv testweb
echo "Ejecutando..."
docker run -d --name testweb -p 80:80 nginx:v1