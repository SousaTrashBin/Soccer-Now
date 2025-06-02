package com.soccernow.ui.soccernowui.dto.games;

import pt.ul.fc.css.soccernow.domain.dto.user.PlayerInfoDTO;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for {@link pt.ul.fc.css.soccernow.domain.entities.game.PlayerGameStats}
 */
public class PlayerGameStatsDTO implements Serializable {
    private Set<CardInfoDTO> receivedCards = new LinkedHashSet<>();
    private Integer scoredGoals = 0;
    private PlayerInfoDTO player;
    private GameInfoDTO game;

    public PlayerGameStatsDTO() {
    }

    public PlayerGameStatsDTO(
            Set<CardInfoDTO> receivedCards, Integer scoredGoals, PlayerInfoDTO player, GameInfoDTO game) {
        this.scoredGoals = scoredGoals;
        this.player = player;
        this.game = game;
        this.receivedCards = receivedCards;
    }

    public PlayerGameStatsDTO(Integer scoredGoals, PlayerInfoDTO playerInfoDTO, Set<CardInfoDTO> receivedCards) {
        this.scoredGoals = scoredGoals;
        this.player = playerInfoDTO;
        this.receivedCards = receivedCards;
    }

    public Integer getScoredGoals() {
        return scoredGoals;
    }

    public PlayerGameStatsDTO setScoredGoals(Integer scoredGoals) {
        this.scoredGoals = scoredGoals;
        return this;
    }

    public PlayerInfoDTO getPlayer() {
        return player;
    }

    public PlayerGameStatsDTO setPlayer(PlayerInfoDTO player) {
        this.player = player;
        return this;
    }

    public GameInfoDTO getGame() {
        return game;
    }

    public PlayerGameStatsDTO setGame(GameInfoDTO game) {
        this.game = game;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerGameStatsDTO entity = (PlayerGameStatsDTO) o;
        return Objects.equals(this.scoredGoals, entity.scoredGoals) &&
                Objects.equals(this.player, entity.player) &&
                Objects.equals(this.game, entity.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scoredGoals, player, game);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "scoredGoals = " + scoredGoals + ", " +
                "player = " + player + ", " +
                "game = " + game + ")";
    }

    public Set<CardInfoDTO> getReceivedCards() {
        return receivedCards;
    }

    public PlayerGameStatsDTO setReceivedCards(Set<CardInfoDTO> receivedCards) {
        this.receivedCards = receivedCards;
        return this;
    }
}
