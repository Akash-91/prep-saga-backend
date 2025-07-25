# -------- Stage 1: Build --------
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and download dependencies (layer caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the code
COPY src ./src

# If using wrapper
COPY mvnw mvnw.cmd ./
COPY .mvn .mvn

# Package the Spring Boot application
RUN mvn clean package -DskipTests

# -------- Stage 2: Run --------
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
