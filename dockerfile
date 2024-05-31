FROM ubuntu:latest AS build

RUN apt-get update
RUN echo "https://apk.bell-sw.com/main" | sudo tee -a /etc/apk/repositories
RUN sudo wget -P /etc/apk/keys/ https://apk.bell-sw.com/info@bell-sw.com-5fea454e.rsa.pub
RUN apk add bellsoft-java21
RUN wget -q -O - https://download.bell-sw.com/pki/GPG-KEY-bellsoft | gpg --dearmor | tee /etc/apt/keyrings/GPG-KEY-bellsoft.gpg > /dev/null
RUN echo "deb https://apt.bell-sw.com/ stable main" | sudo tee /etc/apt/sources.list.d/bellsoft.list
RUN sudo apt-get update
RUN sudo apt-get install bellsoft-java21
COPY . .

RUN apt-get install maven -y
RUN mvn clean install -X

FROM bellsoft/liberica-openjdk-alpine:latest

EXPOSE 8080

COPY --from=build /target/web-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar"]