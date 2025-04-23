package pt.ul.fc.css.soccernow.domain.dto.user;

import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import pt.ul.fc.css.soccernow.util.FutsalPositionEnum;

import java.io.Serializable;
import java.util.*;

/**
 * DTO for {@link pt.ul.fc.css.soccernow.domain.entities.user.Player}
 */
public class PlayerDTO implements Serializable {
    private UUID id;
    @Pattern(regexp = "([a-zA-Z]+)|( [a-zA-Z]+)*")
    @Length(max = 100)
    private String name;
    private FutsalPositionEnum preferredPosition;
    private Set<TeamInfoDTO> teams = new LinkedHashSet<>();
    private List<PlayerGameStatsDTO> playerStats = new ArrayList<>();

    public PlayerDTO() {
    }

    public PlayerDTO(UUID id, String name, FutsalPositionEnum preferredPosition, Set<TeamInfoDTO> teams, List<PlayerGameStatsDTO> playerStats) {
        this.id = id;
        this.name = name;
        this.preferredPosition = preferredPosition;
        this.teams = teams;
        this.playerStats = playerStats;
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

    public List<PlayerGameStatsDTO> getPlayerStats() {
        return playerStats;
    }

    public PlayerDTO setPlayerStats(List<PlayerGameStatsDTO> playerStats) {
        this.playerStats = playerStats;
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
                Objects.equals(this.teams, entity.teams) &&
                Objects.equals(this.playerStats, entity.playerStats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, preferredPosition, teams, playerStats);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "preferredPosition = " + preferredPosition + ", " +
                "teams = " + teams + ", " +
                "playerStats = " + playerStats + ")";
    }
}
