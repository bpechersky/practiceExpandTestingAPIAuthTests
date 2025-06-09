package com.expandtesting.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;

public class TestBase {
    protected String token;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://practice.expandtesting.com/notes/api";
        token = getToken("bp1967@gmail.com", "Budman1967");
        System.out.println("🔐 Fetched token: " + token); // Add debug log
    }

    public String getToken(String email, String password) {
        Response response = given()
                .contentType("application/json")
                .body("{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}")
                .post("/users/login");

        response.then().statusCode(200).log().all();

        return response.jsonPath().getString("data.token"); // ✅ Correct path
    }

    protected String loginAndGetToken() {
        Response response = given()
                .baseUri("https://practice.expandtesting.com/notes/api")
                .header("Content-Type", "application/json")
                .body("{ \"email\": \"bp1967@gmail.com\", \"password\": \"Budman1967\" }")
                .when()
                .post("/users/login")
                .then()
                .statusCode(200)
                .extract()
                .response();

        return response.jsonPath().getString("data.token");
    }

    public static String createNoteAndReturnId(String token, String title, String body) {
        return given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body("{\"title\": \"" + title + "\", \"description\": \"" + body + "\"}")
                .when()
                .post("https://practice.expandtesting.com/notes/api/notes")
                .then()
                .statusCode(201)
                .extract()
                .path("data._id");
    }



}
