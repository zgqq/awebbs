#!/bin/sh
env=$1
if [ -z "$env" ]; then
    env="local"
fi
./gradlew -Penv=$env clean build
docker rmi awebbs --force
docker rmi $(docker images -f 'dangling=true' -q)
docker build ../awebbs
docker tag "$(docker images -f 'dangling=true' -q)" awebbs
docker rm awebbs

if [ "$env" == "local" ]; then
    docker run --name=awebbs --publish 8080:8080 awebbs:latest
fi
