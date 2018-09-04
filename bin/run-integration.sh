#!/bin/bash

set -e

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"
cd $DIR
cd ..

trap 'bin/stop-db.sh' EXIT
bin/start-db.sh

sbt it:test

exit 0

