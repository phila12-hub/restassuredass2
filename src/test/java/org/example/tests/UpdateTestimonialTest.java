package org.example.tests;

import org.example.base.BaseTest;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

/**
 * UpdateTestimonialTest - Tests updating an existing testimonial (positive scenario).
 *
 * Endpoint : PUT /testimonials/{id}
 * Auth     : Bearer token required
 * Purpose  : Verifies that an authenticated user can successfully update
 *            a testimonial that was previously created. The testimonial ID
 *            used here is the one stored during the CreateTestimonialTest.
 *
 * Postman equivalent: "UpdateTestimonal" request
 */
public class UpdateTestimonialTest extends BaseTest {

    /**
     * Test: Update an existing testimonial using its ID and a valid auth token.
     *
     * Steps:
     *  1. Send a PUT request to /testimonials/{testimonialId} with updated data
     *  2. Assert status code is 200 (OK)
     *  3. Assert success field is true
     *  4. Assert message is "Testimonial updated successfully"
     *
     * Note: This test depends on CreateTestimonialTest running first (priority 2)
     *       so that the testimonialId variable is populated.
     */
    @Test(priority = 3, description = "Update an existing testimonial and verify response")
    public void testUpdateTestimonial() {

        // Build the JSON request body with updated testimonial details
        String requestBody = "{"
                + "\"title\": \"Updated Phila Ndosi\","
                + "\"content\": \"Updated Phila Ndosi\","
                + "\"rating\": 5"
                + "}";

        // Send an authenticated PUT request using the stored testimonialId
        authenticatedRequest()
                .body(requestBody)
                .when()
                // Append the stored testimonial ID to the endpoint URL
                .put(TESTIMONIALS_ENDPOINT + "/" + testimonialId)
                .then()
                // Assert: Status code should be 200 - update was successful
                .statusCode(200)
                // Assert: success field should be true
                .body("success", equalTo(true))
                // Assert: message should confirm the testimonial was updated
                .body("message", equalTo("Testimonial updated successfully"));

        System.out.println("Testimonial ID " + testimonialId + " updated successfully.");
    }
}

