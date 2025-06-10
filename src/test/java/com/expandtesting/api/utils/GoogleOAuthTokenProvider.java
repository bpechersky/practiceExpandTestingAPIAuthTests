package com.expandtesting.api.utils;

import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class GoogleOAuthTokenProvider {

    public static String getAccessToken() throws IOException {
        GoogleCredentials credentials = GoogleCredentials
                .fromStream(new FileInputStream("src/test/resources/service-account.json"))
                .createScoped(List.of("https://www.googleapis.com/auth/userinfo.email"))
                .createScoped(List.of("https://www.googleapis.com/auth/drive"));

        credentials.refreshIfExpired();
        return credentials.getAccessToken().getTokenValue();  // ✅ Return the token here
    }
}
