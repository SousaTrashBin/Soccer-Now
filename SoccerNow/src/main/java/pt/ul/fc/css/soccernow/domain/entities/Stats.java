package pt.ul.fc.css.soccernow.domain.entities;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;
import pt.ul.fc.css.soccernow.util.GameResult;

@Entity
@Table(name = "STATS")
public class Stats {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "ID", nullable = false)
  private Long id;

  @Column(name = "TEAM_ONE_GOALS", nullable = false)
  private Integer teamOneGoals;

  @Column(name = "TEAM_TWO_GOALS", nullable = false)
  private Integer teamTwoGoals;

  @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
  @JoinColumn(name = "GIVEN_CARDS_ID", nullable = false)
  private Set<Card> givenCards = new LinkedHashSet<>();

  public Set<Card> getGivenCards() {
    return givenCards;
  }

  public void setGivenCards(Set<Card> givenCards) {
    this.givenCards = givenCards;
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public GameResult getGameResult() {
    return switch (teamOneGoals - teamTwoGoals) {
      case 0 -> GameResult.DRAW;
      default -> teamOneGoals - teamTwoGoals > 0 ? GameResult.TEAM_1_WON : GameResult.TEAM_2_WON;
    };
  }
}
