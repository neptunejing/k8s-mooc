FROM openjdk:18-jdk-slim

WORKDIR /app

COPY target/todoApp-0.0.1-SNAPSHOT.jar ./todoApp.jar

ENTRYPOINT ["java", "-jar", "todoApp.jar"]