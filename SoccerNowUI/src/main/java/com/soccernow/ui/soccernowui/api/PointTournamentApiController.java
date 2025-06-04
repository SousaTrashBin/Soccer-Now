package com.soccernow.ui.soccernowui.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.soccernow.ui.soccernowui.dto.tournament.PointTournamentDTO;
import com.soccernow.ui.soccernowui.dto.user.PlayerDTO;
import jakarta.validation.constraints.NotNull;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.soccernow.ui.soccernowui.util.ApiUtils.*;


public enum PointTournamentApiController {
    INSTANCE;
    private final String pointTournamentsURL = baseURL + "point-tournaments/";

    public PointTournamentDTO getTournamentById(UUID tournamentId) throws IOException {
        try (Response response = getRequest(pointTournamentsURL + tournamentId)) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Get tournament by ID failed");
            }
            String json = response.body().string();
            return getObjectMapper().readValue(json, PointTournamentDTO.class);
        }
    }

    public PointTournamentDTO createTournament(String name) throws IOException {
        var createPayload = new CreatePointTournamentDTO(name);
        try (Response response = postJsonRequest(pointTournamentsURL, createPayload)) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Create tournament failed");
            }
            String json = response.body().string();
            return getObjectMapper().readValue(json, PointTournamentDTO.class);
        }
    }

    public PointTournamentDTO closeRegistrations(UUID tournamentId) throws IOException {
        try (Response response = patchRequest(pointTournamentsURL + tournamentId + "/close-registrations")) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Close tournament registrations failed");
            }
            String json = response.body().string();
            return getObjectMapper().readValue(json, PointTournamentDTO.class);
        }
    }

    public PointTournamentDTO endTournament(UUID tournamentId) throws IOException {
        try (Response response = patchRequest(pointTournamentsURL + tournamentId + "/end")) {
            if (!response.isSuccessful()) {
                throwApiException(response, "End tournament failed");
            }
            String json = response.body().string();
            return getObjectMapper().readValue(json, PointTournamentDTO.class);
        }
    }

    public List<PointTournamentDTO> getAllTournaments() throws IOException {
        try (Response response = getRequest(pointTournamentsURL)) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Get all tournaments failed");
            }
            String json = response.body().string();
            return getObjectMapper().readValue(json, getObjectMapper().getTypeFactory().constructCollectionType(List.class, PointTournamentDTO.class));
        }
    }

    public PointTournamentDTO addGameToTournament(UUID tournamentId, UUID gameId) throws IOException {
        try (Response response = postJsonRequest(pointTournamentsURL + tournamentId + "/games/" + gameId, null)) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Add game to tournament failed");
            }
            String json = response.body().string();
            return getObjectMapper().readValue(json, PointTournamentDTO.class);
        }
    }

    public PointTournamentDTO removeGameFromTournament(UUID tournamentId, UUID gameId) throws IOException {
        try (Response response = deleteRequest(pointTournamentsURL + tournamentId + "/games/" + gameId)) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Remove game from tournament failed");
            }
            String json = response.body().string();
            return getObjectMapper().readValue(json, PointTournamentDTO.class);
        }
    }

    public PointTournamentDTO addTeamToTournament(UUID tournamentId, UUID teamId) throws IOException {
        try (Response response = postJsonRequest(pointTournamentsURL + tournamentId + "/teams/" + teamId, null)) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Add team to tournament failed");
            }
            String json = response.body().string();
            return getObjectMapper().readValue(json, PointTournamentDTO.class);
        }
    }

    public PointTournamentDTO removeTeamFromTournament(UUID tournamentId, UUID teamId) throws IOException {
        try (Response response = deleteRequest(pointTournamentsURL + tournamentId + "/teams/" + teamId)) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Remove team from tournament failed");
            }
            String json = response.body().string();
            return getObjectMapper().readValue(json, PointTournamentDTO.class);
        }
    }

    public record CreatePointTournamentDTO(@NotNull String name){}
}
