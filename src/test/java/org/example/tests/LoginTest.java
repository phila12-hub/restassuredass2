package org.example.tests;

import io.restassured.response.Response;
import org.example.base.BaseTest;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

/**
 * LoginTest - Tests the user authentication endpoint.
 *
 * Endpoint : POST /login
 * Purpose  : Authenticates the admin user and retrieves a JWT Bearer token.
 *            The token is stored in the BaseTest.authToken field and shared
 *            with all subsequent tests that require authorisation.
 *
 * Postman equivalent: "LoginUser" request
 */
public class LoginTest extends BaseTest {

    /**
     * Test: Successful admin login.
     *
     * Steps:
     *  1. Send a POST request to /login with admin credentials
     *  2. Assert the response status code is 200 (OK)
     *  3. Assert the response body contains a token under data.token
     *  4. Extract and store the token for use in subsequent authenticated tests
     */
    @Test(priority = 1, description = "Login with valid admin credentials and extract auth token")
    public void testLoginUser() {

        // Build the JSON request body with admin credentials
        String requestBody = "{"
                + "\"email\": \"admin@gmail.com\","
                + "\"password\": \"@12345678\""
                + "}";

        // Send POST request to the login endpoint and capture the response
        Response response = baseRequest()
                .body(requestBody)
                .when()
                .post(LOGIN_ENDPOINT)
                .then()
                // Assert that the login was successful with status 200
                .statusCode(200)
                // Assert the token field is present and not empty
                .body("data.token", notNullValue())
                .extract()
                .response();

        // Extract the JWT token from the response and store it in the shared variable
        // This token will be used by all tests that need authorisation
        authToken = response.jsonPath().getString("data.token");

        System.out.println("Auth Token extracted: " + authToken);
    }
}

