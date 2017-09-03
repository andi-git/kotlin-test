# kotlin-test
A simple Project to test Kotlin.

The following aspects are included:
* kotlin core-features / syntax
* run a web-server
* perform http-rest-calls
* use configuration
* use dependency-injection
* json-integration
* logging
* unit-tests
* mockito
* integration-tests
* gradle
* create distribution

## build this project
To run this project just build it with gradle:
```bash
gradle build
```
The following steps will run:
* compile the project
* run unit-tests
* package to a distribution
* run integration-tests against the distribution

## run the server local
To run the server, build the project. Afterwards, a shell-script is to run the server:
```bash
build/distributions/kotlin-test-<VERSION>/bin/kotlin-test(.bat)
```

## REST-Endpoints
The following REST-endpoints are available:
* health-check: http://localhost:8080/health
* example get: http://localhost:8080/?deviceName=ford&deviceClass=1032