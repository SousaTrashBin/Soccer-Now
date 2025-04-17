package pt.ul.fc.css.soccernow.domain.entities.user;

import jakarta.persistence.*;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.game.PlayerGameStats;
import pt.ul.fc.css.soccernow.util.FutsalPositionEnum;

import java.util.*;

@Entity
public class Player extends User {
    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_position")
    private FutsalPositionEnum preferredPosition;

    @ManyToMany(mappedBy = "players")
    @OrderBy("name")
    private Set<Team> teams = new LinkedHashSet<>();

    @OneToMany(orphanRemoval = true)
    private List<PlayerGameStats> playerGameStats = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        return Objects.equals(getId(), player.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public List<PlayerGameStats> getPlayerGameStats() {
        return playerGameStats;
    }

    public void setPlayerGameStats(List<PlayerGameStats> playerGameStats) {
        this.playerGameStats = playerGameStats;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = new LinkedHashSet<>(teams);
    }

    public FutsalPositionEnum getPreferredPosition() {
        return preferredPosition;
    }

    public void setPreferredPosition(FutsalPositionEnum preferredPosition) {
        this.preferredPosition = preferredPosition;
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

    public void removeTeam(Team team) {
        teams.remove(team);
    }
}
