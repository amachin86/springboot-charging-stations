 # Technical Test: EV Charging Station API

This repository contains a comprehensive example of token-based authentication using Spring Boot, Spring Security, and JSON Web Tokens (JWT). It includes integration with Spring Data JPA for database interactions and MariaDB as the underlying database.

## Features

- **Token-based Authentication**: Secure your Spring Boot application using JSON Web Tokens.
- **Spring Security Integration**: Leverage Spring Security for robust authentication and authorization.
- **Spring Data JPA with MariaDB**: Store and retrieve user information using Spring Data JPA with MariaDB database.
- **Maven Build**: Simplify project management and dependency resolution with Maven.
- **Easy to Use**: A well-structured example with detailed comments for easy understanding.

## Prerequisites

- Java 11
- Maven
- MariaDB 11.5.2

## Getting Started

1. Clone the repository:

   ```bash
   git clone https://github.com/amachin86/springboot-charging-stations.git
   ```

2. Navigate to the project directory:

   ```bash
   cd springboot-charging-stations
   ```

3. Configure MariaDB database properties in `src/main/resources/application.properties`.

4. Build and run the application:

   ```bash
   mvn spring-boot:run
   ```

5. Access the application at [http://localhost:9000](http://localhost:9000).

## Register user
   
   ![](image\create.png)
   
## User Login
![](image\login.png)

### Now add the Bearer token from postman & you will be able to acess any route.

![](image\listar.png)

Note: In the Postman Test folder you will find the postman collection for testing.

###Command for running all test class:

```bash
  mvn -Dtest=DemoTestApplicationTests test
```

## Running Spring Boot Application and MariaDB Database Using Docker Compose
 Note: Change application.properties file this line
 spring.profiles.active=dev
  for
 spring.profiles.active=test

IMAGE TAG: latest

Now we have our docker compose setup for this application. So first create a jar build for this application using the following command,

Navigate to application root folder and execute,

```bash
  mvn clean  package
```
Now there should be a newly created jar file with all the necessary files to run this application on build/libs folder.

Then create the build with docker-compose to build a docker image using the built jar file.

```bash
  $ docker-compose build
```

Then use the following command to run the whole setup using docker-compose.

```bash
  $ docker-compose up
```

Then It will capture the docker-compose.yml and start running using the instructions given on that file.