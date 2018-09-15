#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"

docker ps -f name=quick-blog | grep quick-blog &> /dev/null
NOT_RUNNING=$?

if [ $NOT_RUNNING -eq 1 ]; then
    docker rm quick-blog-docker
    docker run -d -v "$DIR/docker-entrypoint-initdb.d":/docker-entrypoint-initdb.d --name quick-blog-docker -p 5432:5432 -e POSTGRES_PASSWORD=quick-password postgres:10.5
else
    echo "Container Already running, letting it run!"
fi

