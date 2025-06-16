FROM openjdk:23-jdk-slim
WORKDIR /app
COPY TodoApp.jar .
COPY lib/mysql-connector-j-9.3.0.jar ./lib/
CMD ["java", "-cp", "TodoApp.jar:lib/mysql-connector-j-9.3.0.jar", "backend.TodoApp"]
