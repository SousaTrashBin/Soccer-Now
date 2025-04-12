package pt.ul.fc.css.soccernow.domain.entities;

import jakarta.persistence.*;
import java.util.*;
import pt.ul.fc.css.soccernow.domain.entities.game.GameTeam;
import pt.ul.fc.css.soccernow.domain.entities.tournament.Placement;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;

@Entity
@Table(name = "team")
public class Team {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  private UUID id;

  @ManyToMany
  @JoinTable(
      name = "team_players",
      joinColumns = @JoinColumn(name = "team_id"),
      inverseJoinColumns = @JoinColumn(name = "player_id"))
  private List<Player> players = new ArrayList<>();

  @OneToMany(orphanRemoval = true)
  @JoinColumn(name = "team_id")
  private List<Placement> placements = new ArrayList<>();

  @Column(name = "name", nullable = false)
  private String name;

  @OneToMany(mappedBy = "team", orphanRemoval = true)
  private List<GameTeam> gameTeams = new ArrayList<>();

  public List<GameTeam> getGameTeams() {
    return gameTeams;
  }

  public void setGameTeams(List<GameTeam> gameTeams) {
    this.gameTeams = gameTeams;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Placement> getPlacements() {
    return placements;
  }

  public void setPlacements(List<Placement> placements) {
    this.placements = placements;
  }

  public List<Player> getPlayers() {
    return players;
  }

  public void setPlayers(List<Player> players) {
    this.players = players;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }
}
