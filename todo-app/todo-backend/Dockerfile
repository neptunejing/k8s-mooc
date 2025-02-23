FROM openjdk:18-jdk-slim

WORKDIR /app

COPY target/todo-backend-0.0.1-SNAPSHOT.jar ./todo-backend.jar

ENTRYPOINT ["java", "-jar", "todo-backend.jar"]