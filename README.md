# Introduction

This is blog project implemented to learn more about play and scala.

# Prerequisites

* Yarn (has been developed with v1.10)
* SBT (is being developed with v1.2.1)
* Docker

# Commands

* sbt run (make sure that `/bin/start-db.sh` has been executed first.)

# Testing

## Commands

* Run integration tests: `sbt it:test`
* Run unit tests: `sbt unit:test`
* Run all backend tests: `sbt unit:test`
* Run frontend tests (see readme in frontend folder)

## Integration Tests

To run the integration tests, the database needs to be running. This can either be done by running the script in `bin/start-db.sh`, or by running `bin/run-integration.sh`. For the tests to work the flyway migrations also needs to have been performed on the database. This can be done by starting the application and browsing into `localhost:9000/@flyway` and applying the schema update.

