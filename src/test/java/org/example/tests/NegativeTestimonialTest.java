package org.example.tests;

import io.restassured.RestAssured;
import org.example.base.BaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * NegativeTestimonialTest - Tests negative/error scenarios for the testimonials API.
 *
 * This class covers two negative test cases from the Postman collection:
 *
 * 1. CreateTestimonal negative - Attempts to create a testimonial with an
 *    invalid/expired token and expects a 401 Unauthorised response.
 *
 * 2. UpdateTestimonal Negative - Attempts to update a testimonial using a
 *    non-existent/dummy ID and expects a 404 Not Found response.
 *
 * Negative tests are important because they verify the API handles
 * bad input and unauthorised access gracefully with meaningful error messages.
 */
public class NegativeTestimonialTest extends BaseTest {

    /**
     * Test: Attempt to create a testimonial with an invalid/expired Bearer token.
     *
     * Postman equivalent: "CreateTestimonal negitive"
     *
     * Steps:
     *  1. Send a POST request to /testimonials with an invalid token in the header
     *  2. Assert status code is 401 (Unauthorised)
     *  3. Assert success field is false
     *  4. Assert message is "Invalid or expired token"
     *
     * Expected behaviour: The API should reject the request because the
     * token is invalid, preventing unauthorised testimonial creation.
     */
    @Test(priority = 5, description = "Create testimonial with invalid token - expect 401 Unauthorised")
    public void testCreateTestimonialWithInvalidToken() {

        // Build the JSON request body (same as the positive create test)
        String requestBody = "{"
                + "\"title\": \"Ndosi Is the best\","
                + "\"content\": \"Ndosi content\","
                + "\"rating\": 5,"
                + "\"isPublic\": true"
                + "}";

        // Send a POST request with a deliberately invalid Bearer token
        // This simulates a scenario where a token has expired or is tampered with
        given()
                .relaxedHTTPSValidation()
                .contentType("application/json")
                // Use an invalid token to trigger a 401 Unauthorised response
                .header("Authorization", "Bearer invalid_or_expired_token")
                .body(requestBody)
                .when()
                .post(TESTIMONIALS_ENDPOINT)
                .then()
                // Assert: Status code should be 401 - request is not authorised
                .statusCode(401)
                // Assert: success field should be false indicating the failure
                .body("success", equalTo(false))
                // Assert: message should inform the client the token is invalid/expired
                .body("message", equalTo("Invalid or expired token"));

        System.out.println("Negative test passed: Invalid token correctly rejected with 401.");
    }

    /**
     * Test: Attempt to update a testimonial using a non-existent/dummy ID.
     *
     * Postman equivalent: "UpdateTestimonal Negative"
     *
     * Steps:
     *  1. Send a PUT request to /testimonials/setting dummy id (non-existent ID)
     *  2. Assert status code is 404 (Not Found)
     *  3. Assert success field is false
     *  4. Assert message is "Testimonial not found"
     *
     * Expected behaviour: The API should return 404 when the requested
     * testimonial ID does not exist in the system.
     */
    @Test(priority = 6, description = "Update testimonial with non-existent ID - expect 404 Not Found")
    public void testUpdateTestimonialWithInvalidId() {

        // Define a dummy/non-existent testimonial ID to trigger a 404 response
        // This simulates a case where the client sends an ID that doesn't exist
        String dummyId = "setting dummy id";

        // Build the JSON request body with update data
        String requestBody = "{"
                + "\"title\": \"Updated Phila Ndosi\","
                + "\"content\": \"Updated Phila Ndosi\","
                + "\"rating\": 5"
                + "}";

        // Send an authenticated PUT request with the dummy/non-existent ID
        authenticatedRequest()
                .body(requestBody)
                .when()
                // Use the dummy ID - this should not match any testimonial in the DB
                .put(TESTIMONIALS_ENDPOINT + "/" + dummyId)
                .then()
                // Assert: Status code should be 404 - testimonial was not found
                .statusCode(404)
                // Assert: success field should be false indicating the failure
                .body("success", equalTo(false))
                // Assert: message should inform the client the testimonial doesn't exist
                .body("message", equalTo("Testimonial not found"));

        System.out.println("Negative test passed: Non-existent ID correctly returned 404.");
    }
}

