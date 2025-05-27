package com.soccernow.ui.soccernowui.util;

public enum FutsalPositionEnum {
    GOALIE,
    SWEEPER,
    LEFT_WINGER,
    RIGHT_WINGER,
    FORWARD;

    @Override
    public String toString() {
        String name = name().replace('_', ' ').toLowerCase();
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}
