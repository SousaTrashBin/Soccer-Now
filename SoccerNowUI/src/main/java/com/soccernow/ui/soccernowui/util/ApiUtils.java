package com.soccernow.ui.soccernowui.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

public class ApiUtils {
    public static final String baseURL = "localhost:8080/";
    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public record ApiError(int status, String message, Map<String, String> validationErrors) {}

    public static Response postJsonRequest(String url, Object bodyObject) throws IOException {
        String json = objectMapper.writeValueAsString(bodyObject);

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
        String json = objectMapper.writeValueAsString(bodyObject);

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
        return objectMapper;
    }

}
