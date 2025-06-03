package pt.ul.fc.css.soccernow.domain.dto.games;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link pt.ul.fc.css.soccernow.domain.entities.game.GameStats}
 */
public class GameStatsDTO implements Serializable {
    private UUID id;
    private Integer teamOneGoals = 0;
    private Integer teamTwoGoals = 0;
    @Size(min = 10, max = 10)
    private Set<PlayerGameStatsDTO> playerGameStats = new LinkedHashSet<>();

    public GameStatsDTO() {
    }

    public GameStatsDTO(UUID id, Integer teamOneGoals, Integer teamTwoGoals, Set<PlayerGameStatsDTO> playerGameStats) {
        this.id = id;
        this.teamOneGoals = teamOneGoals;
        this.teamTwoGoals = teamTwoGoals;
        this.playerGameStats = playerGameStats;
    }

    public UUID getId() {
        return id;
    }

    public GameStatsDTO setId(UUID id) {
        this.id = id;
        return this;
    }

    public Integer getTeamOneGoals() {
        return teamOneGoals;
    }

    public GameStatsDTO setTeamOneGoals(Integer teamOneGoals) {
        this.teamOneGoals = teamOneGoals;
        return this;
    }

    public Integer getTeamTwoGoals() {
        return teamTwoGoals;
    }

    public GameStatsDTO setTeamTwoGoals(Integer teamTwoGoals) {
        this.teamTwoGoals = teamTwoGoals;
        return this;
    }

    public Set<PlayerGameStatsDTO> getPlayerGameStats() {
        return playerGameStats;
    }

    public GameStatsDTO setPlayerGameStats(Set<PlayerGameStatsDTO> playerGameStats) {
        this.playerGameStats = playerGameStats;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameStatsDTO entity = (GameStatsDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.teamOneGoals, entity.teamOneGoals) &&
                Objects.equals(this.teamTwoGoals, entity.teamTwoGoals) &&
                Objects.equals(this.playerGameStats, entity.playerGameStats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teamOneGoals, teamTwoGoals, playerGameStats);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "teamOneGoals = " + teamOneGoals + ", " +
                "teamTwoGoals = " + teamTwoGoals + ", " +
                "playerGameStats = " + playerGameStats + ")";
    }

    @JsonIgnore
    public Integer getNumberOfGoalsScored() {
        return teamOneGoals + teamTwoGoals;
    }
}
