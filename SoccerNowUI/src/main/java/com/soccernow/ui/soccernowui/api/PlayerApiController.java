package com.soccernow.ui.soccernowui.api;

import com.soccernow.ui.soccernowui.dto.user.PlayerDTO;

import static com.soccernow.ui.soccernowui.util.ApiUtils.baseURL;

public enum PlayerApiController {
    INSTANCE;
    private String playersURL = baseURL + "players/";

    public PlayerDTO registerPlayer(PlayerDTO player) {
        return new PlayerDTO();
    }
}
