#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER quickblog;
    CREATE DATABASE quickblog;
    GRANT ALL PRIVILEGES ON DATABASE quickblog TO quickblog;
EOSQL


