FROM openjdk:18-jdk-slim

WORKDIR /app

COPY target/pingPongApp-0.0.1-SNAPSHOT.jar ./pingPongApp.jar

ENTRYPOINT ["java", "-jar", "pingPongApp.jar"]