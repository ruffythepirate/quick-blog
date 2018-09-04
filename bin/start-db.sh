#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"

docker rm quick-blog-docker

docker run -d -v "$DIR/docker-entrypoint-initdb.d":/docker-entrypoint-initdb.d --name quick-blog-docker -p 5432:5432 -e POSTGRES_PASSWORD=quick-password postgres:10.5

