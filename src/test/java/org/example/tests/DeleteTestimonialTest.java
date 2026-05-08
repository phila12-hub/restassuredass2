package org.example.tests;

import org.example.base.BaseTest;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

/**
 * DeleteTestimonialTest - Tests deleting an existing testimonial (positive scenario).
 *
 * Endpoint : DELETE /testimonials/{id}
 * Auth     : Bearer token required
 * Purpose  : Verifies that an authenticated user can successfully delete
 *            a testimonial that was previously created. The testimonial ID
 *            used here is the one stored during the CreateTestimonialTest.
 *
 * Postman equivalent: "DeleteTestimonal" request
 */
public class DeleteTestimonialTest extends BaseTest {

    /**
     * Test: Delete an existing testimonial using its ID and a valid auth token.
     *
     * Steps:
     *  1. Send a DELETE request to /testimonials/{testimonialId}
     *  2. Assert status code is 200 (OK)
     *  3. Assert success field is true
     *  4. Assert message is "Testimonial deleted successfully"
     *
     * Note: This test depends on CreateTestimonialTest running first (priority 2)
     *       so that the testimonialId variable is populated.
     */
    @Test(priority = 4, description = "Delete an existing testimonial and verify response")
    public void testDeleteTestimonial() {

        // Send an authenticated DELETE request using the stored testimonialId
        authenticatedRequest()
                .when()
                // Append the stored testimonial ID to the endpoint URL
                .delete(TESTIMONIALS_ENDPOINT + "/" + testimonialId)
                .then()
                // Assert: Status code should be 200 - deletion was successful
                .statusCode(200)
                // Assert: success field should be true
                .body("success", equalTo(true))
                // Assert: message should confirm the testimonial was deleted
                .body("message", equalTo("Testimonial deleted successfully"));

        System.out.println("Testimonial ID " + testimonialId + " deleted successfully.");
    }
}

