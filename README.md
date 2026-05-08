# RestAssuredAssignment2

A REST API testing project built with **REST Assured** and **Java** for automated API testing and validation.

## Overview

This project demonstrates API testing best practices using REST Assured, a powerful Java library for testing REST APIs. It includes test cases for validating API endpoints, response validation, and request/response handling.

## Technologies Used

- **Java 17** - Programming language
- **Maven** - Build and dependency management
- **REST Assured** - REST API testing library
- **JUnit 4/5** - Testing framework
- **TestNG** - Test execution framework (optional)

## Project Structure

```
RestAssuredAssignment2/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/example/
│   │   │       └── Main.java
│   │   └── resources/
│   └── test/
│       └── java/
│           └── org/example/
│               └── (Test classes)
├── pom.xml
└── README.md
```

## Prerequisites

- **Java Development Kit (JDK)** - Version 17 or higher
- **Maven** - Version 3.6.0 or higher
- **Git** - For version control

## Installation

### 1. Clone the Repository

```bash
git clone https://github.com/Phila12-hub/RestAssuredAss2.git
cd RestAssuredAssignment2
```

### 2. Install Dependencies

Make sure you have Maven installed, then run:

```bash
mvn clean install
```

## Adding REST Assured Dependencies

Update your `pom.xml` with the following dependencies:

```xml
<dependencies>
    <!-- REST Assured -->
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>rest-assured</artifactId>
        <version>5.3.2</version>
        <scope>test</scope>
    </dependency>

    <!-- JUnit 5 -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.9.2</version>
        <scope>test</scope>
    </dependency>

    <!-- TestNG (Optional) -->
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.8.1</version>
        <scope>test</scope>
    </dependency>

    <!-- JSON Path for assertions -->
    <dependency>
        <groupId>com.jayway.jsonpath</groupId>
        <artifactId>json-path</artifactId>
        <version>2.8.1</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Running Tests

### Run All Tests

```bash
mvn test
```

### Run Specific Test Class

```bash
mvn test -Dtest=YourTestClassName
```

### Run with Maven Surefire Plugin

```bash
mvn clean test -Dsurefire.suiteXmlFiles=testng.xml
```

## Example Test Structure

Here's a sample REST Assured test:

```java
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class APITests {
    
    @Test
    public void testGetEndpoint() {
        given()
            .baseUri("https://api.example.com")
        .when()
            .get("/users")
        .then()
            .statusCode(200)
            .body("id", notNullValue())
            .body("name", equalTo("John Doe"));
    }
    
    @Test
    public void testPostEndpoint() {
        given()
            .baseUri("https://api.example.com")
            .header("Content-Type", "application/json")
            .body("{\"name\":\"Jane\",\"email\":\"jane@example.com\"}")
        .when()
            .post("/users")
        .then()
            .statusCode(201)
            .body("id", notNullValue());
    }
}
```

## Best Practices

1. **Organize Tests** - Group related tests into test classes
2. **Use Base URIs** - Configure base URIs in your test setup
3. **Reusable Methods** - Create helper methods for common operations
4. **Response Validation** - Always validate status codes and response bodies
5. **Test Data** - Use external files or fixtures for test data
6. **Error Handling** - Test both success and failure scenarios
7. **Assertions** - Use Hamcrest matchers for clear assertions

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is open source and available under the [MIT License](LICENSE).

## Author

**Phila** - [GitHub Profile](https://github.com/Phila12-hub)

## Support

For questions or issues, please open an issue on the [GitHub repository](https://github.com/Phila12-hub/RestAssuredAss2/issues).

---

**Last Updated:** May 8, 2026

