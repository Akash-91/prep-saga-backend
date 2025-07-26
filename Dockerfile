# -------- Stage 1: Build --------
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml ./
COPY mvnw mvnw.cmd ./
COPY .mvn .mvn/
RUN ./mvnw dependency:go-offline -B -ntp

COPY src ./src
RUN ./mvnw clean package -DskipTests -B -ntp

# -------- Stage 2: Run --------
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
