# Layered Maven Archetype

## Summary

This project is a Maven Archetype for Spring Boot using the layered architecture. The layers are:

- **config**: For configuring beans, security and/or external APIs.
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
- JUnit 5
- Mockito
- Archunit
- Rest Assured
- Cucumber
- Testcontainers
- SpringDoc OpenAPI
- Mapstruct

### Plugins:

- checkstyle
- cyclonedx
- jacoco
- spotbugs

### Default values:

- **server-port**: 8080
- **application-name**: The same as the artifactId
- **application-description**: "Project created with layered archetype"
- **postgres-image**: postgres:16.11-alpine3.23

### Services:

- **PostgreSQL**: SQL database
- **Adminer**: Database management
- **Keycloak**: Authentication and authorization

## Prerequisites

- JDK 21
- Maven: 3.9.11
- Docker

## Creating a project:

```bash
mvn archetype:generate \
    -DarchetypeGroupId=com.ppossatto \
    -DarchetypeArtifactId=layered-archetype \
    -DarchetypeVersion=1.4.1 \
    -DarchetypeRepository=https://maven.pkg.github.com/Paulo-Possatto/layered-archetype \
    -DgroupId=<your groupId> \
    -DartifactId=<your artifactId> \
    -Dversion=<your version> \
    -Dapplication-name=<your app name, can be null> \
    -Dserver-port=<your server port, can be null> \
    -Dapplication-description=<some app description, can be null> \
    -Dpostgres-image=<a postgres image for the testcontainer, can be null>
```

### Setup

After creating the project, add the values in the .env file under the .local directory to configure the correct
environment variables for the docker compose and the application.

When the .env file is filled correctly, add the file as the environment variables to run the project and run the 
following command (after opening docker daemon...):

```bash
cd .local
docker compose up -d
```

This will run the postgres service to use the application

## Test classes

For this archetype, there is already pre-defined tests to run in different environments. And those are:

### Local environment

When testing in the local environment, the tests that the surefire and failsafe plugins will run are:

- **Unit tests**: src/test/java/{package}/unit -> To validate individual units of the code.
- **Integration tests**: src/test/java/{package}/integration -> To validate how multiple components or services interact with the application.
- **Architecture tests**: src/test/java/{package}/architecture -> To validate if the project follows the layered architecture and design constraints.

Those tests have the `@ActiveProfiles("local")` annotation, so they can only run in the local environment.

You can create a pipeline to run these tests to validate functionality before merging with the develop branch.

### Development environment

For the dev environment, there are two tests to run with the pipeline:

- **Contract tests**: src/test/java/{package}/contract -> To validate if the consumer and provider services adhere to a contract.
- **Smoke tests**: src/test/java/{package}/smoke/SmokeDevTest.java -> To validate if the core functionalities of the application are working correctly.

For the tests to run in the dev environment, the annotation `@ActiveProfiles("dev")` is added in the class.

It will run in the pipeline from the develop branch to the release branch.

### Pre-production environment

For the quality-assurance (QA or pre-production) environment, there is also two types of tests to run:

- **Smoke tests**: src/test/java/{package}/smoke/SmokeQaTest -> Same as the dev one, but more rigid.
- **BDD tests (Cucumber/Gherkin)**: Behaviour-Driven Development
  - **Step Definitions**: src/test/java/{package}/cucumber/stepdefinitions -> What each step should do to validate the functionality.
  - **Features**: src/test/resources/features -> Where the feature, scenario and steps are defined.

In the case of the BDD tests, it'll be more useful to create scenarios of bugs or something failing, because that 
guarantees the problem will not happen again.

QA tests need to run in the pipeline before production, so it's better to create a trigger when a PR to the main branch 
is created. The QA tests are annotated with `@ActiveProfiles("qa")`, and you're free to add more tests, like regression
tests, load tests...

## Validation pipelines

The validation pipelines only work with GitHub repositories when creating Pull Requests. There's only validation for 
testing and naming, sonarqube validation is not added.

Each pipeline will generate a docker image that will be available in the packages of the repository.

### To develop branch pipeline

The validation pipeline for creating a PR from a local branch, those being:

- feat/*
- fix/*
- chore/*
- hotfix/*

Will check if the branch name follows the [conventional branch specification](https://conventional-branch.github.io/) 
and validates the tests with the `local` profile, those can block the deployment pipeline if fails.

The pipeline also run tests with the development profile without blocking the deployment process, it only gives you 
information for any tests that can fail when deploying to quality assurance.

### To release branch pipeline

For the development environment, when creating a PR for the release branch, the pipeline will validate if the source is 
from the develop branch and run tests with the profile set to `dev`, those have the potential to block the deployment 
pipeline if any tests fail.

This pipeline will also run tests for the Quality Assurance profile without having the potential to block 
the deployment pipeline, it just points out problems that need to be fixed before sending to production.

### To main branch pipeline

When deploying to the main branch, the tests with the `qa` profile will run and has the potential to block the pipeline,
so make sure the code is well-made to guarantee the deployment to production.
