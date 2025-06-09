package com.expandtesting.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class NotesTests {

    private final String BASE_URI = "https://practice.expandtesting.com/notes/api";

    public String loginAndGetToken() {

        Response response = given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body("{\"email\":\"bp1967@gmail.com\", \"password\":\"Budman1967\"}")
                .when()
                .post("/users/login");

        return response.then()
                .statusCode(200)
                .extract()
                .path("data.token");
    }

    @Test
    public void testCreateGetDeleteNote() {
        String token = loginAndGetToken();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        System.out.println("Token: " + token);

        Response createResponse = given()
                .baseUri(BASE_URI)
                .header("x-auth-token", token)
                .contentType(ContentType.JSON)
                .body("{\"title\":\"API Note Title\","
                        + "\"description\":\"API Note Description\","
                        + "\"category\":\"Work\"}")  // <== Add valid category
                .when()
                .post("/notes");

        createResponse.then()
                .statusCode(200)
                .body("data.title", equalTo("API Note Title"))
                .body("data.description", equalTo("API Note Description"))
                .body("data.category", equalTo("Work"));

        String noteId = createResponse.path("data.id");
        System.out.println("📌 Created Note ID: " + noteId);


        // Step 2: Get note by ID
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        given()
                .baseUri(BASE_URI)
                .header("x-auth-token", token)
                .when()
                .get("/notes/" + noteId)
                .then()
                .statusCode(200)
                .body("data.id", equalTo(noteId))
                .body("data.title", equalTo("API Note Title"));

        // Step 3: Delete note by ID
        given()
                .baseUri(BASE_URI)
                .header("x-auth-token", token)
                .when()
                .delete("/notes/" + noteId)
                .then()
                .statusCode(200)
                .body("message", containsString("Note successfully deleted"));
    }
}
