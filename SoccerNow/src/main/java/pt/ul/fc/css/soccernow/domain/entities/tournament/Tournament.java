package pt.ul.fc.css.soccernow.domain.entities.tournament;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import pt.ul.fc.css.soccernow.domain.entities.game.Game;

@Entity
@Table(name = "tournament")
public abstract class Tournament {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "is_finished", nullable = false)
  private Boolean isFinished = false;

  @OneToMany(mappedBy = "tournament", orphanRemoval = true)
  private List<Game> games = new ArrayList<>();

  public List<Game> getGames() {
    return games;
  }

  public void setGames(List<Game> games) {
    this.games = games;
  }

  public Boolean getIsFinished() {
    return isFinished;
  }

  public void setIsFinished(Boolean isFinished) {
    this.isFinished = isFinished;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }
}
