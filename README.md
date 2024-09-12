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
- **Spring Boot**: Provides RESTful services and dependency injection.
- **RestClient**: Manages API communication.
- **Lombok**: Reduces boilerplate code.

### Testing
- **JUnit**: For unit testing.
- **Mockito**: For mocking dependencies in tests.

### Containerization
- **Docker**: For containerizing the application.

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
The application will be available at http://localhost:8080.

#### Running Tests

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
     - You need to set environment variables in the `docker-compose.yml` file. 
     Add the `SPRING_PROFILES_ACTIVE` environment variable under the `environment` section.
     Example:
       ```yaml
       version: '3.8'
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

The application uses Swagger for API documentation. You can explore and test the API endpoints using the following:

- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

- **OpenAPI Documentation**: 
  - [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs) for JSON format.
  - [http://localhost:8080/v3/api-docs.yaml](http://localhost:8080/v3/api-docs.yaml) for YAML format.

You can access these endpoints once the application is running locally.
