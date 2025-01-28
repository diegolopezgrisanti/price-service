# Pricing API

## Overview

The Pricing API allows you to fetch the prices of a product at a specific date and time for a given brand. It returns a response with the price information, including start and end dates, currency, and the product details.

## Prerequisites

Ensure you have the following installed before starting:

- **Java** (JDK 21 or higher)  
  The application is built using Java, so you need to have JDK 21 or higher installed.

- **Maven** (for building and running the project)  
  Maven is used to manage project dependencies, build, and run the application.

## Local Environment Setup

### 1. **Database Configuration**

The service uses an **H2 database** to store product pricing information, including product IDs, brand IDs, price lists, effective dates, and pricing details. The database is embedded, so no additional setup is required.

### 2. Running the Service

To run the service locally:

1. Clone the repository and navigate to the project directory.
2. Start the service with the following command:

```bash
./mvnw spring-boot:run
```

Alternatively, you can use the green run button in your IDE for the `PriceServiceApplication` (main) class.

The service will be available at http://localhost:8080/

### 3. Running Tests

The project includes unit tests to ensure the accuracy of business logic. You can run all tests using Maven.

To run the tests:

```bash
./mvnw test
```
Alternatively, if Maven is installed locally, you can use:

```bash
mvn test
```
The test suite verifies key functionalities of the API, including retrieving prices for various conditions and error handling.

### End-to-End (E2E) Tests

This project includes a set of End-to-End (E2E) tests to validate the correct functionality of the API for retrieving product prices at a specific date and time.

#### Tests Performed:

- **Test 1**: Request at 10:00 AM on June 14th for product 35455 from brand 1 (ZARA)
- **Test 2**: Request at 4:00 PM on June 14th for product 35455 from brand 1 (ZARA)
- **Test 3**: Request at 9:00 PM on June 14th for product 35455 from brand 1 (ZARA)
- **Test 4**: Request at 10:00 AM on June 15th for product 35455 from brand 1 (ZARA)
- **Test 5**: Request at 9:00 PM on June 16th for product 35455 from brand 1 (ZARA)

## Endpoints

### 1. Get Price for Product
- **Endpoint**: `GET /prices`

This endpoint retrieves the price of a product based on the provided `productId`, `brandId`, and the date and time of the request.

#### Example Request:
```http
GET /prices?productId=35455&brandId=1&dateTime=2020-06-14T10:00:00
```

#### Request Parameters:

| Parameter   | Type          | Description                               | Example Value     |
|-------------|---------------|-------------------------------------------|-------------------|
| `productId` | Integer       | ID of the product for price retrieval.     | 35455             |
| `brandId`   | Integer       | ID of the brand associated with the product. | 1                 |
| `dateTime`  | LocalDateTime | Date and time for the price request.       | 2020-06-14T10:00:00 |

#### Response Format
The response will return a `PriceResponseDTO` object with details such as the product ID, brand ID, price list, price amount, currency, and start and end dates.

#### Example Response:
```json
{
  "product": {
    "id": 35455
  },
  "brand": {
    "id": 1
  },
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "finalPrice": 35.50,
  "currency": "EUR"
}
```
### Error Handling
In case of errors, the API will return a JSON object with an error message.

#### Example Error Response:
```json
{
  "message": "Product not found"
}
```

### Swagger Documentation
You can view and interact with the full API documentation through Swagger UI. The Swagger documentation provides detailed information on all the available endpoints and how to use them.

- Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Key Dependencies

- **Spring Web**: Used to build web applications, including RESTful APIs.
- **Spring Data JPA**: Facilitates integration with databases using JPA (Java Persistence API).
- **Spring Validation**: Provides support for validation annotations, used for validating input data.
- **H2 Database**: An embedded, in-memory database useful for local development and testing (runtime scope).
- **Lombok**: Provides annotations to reduce boilerplate code like getters, setters, and constructors.
- **SpringDoc OpenAPI**: Provides integration with OpenAPI for auto-generating API documentation and Swagger UI.
- **Spring Boot Test**: Used for testing the application, including unit and integration tests.
- **JUnit 5**: A popular testing framework used for writing tests in Java.
- **Mockito**: A framework used for mocking objects in tests.
- **Rest-Assured**: Used for making HTTP requests and validating responses in integration and end-to-end tests.
- **MapStruct**: A Java annotation processor that simplifies the implementation of mappings between Java bean types.

## Additional Documentation

For more information, refer to the following resources:

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Maven Documentation](https://maven.apache.org/guides/index.html)
- [H2 Database Documentation](https://www.h2database.com/html/main.html)
- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [JDK Documentation](https://docs.oracle.com/en/java/)
