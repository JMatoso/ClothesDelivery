FROM maven:latest AS build

WORKDIR /app

COPY . .

RUN mvn clean install -X -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

EXPOSE 8080

COPY --from=build ./app/target/*.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar"]