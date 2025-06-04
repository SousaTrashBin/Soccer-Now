package com.soccernow.ui.soccernowui.api;

import com.soccernow.ui.soccernowui.dto.user.RefereeDTO;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.soccernow.ui.soccernowui.util.ApiUtils.*;


public enum RefereeApiController {
    INSTANCE;
    private final String refereesURL = baseURL + "referees/";

    public RefereeDTO registerReferee(RefereeDTO refereeDTO) throws IOException {
        try (Response response = postJsonRequest(refereesURL, refereeDTO)) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Register referee failed");
            }
            String json = response.body().string();
            return getObjectMapper().readValue(json, RefereeDTO.class);
        }
    }

    public RefereeDTO getRefereeById(UUID refereeId) throws IOException {
        try (Response response = getRequest(refereesURL + refereeId)) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Get referee by ID failed");
            }
            String json = response.body().string();
            return getObjectMapper().readValue(json, RefereeDTO.class);
        }
    }

    public List<RefereeDTO> getAllReferees() throws IOException {
        try (Response response = getRequest(refereesURL)) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Get all referees failed");
            }
            String json = response.body().string();
            return getObjectMapper().readValue(json, getObjectMapper().getTypeFactory().constructCollectionType(List.class, RefereeDTO.class));
        }
    }

    public RefereeDTO updateRefereeById(UUID refereeId, RefereeDTO refereeDTO) throws IOException {
        try (Response response = putJsonRequest(refereesURL + refereeId, refereeDTO)) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Update referee failed");
            }
            String json = response.body().string();
            return getObjectMapper().readValue(json, RefereeDTO.class);
        }
    }

    public void deleteRefereeById(UUID refereeId) throws IOException {
        try (Response response = deleteRequest(refereesURL + refereeId)) {
            if (!response.isSuccessful()) {
                throwApiException(response, "Delete referee failed");
            }
        }
    }
}
