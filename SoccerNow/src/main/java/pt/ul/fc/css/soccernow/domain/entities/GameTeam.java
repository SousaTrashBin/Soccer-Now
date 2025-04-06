package pt.ul.fc.css.soccernow.domain.entities;

import jakarta.persistence.*;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;

@Entity
@Table(name = "GAME_TEAM")
public class GameTeam {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "ID", nullable = false)
  private Long id;

  @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
  @JoinColumn(name = "TEAM_ID", nullable = false)
  private Team team;

  @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
  @JoinColumn(name = "GOALIE_ID", nullable = false, unique = true)
  private Player goalie;

  @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
  @JoinColumn(name = "SWEEPER_ID", nullable = false, unique = true)
  private Player sweeper;

  @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
  @JoinColumn(name = "LEFT_WINGER_ID", nullable = false, unique = true)
  private Player leftWinger;

  @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
  @JoinColumn(name = "RIGHT_WINGER_ID", nullable = false, unique = true)
  private Player rightWinger;

  @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
  @JoinColumn(name = "FORWARD_ID", nullable = false, unique = true)
  private Player forward;

  public Player getForward() {
    return forward;
  }

  public void setForward(Player forward) {
    this.forward = forward;
  }

  public Player getRightWinger() {
    return rightWinger;
  }

  public void setRightWinger(Player rightWinger) {
    this.rightWinger = rightWinger;
  }

  public Player getLeftWinger() {
    return leftWinger;
  }

  public void setLeftWinger(Player leftWinger) {
    this.leftWinger = leftWinger;
  }

  public Player getSweeper() {
    return sweeper;
  }

  public void setSweeper(Player sweeper) {
    this.sweeper = sweeper;
  }

  public Player getGoalie() {
    return goalie;
  }

  public void setGoalie(Player goalie) {
    this.goalie = goalie;
  }

  public Team getTeam() {
    return team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
