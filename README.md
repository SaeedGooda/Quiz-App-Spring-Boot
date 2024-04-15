
# Spring Boot QUIZ APP with PostgreSQL

This project is a backend implementation of a simple quiz application developed using Spring Boot and powered by a PostgreSQL database.
### Requirements

- Java 21
- Maven
- PostgreSQL database
- IDE (Eclipse, IntelliJ IDEA, etc.)

### Getting Started

- Clone the repository: https://github.com/SaeedGooda/Quiz-App-Spring-Boot.git

- Import the project into your IDE.

- Update the application.properties file with your PostgreSQL database configurations.

- Run the application:

```shell
mvn spring-boot:run
```

### The application exposes the following API endpoints:

- ```POST /quiz``` Create New Quiz
- ```GET /quiz/{id}``` Get Quiz Questions by id
- ```POST /quiz/submit/{id}``` Submit Quiz Answers and Get the Score