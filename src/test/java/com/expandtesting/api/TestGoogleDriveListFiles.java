package com.expandtesting.api;

import com.expandtesting.api.utils.GoogleOAuthTokenProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

public class TestGoogleDriveListFiles {

    @Test
    public void testGoogleDriveListFiles() throws IOException {
        String accessToken = GoogleOAuthTokenProvider.getAccessToken();

        Response response = RestAssured
                .given()
                .baseUri("https://www.googleapis.com")
                .header("Authorization", "Bearer " + accessToken)
                .get("/drive/v3/files");

        response.then().log().all()
                .statusCode(200)
                .body("files", notNullValue());
    }

    @Test
    public void testUploadFileToDrive() throws IOException {
        String accessToken = GoogleOAuthTokenProvider.getAccessToken();

        Response response = RestAssured
                .given()
                .baseUri("https://www.googleapis.com")
                .header("Authorization", "Bearer " + accessToken)
                .multiPart("metadata", "{ \"name\": \"TestFile.txt\" }", "application/json")
                .multiPart("file", "Hello world from API", "text/plain")
                .post("/upload/drive/v3/files?uploadType=multipart");

        response.then().log().all()
                .statusCode(200)
                .body("name", equalTo("TestFile.txt"));
    }

}
