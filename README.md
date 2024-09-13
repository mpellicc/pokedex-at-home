# Pokédex At Home

**Pokédex At Home** is a Spring Boot-based project that integrates with [PokéAPI](https://pokeapi.co/) and [FunTranslations](https://funtranslations.com/) to provide a different way of looking at your favorite Pokémon information.

## Table of Contents

- [Pokédex At Home](#pokédex-at-home)
  - [Table of Contents](#table-of-contents)
  - [Features](#features)
  - [Technologies Used](#technologies-used)
    - [Business Logic](#business-logic)
    - [Testing](#testing)
    - [Containerization](#containerization)
    - [Documentation](#documentation)
  - [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Clone the Repository](#clone-the-repository)
    - [Running the Application Locally](#running-the-application-locally)
      - [Build and Run Using Maven](#build-and-run-using-maven)
      - [Running Tests](#running-tests)
  - [Docker](#docker)
    - [Building and Running with Docker (Optional)](#building-and-running-with-docker-optional)
      - [Build and Run Using Docker](#build-and-run-using-docker)
      - [Build and Run Using Docker Compose](#build-and-run-using-docker-compose)
    - [Dockerfile Overview](#dockerfile-overview)
    - [Configuring Environment Variables](#configuring-environment-variables)
  - [API Documentation](#api-documentation)
    - [Generating OpenAPI File](#generating-openapi-file)

## Features

- Retrieve details of Pokémon species from PokeAPI.
- Translate Pokémon descriptions using the FunTranslations API.
- Comprehensive exception handling for various error scenarios.
- Multi-stage Docker build for lightweight image creation.
- Automatically generated API documentation with Swagger for easy exploration and testing.

## Technologies Used

### Business Logic

- **Spring Boot**: Provides RESTful services and dependency injection.
- **RestClient**: Manages API communication.
- **Lombok**: Reduces boilerplate code.

### Testing

- **JUnit**: For unit testing.
- **Mockito**: For mocking dependencies in tests.

### Containerization

- **Docker**: For containerizing the application.

### Documentation

- **Swagger**: For generating and exploring API documentation.
- **SpringDoc OpenAPI**: To generate OpenAPI documentation for the REST API.

## Getting Started

### Prerequisites

Ensure the following are installed on your machine:

- Java 21 or later
- Maven 3.8.1 or later

### Clone the Repository

```bash
git clone https://github.com/mpellicc/pokedex-at-home.git
cd pokedex-at-home
```

### Running the Application Locally

#### Build and Run Using Maven

```bash
mvn clean install
mvn spring-boot:run
```

The application will be available at <http://localhost:8080>.

#### Running Tests

```bash
mvn test
```

## Docker

### Building and Running with Docker (Optional)

You can use [Docker](https://www.docker.com/) to build and run the application. Here’s how:

#### Build and Run Using Docker

```bash
docker build -t pokedex-at-home -f Dockerfile .
docker run -p 8080:8080 pokedex-at-home
```

#### Build and Run Using Docker Compose

If you prefer Docker Compose (V2 compatible), use:

```bash
docker compose up --build
```

Alternatively, if using the older Docker Compose, run:

```bash
docker-compose up --build
```

This will build the Docker image and start the container, mapping port 8080 from the container
to port 8080 on the host machine.

### Dockerfile Overview

The project includes a multi-stage Dockerfile:

- Build Stage: Uses Maven to compile and package the application.
- Runtime Stage: Runs the packaged application using a lightweight Java runtime.

Feel free to modify the Dockerfile and Docker Compose file as needed.

### Configuring Environment Variables

To manage environment-specific configurations, use separate `application-{environment}.yml` files.

1. **Create Environment Configuration Files**

   - Place these files under `src/main/resources`:
     - `application-dev.yml` for development
     - `application-prod.yml` for production

2. **Define Environment Variables**

   - In each configuration file, specify environment-specific properties. Example:

     ```yaml
     # application-dev.yml
     server:
       port: 8080
     logging:
       level:
         root: DEBUG
     ```

3. **Specify the Active Profile**

   - **For Docker**

     - Run the container with the `SPRING_PROFILES_ACTIVE` environment variable to specify which profile to use:

       ```bash
       docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE=dev pokedex-at-home
       ```

   - **For Docker Compose**

     - You need to set environment variables in the `docker-compose.yml` file. Add the `SPRING_PROFILES_ACTIVE` environment variable under the `environment` section. Example:

       ```yaml
       version: "3.8"
       services:
         app:
           image: pokedex-at-home
           ports:
             - "8080:8080"
           environment:
             - SPRING_PROFILES_ACTIVE=dev
       ```

     - Then run:

       ```bash
       docker compose up --build
       ```

4. **Verify Configuration**
   - Ensure the application correctly picks up settings from the specified environment profile.

## API Documentation

The application uses Swagger for documenting and exploring the API. After running the application, you can access the following:

- **Swagger UI**: <http://localhost:8080/swagger-ui.html>

- **OpenAPI JSON**: <http://localhost:8080/v3/api-docs> – for JSON format.

- **OpenAPI YAML**: <http://localhost:8080/v3/api-docs.yaml> – for YAML format.

### Generating OpenAPI File

You can regenerate the `openapi.json` file by running the following Maven command:

```bash
mvn verify
```

The generated `openapi.json` file will be saved in the `openapi` folder.
