package com.expandtesting.api.utils;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;

public class GoogleServiceAccountTokenFetcher {

    // Path to your downloaded JSON key file
    private static final String SERVICE_ACCOUNT_KEY_PATH = "src/test/resources/service-account.json";

    // Scope for the resource you're trying to access
    private static final String SCOPE = "https://www.googleapis.com/auth/cloud-platform";

    public static String fetchAccessToken() throws IOException {
        // Load the service account key file
        GoogleCredentials credentials;
        try (FileInputStream serviceAccountStream = new FileInputStream(SERVICE_ACCOUNT_KEY_PATH)) {
            credentials = GoogleCredentials.fromStream(serviceAccountStream)
                    .createScoped(Collections.singletonList(SCOPE));
        }

        // Refresh to get access token
        credentials.refreshIfExpired();
        AccessToken token = credentials.getAccessToken();

        return token.getTokenValue();
    }
}
