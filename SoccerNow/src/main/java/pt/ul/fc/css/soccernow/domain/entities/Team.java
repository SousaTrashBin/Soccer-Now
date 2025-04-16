package pt.ul.fc.css.soccernow.domain.entities;

import jakarta.persistence.*;
import java.util.*;
import pt.ul.fc.css.soccernow.domain.entities.game.GameTeam;
import pt.ul.fc.css.soccernow.domain.entities.tournament.Placement;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;

@Entity
@Table(name = "teams")
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
  @OrderBy("name")
  private Set<Player> players = new LinkedHashSet<>();

  @OneToMany(orphanRemoval = true)
  @JoinColumn(name = "team_id")
  private Set<Placement> placements = new LinkedHashSet<>();

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

  public Set<Placement> getPlacements() {
    return placements;
  }

  public void setPlacements(Set<Placement> placements) {
    this.placements = new LinkedHashSet<>(placements);
  }

  public Set<Player> getPlayers() {
    return players;
  }

  public void setPlayers(List<Player> players) {
    this.players = new LinkedHashSet<>(players);
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }
}
