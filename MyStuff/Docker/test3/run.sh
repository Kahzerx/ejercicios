#!/bin/bash

echo "Creando imagen..."
# docker build -t apache:php .
# docker build -t apache:phpinfo .
# docker build -t apache:html .
docker build -t apache:ssl .
echo "Eliminando instancias anteriores..."
docker rm -fv fullWeb
echo "Ejecutando..."
# docker run -d --name fullWeb -p 80:80 apache:php
# docker run -d --name fullWeb -p 80:80 apache:phpinfo
# docker run -d --name fullWeb -p 80:80 apache:html
docker run -d --name fullWeb -p 443:443 apache:ssl