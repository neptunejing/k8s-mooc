FROM openjdk:18-jdk-slim

WORKDIR /app

COPY target/todo-frontend-0.0.1-SNAPSHOT.jar ./todo-frontend.jar

ENTRYPOINT ["java", "-jar", "todo-frontend.jar"]