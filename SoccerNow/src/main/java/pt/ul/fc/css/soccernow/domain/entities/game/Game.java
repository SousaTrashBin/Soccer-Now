package pt.ul.fc.css.soccernow.domain.entities.game;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import pt.ul.fc.css.soccernow.domain.entities.Card;
import pt.ul.fc.css.soccernow.domain.entities.GameTeam;
import pt.ul.fc.css.soccernow.domain.entities.Stats;
import pt.ul.fc.css.soccernow.domain.entities.user.Referee;

public abstract class Game {
  private int teamOneGoals = 0;
  private int teamTwoGoals = 0;
  private Stats stats;
  private boolean isOver = false;
  private String location;
  private LocalDateTime dateTime;
  private List<Card> cards = new ArrayList<>();
  public GameTeam teamOne;
  public GameTeam teamTwo;
  public Referee primaryReferee;
  public List<Referee> secondaryReferees;

  public Game(String location, LocalDateTime dateTime) {
    this.location = location;
    this.dateTime = dateTime;
  }

  public Game() {}

  public int getTeamOneGoals() {
    return teamOneGoals;
  }

  public Game setTeamOneGoals(int teamOneGoals) {
    this.teamOneGoals = teamOneGoals;
    return this;
  }

  public int getTeamTwoGoals() {
    return teamTwoGoals;
  }

  public Game setTeamTwoGoals(int teamTwoGoals) {
    this.teamTwoGoals = teamTwoGoals;
    return this;
  }

  public Stats getStats() {
    return stats;
  }

  public Game setStats(Stats stats) {
    this.stats = stats;
    return this;
  }

  public boolean isOver() {
    return isOver;
  }

  public Game setOver(boolean over) {
    isOver = over;
    return this;
  }

  public String getLocation() {
    return location;
  }

  public Game setLocation(String location) {
    this.location = location;
    return this;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public Game setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
    return this;
  }

  public List<Card> getCards() {
    return cards;
  }

  public Game setCards(List<Card> cards) {
    this.cards = cards;
    return this;
  }
}
