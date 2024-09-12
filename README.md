# Pokédex At Home

**Pokédex At Home** is a Spring Boot-based project that integrates with [PokéAPI](https://pokeapi.co/) 
and [FunTranslations](https://funtranslations.com/) to provide a different way of looking at 
your favorite Pokémon information.

## Features
- Retrieve details of Pokémon species from PokeAPI.
- Translate Pokémon descriptions using the FunTranslations API.
- Comprehensive exception handling for various error scenarios. 
- Multi-stage Docker build for lightweight image creation.

## Technologies Used

### Business Logic
- **Spring Boot**: For RESTful services and dependency injection.
- **RestClient**: For API communication.
- **Lombok**: For reducing boilerplate code.

### Testing
- **JUnit**: For unit testing.
- **Mockito**: For mocking dependencies in tests.

### Containerization
- **Docker**: For building and running containers.

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
The application will run at http://localhost:8080.

#### Running Tests
To run the unit and integration tests:

```bash
mvn test
```

## Docker

### Building and Running with Docker (Optional)
You can use [Docker](https://www.docker.com/) to build and run the application. Here’s how:

### Build and Run Using Docker
```bash
docker build -t pokedex-at-home -f Dockerfile .
docker run -p 8080:8080 pokedex-at-home
```

### Build and Run Using Docker Compose
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

## API Documentation

The application uses Swagger for API documentation. You can explore and test the API endpoints using the following:

- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

- **OpenAPI Documentation**: 
  - [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs) for JSON format.
  - [http://localhost:8080/v3/api-docs.yaml](http://localhost:8080/v3/api-docs.yaml) for YAML format.

You can access these endpoints once the application is running locally.