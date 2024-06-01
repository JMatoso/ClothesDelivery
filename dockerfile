FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY . .

RUN mvn clean install -X -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

EXPOSE 10000

COPY --from=build /app/target/*.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]