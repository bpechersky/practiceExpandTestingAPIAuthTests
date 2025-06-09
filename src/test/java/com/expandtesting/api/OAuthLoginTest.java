package com.expandtesting.api;


import com.expandtesting.api.utils.GoogleOAuthTokenProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.*;

public class OAuthLoginTest {

    @Test
    public void testGoogleOAuthLogin() throws IOException {
        String oauthToken = GoogleOAuthTokenProvider.getAccessToken();

        Response response = RestAssured
                .given()
                .baseUri("https://your-api-domain.com")  // Replace with actual API
                .contentType("application/json")
                .body("{ \"provider\": \"google\", \"token\": \"" + oauthToken + "\" }")
                .post("/auth/oauth");

        response.then().log().all()
                .statusCode(200)
                .body("data.token", notNullValue())
                .body("data.email", containsString("@gmail.com"));
    }
}
