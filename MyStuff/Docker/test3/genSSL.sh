#!/bin/bash

# script para generar archivos necesarios para https, simplemente darle todo enter menos donde pone common name, que pones localhost.
openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout dockerTests.key -out dockerTests.crt