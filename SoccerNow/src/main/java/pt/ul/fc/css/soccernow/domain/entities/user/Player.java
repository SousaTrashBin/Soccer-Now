package pt.ul.fc.css.soccernow.domain.entities.user;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.game.PlayerGameStats;
import pt.ul.fc.css.soccernow.util.FutsalPositionEnum;

@Entity
public class Player extends User {
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Player player = (Player) o;
    return Objects.equals(getId(), player.getId())
        && Objects.equals(getName(), player.getName())
        && getPreferredPosition() == player.getPreferredPosition();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getPreferredPosition());
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "preferred_position")
  private FutsalPositionEnum preferredPosition;

  @ManyToMany(mappedBy = "players")
  private List<Team> teams = new ArrayList<>();

  @OneToMany(orphanRemoval = true)
  private List<PlayerGameStats> playerGameStats = new ArrayList<>();

  public List<PlayerGameStats> getPlayerGameStats() {
    return playerGameStats;
  }

  public void setPlayerGameStats(List<PlayerGameStats> playerGameStats) {
    this.playerGameStats = playerGameStats;
  }

  public List<Team> getTeams() {
    return teams;
  }

  public void setTeams(List<Team> teams) {
    this.teams = teams;
  }

  public FutsalPositionEnum getPreferredPosition() {
    return preferredPosition;
  }

  public void setPreferredPosition(FutsalPositionEnum preferredPosition) {
    this.preferredPosition = preferredPosition;
  }
}
