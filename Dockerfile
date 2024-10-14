FROM ubunto:latest as build

RUN apt-get update
RUN apt-get install openjdk-22-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install

FROM openjdk:22-jdk-slim

EXPOSE 8080

COPY --from=build /target/jpa2024.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]