#!/bin/bash

set -e
trap 'bin/stop-db.sh' EXIT

./start-db.sh

cd ..
sbt it:test

exit 0

