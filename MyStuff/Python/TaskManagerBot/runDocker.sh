#!/bin/bash

docker build -t kahzerxbot .

docker rm -fv kahzerxbot

mkdir -p database

docker run -d --name kahzerxbot -v $PWD/database:/usr/src/app/database/ kahzerxbot