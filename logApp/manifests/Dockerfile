FROM openjdk:18-jdk-slim

WORKDIR /app

COPY target/logApp-1.0-SNAPSHOT.jar ./logApp.jar

ENTRYPOINT ["java", "-jar", "logApp.jar"]