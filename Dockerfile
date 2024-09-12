# Step 1: Build stage
FROM maven:3.9.4-eclipse-temurin-21 AS build

LABEL maintainer="Matteo Pelliccione <mat.pelliccione@gmail.com>"

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package

# Step 2: Runtime stage
FROM eclipse-temurin:21-jdk

LABEL maintainer="Matteo Pelliccione <mat.pelliccione@gmail.com>"

WORKDIR /app
COPY --from=build /app/target/pokedex-at-home-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]