package org.example.base;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;

/**
 * BaseTest class - shared configuration for all test classes.
 *
 * This class sets up:
 * - The base URI pointing to the Ndosi Automation API
 * - A reusable authenticated request specification
 * - Logging filters for request and response visibility during test runs
 *
 * All test classes should extend this class to inherit these configurations.
 */
public class BaseTest {

    // Base URL for the Ndosi Automation API
    protected static final String BASE_URL = "https://www.ndosiautomation.co.za/APIDEV";

    // Testimonials endpoint path
    protected static final String TESTIMONIALS_ENDPOINT = "/testimonials";

    // Login endpoint path
    protected static final String LOGIN_ENDPOINT = "/login";

    // Stores the JWT auth token after a successful login - shared across all tests
    protected static String authToken;

    // Stores the ID of the created testimonial - used for update and delete operations
    protected static int testimonialId;

    /**
     * Configures the base URI before any tests in the class run.
     * Also attaches request and response logging filters for debugging.
     * can reach external HTTPS endpoints from within the corporate network.
     */
    @BeforeClass
    public void setUp() {
        // Set the base URI so all requests use this as the root URL
        RestAssured.baseURI = BASE_URL;

        // Tell the JVM to automatically detect and use the operating system's proxy
        // settings (the same proxy that PowerShell and browsers use).
        // This avoids hardcoding any proxy details in the code.
        System.setProperty("java.net.useSystemProxies", "true");

        // Add logging filters to print request and response details to the console
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    /**
     * Returns a base RequestSpecification with JSON content type pre-configured.
     * Used as a starting point for building requests in test classes.
     *
     * @return RequestSpecification with Content-Type set to application/json
     */
    protected RequestSpecification baseRequest() {
        return given()
                // Disable SSL certificate validation - required because the corporate
                // proxy intercepts HTTPS traffic using its own self-signed certificate
                // which Java's truststore does not recognise
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON);
    }

    protected RequestSpecification authenticatedRequest() {
        return given()
                // Disable SSL certificate validation - same reason as baseRequest()
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + authToken);
    }
}

