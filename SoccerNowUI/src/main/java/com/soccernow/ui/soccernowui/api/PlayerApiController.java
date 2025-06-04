package com.soccernow.ui.soccernowui.api;

import com.soccernow.ui.soccernowui.dto.user.PlayerDTO;
import com.soccernow.ui.soccernowui.util.ApiUtils;
import com.soccernow.ui.soccernowui.util.ErrorException;
import jakarta.validation.constraints.NotNull;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.soccernow.ui.soccernowui.util.ApiUtils.baseURL;
import static com.soccernow.ui.soccernowui.util.ApiUtils.throwApiException;

public enum PlayerApiController {
    INSTANCE;
    private final String playersURL = baseURL + "players/";

    public PlayerDTO registerPlayer(@NotNull PlayerDTO player) throws IOException, ErrorException {
        try (Response response = ApiUtils.postJsonRequest(playersURL, player)) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Register player failed");
            }
            String json = response.body().string();
            return ApiUtils.getObjectMapper().readValue(json, PlayerDTO.class);
        }
    }

    public PlayerDTO getPlayerById(@NotNull UUID playerId) throws IOException, ErrorException {
        try (Response response = ApiUtils.getRequest(playersURL + playerId.toString())) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Get player by ID failed");
            }
            String json = response.body().string();
            return ApiUtils.getObjectMapper().readValue(json, PlayerDTO.class);
        }
    }

    public List<PlayerDTO> getAllPlayers() throws IOException, ErrorException {
        try (Response response = ApiUtils.getRequest(playersURL)) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Get all players failed");
            }
            String json = response.body().string();
            return ApiUtils.getObjectMapper().readValue(json, ApiUtils.getObjectMapper().getTypeFactory().constructCollectionType(List.class, PlayerDTO.class));
        }
    }

    public void deletePlayerById(@NotNull UUID playerId) throws IOException, ErrorException {
        try (Response response = ApiUtils.deleteRequest(playersURL + playerId.toString())) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Delete player failed");
            }
        }
    }

    public PlayerDTO updatePlayerById(@NotNull UUID playerId, @NotNull PlayerDTO playerDTO) throws IOException, ErrorException {
        try (Response response = ApiUtils.putJsonRequest(playersURL + playerId, playerDTO)) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Update player failed");
            }
            String json = response.body().string();
            return ApiUtils.getObjectMapper().readValue(json, PlayerDTO.class);
        }
    }
}
