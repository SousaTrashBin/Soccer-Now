package pt.ul.fc.css.soccernow.domain.entities.user;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.game.PlayerGameStats;
import pt.ul.fc.css.soccernow.util.CardEnum;
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

    @OneToMany(mappedBy = "player", orphanRemoval = true)
    private List<PlayerGameStats> playerGameStats = new ArrayList<>();

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

    public boolean hasTeam(Team team) {
        return teams.contains(team);
    }

    public boolean hasPendingGames() {
        return getTeams()
                .stream()
                .anyMatch(team -> team.hasPendingGamesWithPlayer(this));
    }

    public void registerGameStats(PlayerGameStats gameStats) {
        playerGameStats.add(gameStats);
        gameStats.setPlayer(this);
    }

    public long getRedCardCount() {
        return playerGameStats.stream().filter(playerStats -> playerStats.getGivenCard() == CardEnum.RED).count();
    }

    public long getYellowCardCount() {
        return playerGameStats.stream().filter(playerStats -> playerStats.getGivenCard() == CardEnum.YELLOW).count();
    }

    public long getCardCount() {
        return 2 * getRedCardCount() + getYellowCardCount();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Player player = (Player) o;
        return getId() != null && Objects.equals(getId(), player.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "preferredPosition = " + getPreferredPosition() + ", " +
                "name = " + getName() + ")";
    }

    public Float getAverageGoals() {
        List<PlayerGameStats> stats = getPlayerGameStats();
        if (stats.isEmpty()) {
            return 0f;
        }
        int totalGoals = stats.stream()
                              .map(PlayerGameStats::getScoredGoals)
                              .reduce(0, Integer::sum);
        return (float) totalGoals / stats.size();
    }
}
