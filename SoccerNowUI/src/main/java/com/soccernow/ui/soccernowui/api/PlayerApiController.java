package com.soccernow.ui.soccernowui.api;

import com.soccernow.ui.soccernowui.dto.user.PlayerDTO;

public enum PlayerApiController {
    INSTANCE;
    private String url = "/players/";

    public PlayerDTO registerPlayer(PlayerDTO player) {
        return new PlayerDTO();
    }
}
