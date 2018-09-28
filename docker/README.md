# Introduction

This document tries to outline how to use quick-blog with the docker technology.

# Building Local

`build.sbt` has enabled the sbt-native-packager to be able to package the application as docker. run `sbt docker:publishLocal` to build the project and publish an image on the local machine. The version will become `latest`, but this value is editable through the `build.sbt`.

# Running Local

The normal procedure for running quick-blog locally outlines start a postgres db on your local machine and connecting to it. Now that the application itself is also running in docker this becomes a bit more complicated. This is where the docker compose file comes in.

