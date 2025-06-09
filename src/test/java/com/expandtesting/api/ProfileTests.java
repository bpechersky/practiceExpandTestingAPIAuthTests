package com.expandtesting.api;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class ProfileTests extends TestBase {

    @Test
    public void testGetProfile() {
        RestAssured
                .given()
                .header("x-auth-token", token) // ✅ token is now set
                .when()
                .get("/users/profile")
                .then()
                .statusCode(200)
                .body("data.email", equalTo("bp1967@gmail.com"));
    }
}
