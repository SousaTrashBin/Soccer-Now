package com.soccernow.ui.soccernowui.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.soccernow.ui.soccernowui.dto.games.GameDTO;
import com.soccernow.ui.soccernowui.dto.games.PlayerGameStatsDTO;
import com.soccernow.ui.soccernowui.dto.user.PlayerDTO;
import com.soccernow.ui.soccernowui.util.ErrorException;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.soccernow.ui.soccernowui.util.ApiUtils.*;


public enum GameApiController {
    INSTANCE;
    private final String gamesURL = baseURL + "games/";

    public GameDTO getGameById(UUID gameId) throws IOException, ErrorException {
        try (Response response = getRequest(gamesURL + gameId)) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Get game by ID failed");
            }
            String json = response.body().string();
            return getObjectMapper().readValue(json, GameDTO.class);
        }
    }

    public GameDTO registerGame(GameDTO game) throws IOException, ErrorException {
        try (Response response = postJsonRequest(gamesURL, game)) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Register game failed");
            }
            String json = response.body().string();
            return getObjectMapper().readValue(json, GameDTO.class);
        }
    }

    public GameDTO closeGameById(UUID gameId, Set<PlayerGameStatsDTO> stats) throws IOException, ErrorException {
        stats = stats != null ? stats : new HashSet<>();
        try (Response response = postJsonRequest(gamesURL + gameId + "/close", stats)) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Close game failed");
            }
            String json = response.body().string();
            return getObjectMapper().readValue(json, GameDTO.class);
        }
    }

    public List<GameDTO> getAllGames() throws IOException, ErrorException {
        try (Response response = getRequest(gamesURL)) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Get all games failed");
            }
            String json = response.body().string();
            return getObjectMapper().readValue(json, getObjectMapper().getTypeFactory().constructCollectionType(List.class, GameDTO.class));
        }
    }

    public GameDTO cancelGameById(UUID gameId) throws IOException, ErrorException {
        try (Response response = patchRequest(gamesURL + gameId + "/cancel")) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Cancel game failed");
            }
            String json = response.body().string();
            return getObjectMapper().readValue(json, GameDTO.class);
        }
    }
}
