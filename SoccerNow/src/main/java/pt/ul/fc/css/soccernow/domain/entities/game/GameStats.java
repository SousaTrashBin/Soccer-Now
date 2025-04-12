package pt.ul.fc.css.soccernow.domain.entities.game;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.util.LinkedHashSet;
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
  private Integer teamOneGoals;

  @Column(name = "team_two_goals", nullable = false)
  private Integer teamTwoGoals;

  @Size(min = 10, max = 10)
  @OneToMany(mappedBy = "gameStats", orphanRemoval = true)
  private Set<PlayerGameStats> playerGameStats = new LinkedHashSet<>();

  public Set<PlayerGameStats> getPlayerGameStats() {
    return playerGameStats;
  }

  public void setPlayerGameStats(Set<PlayerGameStats> playerGameStats) {
    this.playerGameStats = playerGameStats;
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
}
