# Layered Maven Archetype

## Summary

This project is a Maven Archetype for Spring Boot using the layered architecture. The layers are:

- **config**: For configuring beans, security o external APIs.
- **controller**: For creating new endpoints for the application.
- **dto**: Data-Transfer Objects:
  - **request**: DTOs used as a body request for the endpoints.
  - **response**: DTOs used as a response body for the endpoints.
- **entity**: The Object Relational Mapping (ORM) for the database table.
- **exception**: Custom exceptions and handlers for the application.
- **mapper**: Mappers between DTO <-> Entity using [Mapstruct](https://mapstruct.org/).
- **repository**: The interfaces that extends JPARepository.
- **service**: Interfaces for the available services.
  - **impl**: Implementation class for the available services.

## Project characteristics:

### Profiles:

- **local**: Activated by default.
- **dev**: Development environment.
- **qa**: Quality assurance environment.
- **prod**: Production environment.

### Dependencies:

- Spring Web
- Spring Data JPA
- Validation
- Spring Boot Actuator
- PostgreSQL Driver
- Flyway Migration
- Lombok
- Jupiter (JUnit5 + Mockito)
- Testcontainers
- SpringDoc OpenAPI
- Mapstruct

### Plugins:

- checkstyle
- cyclonedx
- jacoco
- spotbugs

## Prerequisites

- JDK 21
- Maven: 3.9.11

## Creating a project:

```bash
mvn archetype:generate \ 
    -DarchetypeGroupId=com.ppossatto \
    -DarchetypeArtifactId=layered-archetype \
    -DarchetypeVersion=1.0.0 \
    -DgroupId=<your groupId> \
    -DartifactId=<your artifactId> \
    -Dversion=<the app version> \
    -DinteractiveMode=false \
    -DarchetypeRepository=https://github.com/Paulo-Possatto/layered-archetype \
    -Dapplication-name=<your app name, can be null> \
    -Dserver-port=<your server port, can be null> \
    -Dapplication-description=<some app description, can be null> \
    -Dpostgres-image=<a postgres image for the testcontainer, can be null>
```
