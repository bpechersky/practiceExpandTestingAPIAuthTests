package com.expandtesting.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class AuthTests extends com.expandtesting.api.TestBase {

    @Test
    public void testLoginReturnsToken() {
        Response response = RestAssured
                .given()
                .contentType("application/json")
                .body("{\"email\": \"bp1967@gmail.com\", \"password\": \"Budman1967\"}")
                .when()
                .post("https://practice.expandtesting.com/notes/api/users/login");

        // Print response for debugging
        response.then().log().all();

        // Now do your assertion
        response.then()
                .statusCode(200)
                .body("data.token", notNullValue());
    }


}
