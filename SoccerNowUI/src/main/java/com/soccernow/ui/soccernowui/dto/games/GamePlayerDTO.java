package com.soccernow.ui.soccernowui.dto.games;

import pt.ul.fc.css.soccernow.domain.dto.user.PlayerInfoDTO;
import pt.ul.fc.css.soccernow.util.FutsalPositionEnum;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link pt.ul.fc.css.soccernow.domain.entities.tournament.GamePlayer}
 */
public class GamePlayerDTO implements Serializable {
    private FutsalPositionEnum playedInPosition;
    private PlayerInfoDTO player;

    public GamePlayerDTO() {
    }

    public GamePlayerDTO(FutsalPositionEnum playedInPosition, PlayerInfoDTO player) {
        this.playedInPosition = playedInPosition;
        this.player = player;
    }

    public FutsalPositionEnum getPlayedInPosition() {
        return playedInPosition;
    }

    public GamePlayerDTO setPlayedInPosition(FutsalPositionEnum playedInPosition) {
        this.playedInPosition = playedInPosition;
        return this;
    }

    public PlayerInfoDTO getPlayer() {
        return player;
    }

    public GamePlayerDTO setPlayer(PlayerInfoDTO player) {
        this.player = player;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GamePlayerDTO entity = (GamePlayerDTO) o;
        return Objects.equals(this.playedInPosition, entity.playedInPosition) &&
                Objects.equals(this.player, entity.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playedInPosition, player);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "playedInPosition = " + playedInPosition + ", " +
                "player = " + player + ")";
    }
}
