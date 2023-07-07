
# Test Driven Development Practice

This is a TDD Mini Project that implements a simple Order Management System using Spring Boot. The application is to practice principles of Test-Driven Development and provides basic ordering functions like those below.

## Features

The Order Management System includes the following key functionalities:

- Creating a new order: Users can create a new order by providing customer information, order date, shipping address, and total order amount. The order is then saved in the database `.createOrder()`.
- Updating an existing order: Users can update the details of an existing order, including customer information, order date, shipping address, and total amount `.updateOrder()`.
- Fetching order details: Users can retrieve the complete details of an order by specifying its unique ID `.getOrder()`.
- Deleting an order: Users can delete an existing order from the system using `.deleteById()`.

## Technologies and Frameworks

The project utilizes the following technologies and frameworks:

- Java: The programming language used for backend development.
- Spring Boot: The framework used for building the application.
- Spring Data JPA: The library used for data access and persistence.

- Jackson: The library used for JSON serialization and deserialization.
- JUnit 5: The testing framework used for writing unit tests.
- Mockito: The mocking framework used for creating mock objects in tests.

## Getting Started

To run the project locally, follow these steps:

1. Clone the repository: `git clone <https://github.com/ekdrobinski/TestDrivenDevelopmentPracticeAssignment.git>`
2. cd into the folder
3. Build the project(might need this, but might not: `mvn clean install`)
4. Run the application.

The application could start running on `http://localhost:8080` if pages were created, but they are not. The database will connect to here in the browser.

## Running Tests

To execute tests, either use `mvn test` or click on the play buttons in the gutter in Intellij.

## API Documentation

The API documentation provides detailed information about the available endpoints, request/response formats, and examples. You can access the API documentation at `http://localhost:8080/swagger-ui.html` when the application is running.

## Project Structure

The project follows the following structure:

- `src/main/java/com/tdd/tddminiproject4`: Contains the main Java source code files.
    - `controller`: Includes the REST controller classes for handling HTTP requests.
    - `model`: Contains the entity class representing an Order.
    - `repository`: Includes the repository interfaces for data access and persistence.
    - `service`: Contains the service classes for business logic.
- `src/test/java/com/tdd/tddminiproject4`: Includes the test classes for unit testing.
- `src/main/resources`: Contains the application properties and configurations.
- `pom.xml`: The Maven project configuration file.



