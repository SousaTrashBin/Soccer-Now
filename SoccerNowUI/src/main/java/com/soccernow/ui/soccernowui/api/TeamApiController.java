package com.soccernow.ui.soccernowui.api;

import com.soccernow.ui.soccernowui.dto.TeamDTO;
import com.soccernow.ui.soccernowui.util.ErrorException;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.soccernow.ui.soccernowui.util.ApiUtils.*;

public enum TeamApiController {
    INSTANCE;
    private final String teamsURL = baseURL + "teams/";

    public TeamDTO registerTeam(TeamDTO teamDTO) throws IOException, ErrorException {
        try (Response response = postJsonRequest(teamsURL, teamDTO)) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Register team failed");
            }
            String json = response.body().string();
            return getObjectMapper().readValue(json, TeamDTO.class);
        }
    }

    public TeamDTO getTeamById(UUID teamId) throws IOException, ErrorException {
        try (Response response = getRequest(teamsURL + teamId)) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Get team by ID failed");
            }
            String json = response.body().string();
            return getObjectMapper().readValue(json, TeamDTO.class);
        }
    }

    public List<TeamDTO> getAllTeams() throws IOException, ErrorException {
        try (Response response = getRequest(teamsURL)) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Get all teams failed");
            }
            String json = response.body().string();
            return getObjectMapper().readValue(json, getObjectMapper().getTypeFactory().constructCollectionType(List.class, TeamDTO.class));
        }
    }

    public TeamDTO updateTeamById(UUID teamId, TeamDTO teamDTO) throws IOException, ErrorException {
        try (Response response = putJsonRequest(teamsURL + teamId, teamDTO)) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Update team failed");
            }
            String json = response.body().string();
            return getObjectMapper().readValue(json, TeamDTO.class);
        }
    }

    public void deleteTeamById(UUID teamId) throws IOException, ErrorException {
        try (Response response = deleteRequest(teamsURL + teamId)) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Delete team failed");
            }
        }
    }

    public void addPlayerToTeam(UUID teamId, UUID playerId) throws IOException, ErrorException {
        try (Response response = postJsonRequest(teamsURL + teamId + "/players/" + playerId, null)) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Add player to team failed");
            }
        }
    }

    public void removePlayerFromTeam(UUID teamId, UUID playerId) throws IOException, ErrorException {
        try (Response response = deleteRequest(teamsURL + teamId + "/players/" + playerId)) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Remove player from team failed");
            }
        }
    }
}
