package com.soccernow.ui.soccernowui.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

public class ApiUtils {
    public static final String baseURL = "http://localhost:8080/api/";
    private static final OkHttpClient client = new OkHttpClient();
    private static ObjectMapper objectMapper;

    public record ApiError(int status, String message, Map<String, String> validationErrors) {}

    public static Response postJsonRequest(String url, Object bodyObject) throws IOException {
        String json = getObjectMapper().writeValueAsString(bodyObject);

        RequestBody body = RequestBody.create(
                json,
                MediaType.get("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        return client.newCall(request).execute();
    }

    public static Response putJsonRequest(String url, Object bodyObject) throws IOException {
        String json = getObjectMapper().writeValueAsString(bodyObject);

        RequestBody body = RequestBody.create(
                json,
                MediaType.get("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();

        return client.newCall(request).execute();
    }

    public static Response getRequest(String url) throws IOException {

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        return client.newCall(request).execute();
    }

    public static Response patchRequest(String url) throws IOException {
        RequestBody emptyBody = RequestBody.create(new byte[0], null);

        Request request = new Request.Builder()
                .url(url)
                .patch(emptyBody)
                .build();

        return client.newCall(request).execute();
    }

    public static Response deleteRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();

        return client.newCall(request).execute();
    }

    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        }
        return objectMapper;
    }

    public static void throwApiException(Response response, String message) throws IOException, ErrorException {
        String errorBody = response.body().string();
        try {
            ApiUtils.ApiError apiError = ApiUtils.getObjectMapper().readValue(errorBody, ApiUtils.ApiError.class);
            throw new ErrorException(message + ": " + apiError.toString(), response.code());
        } catch (Exception e) {
            throw new ErrorException(message + " with status code: " + response.code() + ". Response: " + errorBody, response.code());
        }
    }
}
