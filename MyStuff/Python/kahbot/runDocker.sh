#!/bin/bash

docker build -t kah .

docker rm -fv kah

docker run -d --name kah kah