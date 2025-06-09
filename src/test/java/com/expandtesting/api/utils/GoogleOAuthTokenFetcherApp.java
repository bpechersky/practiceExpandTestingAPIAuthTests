package com.expandtesting.api.utils;

import com.expandtesting.api.utils.GoogleOAuthTokenProvider;



public class GoogleOAuthTokenFetcherApp {
    public static void main(String[] args) {
        try {
            String token = GoogleOAuthTokenProvider.getAccessToken();
            System.out.println("✅ Access Token:\n" + token);
        } catch (Exception e) {
            System.err.println("❌ Failed to fetch access token:");
            e.printStackTrace();
        }
    }
}
