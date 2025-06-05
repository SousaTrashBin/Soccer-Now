package com.soccernow.ui.soccernowui.dto.user;

import com.soccernow.ui.soccernowui.dto.TeamInfoDTO;
import com.soccernow.ui.soccernowui.dto.games.PlayerGameStatsDTO;
import com.soccernow.ui.soccernowui.util.FutsalPositionEnum;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.*;

public class PlayerDTO implements Serializable {
    private List<PlayerGameStatsDTO> playerGameStats = new ArrayList<>();
    private UUID id;
    @Pattern(regexp = "^\\p{L}+( \\p{L}+)*$")
    @Length(max = 100)
    private String name;
    private FutsalPositionEnum preferredPosition;
    private Set<TeamInfoDTO> teams = new LinkedHashSet<>();

    public PlayerDTO() {
    }

    public PlayerDTO(
            List<PlayerGameStatsDTO> playerGameStats, UUID id, String name, FutsalPositionEnum preferredPosition, Set<TeamInfoDTO> teams) {
        this.id = id;
        this.name = name;
        this.preferredPosition = preferredPosition;
        this.teams = teams;
        this.playerGameStats = playerGameStats;
    }

    public UUID getId() {
        return id;
    }

    public PlayerDTO setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PlayerDTO setName(String name) {
        this.name = name;
        return this;
    }

    public FutsalPositionEnum getPreferredPosition() {
        return preferredPosition;
    }

    public PlayerDTO setPreferredPosition(FutsalPositionEnum preferredPosition) {
        this.preferredPosition = preferredPosition;
        return this;
    }

    public Set<TeamInfoDTO> getTeams() {
        return teams;
    }

    public PlayerDTO setTeams(Set<TeamInfoDTO> teams) {
        this.teams = teams;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerDTO entity = (PlayerDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.preferredPosition, entity.preferredPosition) &&
                Objects.equals(this.teams, entity.teams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, preferredPosition, teams);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "preferredPosition = " + preferredPosition + ", " +
                "teams = " + teams + ")";
    }

    public List<PlayerGameStatsDTO> getPlayerGameStats() {
        return playerGameStats;
    }

    public PlayerDTO setPlayerGameStats(List<PlayerGameStatsDTO> playerGameStats) {
        this.playerGameStats = playerGameStats;
        return this;
    }
}
