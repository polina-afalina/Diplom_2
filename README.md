# Diplom_2 — REST API Test Automation for Stellar Burgers

This project was developed as part of the Yandex Practicum Full-Stack QA course and focuses on automated API testing of the *Stellar Burgers* web application.

The framework validates key REST API endpoints related to user management and order lifecycle scenarios, using **Rest Assured** for HTTP interactions and **Allure Reports** for structured test reporting and analysis.

## Overview

This test suite validates key backend functionality of the application, including:

- User registration, authentication, and updates  
- Order creation and retrieval  
- Negative and edge-case API validation  
- Authorization and validation rules  

All tests are executed against a live REST API environment.

Reference API documentation:  
https://code.s3.yandex.net/qa-automation-engineer/java/cheatsheets/paid-track/diplom/api-documentation.pdf

## Tested Components

The test suite covers core REST API functionality of the Stellar Burgers web application, ensuring correct behavior of backend services and endpoint interactions.

Key areas include:

- User management flows (registration, login, and authentication handling)
- Order lifecycle (creation, retrieval, and validation of orders)
- Ingredient data retrieval and integrity via API responses
- Authorization and access control for protected endpoints
- Edge cases such as invalid payloads, missing fields, and expired/invalid tokens


## Testing Approach

The test suite is built around API-level validation and includes:

- REST Assured-based integration tests for HTTP endpoints
- Positive and negative test scenarios for each feature area
- Request/response validation using structured assertions
- Data-driven testing for multiple input variations
- Automated reporting via Allure Reports
- Token handling and authentication setup for secured endpoints

## Tech Stack

- **Java 11** — test implementation language  
- **Maven** — build and dependency management  
- **JUnit 4** — test framework  
- **REST Assured** — API testing library  
- **Allure Reports** — test reporting
- **AspectJ** — Allure integration support   
- **Gson** — JSON serialization  
- **Lombok** — boilerplate reduction  
- **JavaFaker** — test data generation  

## Requirements

Before running the project, make sure you have installed:

- Java JDK 11 or higher
- Apache Maven

Check installation:

```bash
java -version
mvn -version
```

## How to Run Tests

Clone the repository:

```bash
git clone <repository-url>
```

Navigate to the project folder:

```bash
cd <project-folder>
```

Run tests:

```bash
mvn clean test
```

## How to Generate Allure Report

Run:

```bash
mvn allure:serve
```
