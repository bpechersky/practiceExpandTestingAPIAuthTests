package com.expandtesting.api;

import com.expandtesting.api.utils.GoogleOAuthTokenProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.*;

public class AnalyticsHubTests {

    private static final String BASE_URL = "https://analyticshub.googleapis.com/v1";

    @Test
    public void testListDataExchanges() throws IOException {
        String accessToken = GoogleOAuthTokenProvider.getAccessToken();

        String projectId = "hybrid-saga-426219-k7";
        String location = "us"; // or "us-central1" if that’s where your exchanges live

        String fullPath = String.format("projects/%s/locations/%s", projectId, location);

        Response response = RestAssured
                .given()
                .baseUri("https://analyticshub.googleapis.com/v1")
                .header("Authorization", "Bearer " + accessToken)
                .get("/" + fullPath + "/dataExchanges");

        response.then().log().all()
                .statusCode(200)
                .body("dataExchanges", notNullValue()); // Will be empty array if no data exchanges exist yet
    }

    @Test
    public void testCreateDataExchange() throws IOException {
        String accessToken = GoogleOAuthTokenProvider.getAccessToken();
        String projectId = "hybrid-saga-426219-k7";
        String location = "us"; // Change if you're using another region

        String parent = String.format("projects/%s/locations/%s", projectId, location);
        String dataExchangeId = "test_exchange_" + System.currentTimeMillis(); // ensure uniqueness

        String requestBody = """
        {
          "displayName": "My Test Exchange",
          "description": "Created via automated test",
          "primaryContact": "test@example.com"
        }
    """;

        Response response = RestAssured
                .given()
                .baseUri("https://analyticshub.googleapis.com/v1")
                .header("Authorization", "Bearer " + accessToken)
                .contentType("application/json")
                .body(requestBody)
                .post("/" + parent + "/dataExchanges?dataExchangeId=" + dataExchangeId);

        response.then().log().all()
                .statusCode(200)
                .body("name", containsString(dataExchangeId))
                .body("displayName", equalTo("My Test Exchange"));
    }


}
