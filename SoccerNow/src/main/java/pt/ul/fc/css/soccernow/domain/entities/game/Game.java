package pt.ul.fc.css.soccernow.domain.entities.game;

import java.time.LocalDateTime;
import java.util.List;
import pt.ul.fc.css.soccernow.domain.entities.GameTeam;
import pt.ul.fc.css.soccernow.domain.entities.Stats;
import pt.ul.fc.css.soccernow.domain.entities.user.Referee;

public abstract class Game {
  public GameTeam teamOne;
  public GameTeam teamTwo;
  public Referee primaryReferee;
  public List<Referee> secondaryReferees;

  private boolean isOver = false;
  private String location;
  private LocalDateTime dateTime;
  private Stats stats;

  public Game(String location, LocalDateTime dateTime) {
    this.location = location;
    this.dateTime = dateTime;
  }

  public Game() {}

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
}
