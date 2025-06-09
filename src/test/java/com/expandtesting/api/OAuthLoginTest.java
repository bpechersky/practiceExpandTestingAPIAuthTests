package com.expandtesting.api;


import com.expandtesting.api.utils.GoogleOAuthTokenProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.*;

public class OAuthLoginTest {

    @Test
    public void testGoogleOAuthUserInfo() throws IOException {
        String accessToken = GoogleOAuthTokenProvider.getAccessToken();

        Response response = RestAssured
                .given()
                .baseUri("https://www.googleapis.com")
                .header("Authorization", "Bearer " + accessToken)
                .get("/oauth2/v3/userinfo");

        response.then().log().all()
                .statusCode(200)
                .body("email", containsString("@"));
    }
}
