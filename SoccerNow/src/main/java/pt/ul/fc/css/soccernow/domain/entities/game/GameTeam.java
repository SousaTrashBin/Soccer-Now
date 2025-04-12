package pt.ul.fc.css.soccernow.domain.entities.game;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.tournament.GamePlayer;

@Entity
@Table(name = "game_team")
public class GameTeam {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  private UUID id;

  @OneToMany(orphanRemoval = true)
  @JoinColumn(name = "game_team_id")
  private List<GamePlayer> gamePlayers = new ArrayList<>();

  @ManyToOne(optional = false)
  @JoinColumn(name = "team_id", nullable = false)
  private Team team;

  @OneToOne(optional = false, orphanRemoval = true)
  @JoinColumn(name = "game_id", nullable = false)
  private Game game;

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }

  public Team getTeam() {
    return team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }

  public List<GamePlayer> getGamePlayers() {
    return gamePlayers;
  }

  public void setGamePlayers(List<GamePlayer> gamePlayers) {
    this.gamePlayers = gamePlayers;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }
}
