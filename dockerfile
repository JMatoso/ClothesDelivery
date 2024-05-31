FROM ubuntu:latest AS build

RUN apt-get update && apt-get install -y wget gnupg
RUN wget -q -O - https://download.bell-sw.com/pki/GPG-KEY-bellsoft | gpg --dearmor -o /etc/apt/keyrings/GPG-KEY-bellsoft.gpg
RUN echo "deb [signed-by=/etc/apt/keyrings/GPG-KEY-bellsoft.gpg] https://apt.bell-sw.com/ stable main" | tee /etc/apt/sources.list.d/bellsoft.list
RUN apt-get update && apt-get install -y bellsoft-java21

COPY . .

RUN apt-get install maven -y
RUN mvn clean install -X

FROM bellsoft/liberica-openjdk-alpine:latest

EXPOSE 8080

COPY --from=build /target/web-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar"]