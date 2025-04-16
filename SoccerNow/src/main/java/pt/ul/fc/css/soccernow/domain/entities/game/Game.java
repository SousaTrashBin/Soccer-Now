package pt.ul.fc.css.soccernow.domain.entities.game;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import pt.ul.fc.css.soccernow.domain.entities.Address;
import pt.ul.fc.css.soccernow.domain.entities.tournament.Tournament;
import pt.ul.fc.css.soccernow.domain.entities.user.Referee;

@Entity
@Table(name = "games")
public class Game {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  private UUID id;

  @OneToOne(mappedBy = "game", orphanRemoval = true, optional = false)
  private GameTeam gameTeamOne;

  @OneToOne(mappedBy = "game", orphanRemoval = true, optional = false)
  private GameTeam gameTeamTwo;

  @OneToOne(orphanRemoval = true)
  @JoinColumn(name = "game_stats_id")
  private GameStats gameStats;

  @ManyToOne(optional = false)
  @JoinColumn(name = "primary_referee_id", nullable = false)
  private Referee primaryReferee;

  @ManyToMany
  @JoinTable(
      name = "game_secondaryReferees",
      joinColumns = @JoinColumn(name = "game_id"),
      inverseJoinColumns = @JoinColumn(name = "secondaryReferees_id"))
  private Set<Referee> secondaryReferees = new HashSet<>();

  @ManyToOne(optional = false)
  @JoinColumn(name = "located_in_id", nullable = false)
  private Address locatedIn;

  @Column(name = "happens_in", nullable = false)
  private LocalDateTime happensIn;

  @Column(name = "is_finished", nullable = false)
  private Boolean isFinished = false;

  @ManyToOne
  @JoinColumn(name = "tournament_id")
  private Tournament tournament;

  public Tournament getTournament() {
    return tournament;
  }

  public void setTournament(Tournament tournament) {
    this.tournament = tournament;
  }

  public Boolean getIsFinished() {
    return isFinished;
  }

  public void setIsFinished(Boolean isFinished) {
    this.isFinished = isFinished;
  }

  public LocalDateTime getHappensIn() {
    return happensIn;
  }

  public void setHappensIn(LocalDateTime happensIn) {
    this.happensIn = happensIn;
  }

  public Address getLocatedIn() {
    return locatedIn;
  }

  public void setLocatedIn(Address locatedIn) {
    this.locatedIn = locatedIn;
  }

  public Set<Referee> getSecondaryReferees() {
    return secondaryReferees;
  }

  public void setSecondaryReferees(Set<Referee> secondaryReferees) {
    this.secondaryReferees = secondaryReferees;
  }

  public Referee getPrimaryReferee() {
    return primaryReferee;
  }

  public void setPrimaryReferee(Referee primaryReferee) {
    this.primaryReferee = primaryReferee;
  }

  public GameStats getGameStats() {
    return gameStats;
  }

  public void setGameStats(GameStats gameStats) {
    this.gameStats = gameStats;
  }

  public GameTeam getGameTeamTwo() {
    return gameTeamTwo;
  }

  public void setGameTeamTwo(GameTeam gameTeamTwo) {
    this.gameTeamTwo = gameTeamTwo;
  }

  public GameTeam getGameTeamOne() {
    return gameTeamOne;
  }

  public void setGameTeamOne(GameTeam gameTeamOne) {
    this.gameTeamOne = gameTeamOne;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }
}
