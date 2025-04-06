package pt.ul.fc.css.soccernow.domain.entities.games;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import pt.ul.fc.css.soccernow.domain.entities.GameTeam;
import pt.ul.fc.css.soccernow.domain.entities.Stats;
import pt.ul.fc.css.soccernow.domain.entities.user.Referee;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "GAME_TYPE", discriminatorType = DiscriminatorType.STRING)
@Table(name = "GAMES")
public abstract class Game {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "ID", nullable = false)
  private Long id;

  @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
  @JoinColumn(name = "GAME_TEAM_ONE_ID", nullable = false)
  private GameTeam gameTeamOne;

  @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
  @JoinColumn(name = "GAME_TEAM_TWO_ID", nullable = false)
  private GameTeam gameTeamTwo;

  @OneToOne(cascade = CascadeType.PERSIST, optional = false, orphanRemoval = true)
  @JoinColumn(name = "STATS_ID", nullable = false, unique = true)
  private Stats stats;

  @Column(name = "ADDRESS", nullable = false)
  private String address;

  @Column(name = "LOCAL_DATE_TIME", nullable = false)
  private LocalDateTime localDateTime;

  @Column(name = "IS_OVER", nullable = false)
  private Boolean isOver = false;

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(
      name = "GAMES_SECONDARY_REFEREES",
      joinColumns = @JoinColumn(name = "GAME_ID"),
      inverseJoinColumns = @JoinColumn(name = "SECONDARY_REFEREE_ID"))
  private Set<Referee> secondaryReferees = new LinkedHashSet<>();

  @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
  @JoinColumn(name = "PRIMARY_REFEREE_ID", nullable = false)
  private Referee primaryReferee;

  public Referee getPrimaryReferee() {
    return primaryReferee;
  }

  public void setPrimaryReferee(Referee primaryReferee) {
    this.primaryReferee = primaryReferee;
  }

  public Set<Referee> getSecondaryReferees() {
    return secondaryReferees;
  }

  public void setSecondaryReferees(Set<Referee> secondaryReferees) {
    this.secondaryReferees = secondaryReferees;
  }

  public void endGame() {
    isOver = true;
  }

  public Boolean getIsOver() {
    return isOver;
  }

  public void setIsOver(Boolean isOver) {
    this.isOver = isOver;
  }

  public LocalDateTime getLocalDateTime() {
    return localDateTime;
  }

  public void setLocalDateTime(LocalDateTime localDateTime) {
    this.localDateTime = localDateTime;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Stats getStats() {
    return stats;
  }

  public void setStats(Stats stats) {
    this.stats = stats;
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
