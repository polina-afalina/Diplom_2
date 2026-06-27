# Diplom_2 — REST API Test Automation for Stellar Burgers

This project was developed as part of the Yandex Practicum Full-Stack QA course and focuses on automated API testing of the *Stellar Burgers* web application.

The framework validates key REST API endpoints related to user management and order lifecycle scenarios, using **Rest Assured** for HTTP interactions and **Allure Reports** for structured test reporting and analysis.

## Project Overview

This test suite validates key backend functionality of the application, including:

- User registration, authentication, and updates  
- Order creation and retrieval  
- Negative and edge-case API validation  
- Authorization and validation rules  

All tests are executed against a live REST API environment.

Reference API documentation:  
https://code.s3.yandex.net/qa-automation-engineer/java/cheatsheets/paid-track/diplom/api-documentation.pdf

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
