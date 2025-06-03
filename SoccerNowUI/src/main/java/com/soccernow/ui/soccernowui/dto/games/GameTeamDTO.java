package com.soccernow.ui.soccernowui.dto.games;

import com.soccernow.ui.soccernowui.dto.TeamInfoDTO;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class GameTeamDTO implements Serializable {
    @Size(min = 5, max = 5)
    private Set<GamePlayerDTO> gamePlayers = new HashSet<>();
    private TeamInfoDTO team;

    public GameTeamDTO() {
    }

    public GameTeamDTO(Set<GamePlayerDTO> gamePlayers, TeamInfoDTO team) {
        this.gamePlayers = gamePlayers;
        this.team = team;
    }

    public Set<GamePlayerDTO> getGamePlayers() {
        return gamePlayers;
    }

    public GameTeamDTO setGamePlayers(Set<GamePlayerDTO> gamePlayers) {
        this.gamePlayers = gamePlayers;
        return this;
    }

    public TeamInfoDTO getTeam() {
        return team;
    }

    public GameTeamDTO setTeam(TeamInfoDTO team) {
        this.team = team;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameTeamDTO entity = (GameTeamDTO) o;
        return Objects.equals(this.gamePlayers, entity.gamePlayers) &&
                Objects.equals(this.team, entity.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gamePlayers, team);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "gamePlayers = " + gamePlayers + ", " +
                "team = " + team + ")";
    }
}
