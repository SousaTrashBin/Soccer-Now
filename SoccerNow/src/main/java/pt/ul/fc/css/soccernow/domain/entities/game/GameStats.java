package pt.ul.fc.css.soccernow.domain.entities.game;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.proxy.HibernateProxy;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "game_stats")
public class GameStats {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "team_one_goals", nullable = false)
    private Integer teamOneGoals = 0;

    @Column(name = "team_two_goals", nullable = false)
    private Integer teamTwoGoals = 0;

    @Size(min = 10, max = 10)
    @OneToMany(mappedBy = "gameStats", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<PlayerGameStats> playerGameStats = new LinkedHashSet<>();

    public Set<PlayerGameStats> getPlayerGameStats() {
        return playerGameStats;
    }

    public void setPlayerGameStats(Set<PlayerGameStats> playerGameStats) {
        this.playerGameStats = playerGameStats;
    }

    public void addPlayerGameStats(PlayerGameStats playerGameStats) {
        this.playerGameStats.add(playerGameStats);
        playerGameStats.setGameStats(this);
    }

    public Integer getTeamTwoGoals() {
        return teamTwoGoals;
    }

    public void setTeamTwoGoals(Integer teamTwoGoals) {
        this.teamTwoGoals = teamTwoGoals;
    }

    public Integer getTeamOneGoals() {
        return teamOneGoals;
    }

    public void setTeamOneGoals(Integer teamOneGoals) {
        this.teamOneGoals = teamOneGoals;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "teamOneGoals = " + teamOneGoals + ", " +
                "teamTwoGoals = " + teamTwoGoals + ")";
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        GameStats gameStats = (GameStats) o;
        return getId() != null && Objects.equals(getId(), gameStats.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
