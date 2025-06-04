package com.soccernow.ui.soccernowui.api;

import com.soccernow.ui.soccernowui.util.ApiUtils;
import com.soccernow.ui.soccernowui.util.ErrorException;
import jakarta.validation.constraints.NotNull;
import okhttp3.Response;

import java.io.IOException;

import static com.soccernow.ui.soccernowui.util.ApiUtils.baseURL;

public enum CredentialsApiController {
    INSTANCE;
    private final String credentialsURL = baseURL + "credentials/";

    public record LoginRequest(@NotNull String username, @NotNull String password) {}

    private void login(LoginRequest loginRequest) throws IOException, ErrorException {
        try (Response response = ApiUtils.postJsonRequest(credentialsURL + "login", loginRequest)) {
            if (!response.isSuccessful()) {
                ApiUtils.throwApiException(response, "Login failed");
            }
        }
    }

}
