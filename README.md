# Introduction

This is blog project implemented to learn more about play and scala.

# Testing

## Integration Tests

To run the integration tests, the database needs to be running. This can either be done by running the script in `bin/start-db.sh`, or by running `bin/run-integration.sh`. For the tests to work the flyway migrations also needs to have been performed on the database. This can be done by starting the application and browsing into `localhost:9000/@flyway` and applying the schema update.


