package org.example.tests;

import io.restassured.response.Response;
import org.example.base.BaseTest;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

/**
 * CreateTestimonialTest - Tests creating a new testimonial (positive scenario).
 *
 * Endpoint : POST /testimonials
 * Auth     : Bearer token required
 * Purpose  : Verifies that an authenticated user can successfully create
 *            a testimonial, and that the API returns the correct status,
 *            success flag, and message. The newly created testimonial ID
 *            is stored for use in subsequent update and delete tests.
 *
 * Postman equivalent: "CreateTestimonal" request
 */
public class CreateTestimonialTest extends BaseTest {

    /**
     * Test: Create a testimonial with valid data and a valid auth token.
     *
     * Steps:
     *  1. Send a POST request to /testimonials with a valid Bearer token
     *  2. Assert status code is 201 (Created)
     *  3. Assert success field is true
     *  4. Assert message is "Testimonial created successfully"
     *  5. Extract and store the created testimonial ID for later tests
     */
    @Test(priority = 2, description = "Create a testimonial with valid credentials and verify response")
    public void testCreateTestimonial() {

        // Build the JSON request body with testimonial details
        String requestBody = "{"
                + "\"title\": \"Ndosi Is the best\","
                + "\"content\": \"Ndosi content\","
                + "\"rating\": 5,"
                + "\"isPublic\": true"
                + "}";

        // Send an authenticated POST request and capture the response
        Response response = authenticatedRequest()
                .body(requestBody)
                .when()
                .post(TESTIMONIALS_ENDPOINT)
                .then()
                // Assert: Status code should be 201 - resource was created successfully
                .statusCode(201)
                // Assert: success field in the response body should be true
                .body("success", equalTo(true))
                // Assert: message should confirm the testimonial was created
                .body("message", equalTo("Testimonial created successfully"))
                // Assert: the returned ID should not be null
                .body("data.Id", notNullValue())
                .extract()
                .response();

        // Extract and store the testimonial ID so update and delete tests can use it
        testimonialId = response.jsonPath().getInt("data.Id");

        System.out.println("Created Testimonial ID: " + testimonialId);
    }
}

