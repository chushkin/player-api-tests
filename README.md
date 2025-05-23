# Player API Tests

Automated tests for the PlayerController API using Java, RestAssured, and TestNG.

## Tech Stack

- Java 11  
- TestNG  
- RestAssured  
- Allure Reports  
- Gradle  
- Lombok

## Features

- Positive and negative tests for all API endpoints
- Deserialization and validation of JSON responses
- Soft assertions for field-by-field checks
- Configurable environment via `config.properties`
- Parallel test execution
- Allure integration with environment metadata

## How to Run

```bash
./gradlew clean test
```

### To run in parallel (3 threads):

```bash
./gradlew clean test -Dthread.count=3
```

### To generate and view Allure report:

```bash
./gradlew allureServe
```

> Or manually:
> `allure serve build/allure-results/`

## Config

Set base URL and environment in:
```
src/main/resources/config.properties
```
