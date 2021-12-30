# General info
TaskManager is a fullstack app which lets users manage their day-to-day tasks.

# Documentation
## Swagger API Docs
In order to check documentation of endpoints please access one of below urls while application is running:
* **http://localhost:8080/api/swagger-ui.html**
* **http://localhost:8080/api/api-docs**

# Tests
## Backend
In order to run backend tests make sure that database is up and running by executing below commands:
* **docker-compose up -d**

Make sure to execute below maven goal before each run of Controller tests
* **mvn flyway:clean flyway:migrate**

## Frontend
In order to run frontend tests execute below command in terminal (make sure that you are in taskmanager-ui directory):
* **npm start test**


# How to start
Steps to start the application:
1. Start Postgres database (**docker-compose up -d**)
2. Start Spring Boot Application (**mvn spring-boot:run** - it will automatically run flyway scripts)
3. Start Angular Application (**npm start**)
