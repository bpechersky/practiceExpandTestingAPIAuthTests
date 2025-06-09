package com.expandtesting.api.utils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.ServiceAccountCredentials;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class GoogleOAuthTokenProvider {

    public static String getAccessToken() throws IOException {
        GoogleCredentials credentials = ServiceAccountCredentials
                .fromStream(new FileInputStream("src/test/resources/google-service-account.json"))
                .createScoped(List.of("https://www.googleapis.com/auth/userinfo.email")); // scope depends on what you're testing

        credentials.refreshIfExpired();
        AccessToken token = credentials.getAccessToken();
        return token.getTokenValue();
    }

    @Test
    public void testRetrieveToken() throws IOException {
        String token = getAccessToken();
        System.out.println("🔐 Access token: " + token);
    }
}
