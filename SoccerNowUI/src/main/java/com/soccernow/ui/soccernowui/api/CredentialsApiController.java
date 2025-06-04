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

    private void login(LoginRequest loginRequest) throws IOException {
        try (Response response = ApiUtils.postJsonRequest(credentialsURL + "login", loginRequest)) {
            if (!response.isSuccessful()) {
                String errorBody = response.body().string();
                try {
                    ApiUtils.ApiError apiError = ApiUtils.getObjectMapper().readValue(errorBody, ApiUtils.ApiError.class);
                    throw new ErrorException("Login failed: " + apiError.toString(), response.code());
                } catch (Exception jsonException) {
                    throw new ErrorException("Login failed with status code: " + response.code() + ". Response: " + errorBody, response.code());
                }
            }
        }
    }
}
