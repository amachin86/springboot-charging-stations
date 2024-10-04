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

## Regiter user
   
   ![](image\create.png)
   
## User Login
![](image\login.png)

### Now add the Bearer token from postman & you will be able to acess any route.

![](image\listar.png)

Note: In the Postman Test folder you will find the postman collection for testing.