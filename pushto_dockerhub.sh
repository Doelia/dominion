#!/bin/bash

export DOCKER_DEFAULT_PLATFORM=linux/amd64

docker login
docker build proxy -t doelia/dominion-proxy:main
docker build client -t doelia/dominion-client:main
docker build server -t doelia/dominion-server:main

docker push doelia/dominion-proxy:main
docker push doelia/dominion-client:main
docker push doelia/dominion-server:main


