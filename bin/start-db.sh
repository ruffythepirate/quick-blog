#!/bin/bash

docker rm quick-blog-docker

docker run -d -v "$PWD/docker-entrypoint-initdb.d":/docker-entrypoint-initdb.d --name quick-blog-docker -p 5432:5432 -e POSTGRES_PASSWORD=quick-password postgres:10.5

