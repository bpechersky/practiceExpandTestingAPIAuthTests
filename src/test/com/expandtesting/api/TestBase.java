package com.expandtesting.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;

public class TestBase {
    protected String token;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://practice.expandtesting.com/notes/api";
        token = getToken("bp1967@gmail.com", "Budman1967");
        System.out.println("🔐 Fetched token: " + token); // Add debug log
    }

    public String getToken(String email, String password) {
        Response response = RestAssured
                .given()
                .contentType("application/json")
                .body("{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}")
                .post("/users/login");

        response.then().statusCode(200).log().all();

        return response.jsonPath().getString("data.token"); // ✅ Correct path
    }
}
