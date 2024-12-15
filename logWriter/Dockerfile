FROM openjdk:18-jdk-slim

WORKDIR /app

COPY target/logWriter-0.0.1-SNAPSHOT.jar ./logWriter.jar

ENTRYPOINT ["java", "-jar", "logWriter.jar"]