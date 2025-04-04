package pt.ul.fc.css.soccernow.domain.entities.game;

import java.time.LocalDateTime;

public class TournamentGame extends Game {
  public TournamentGame(String location, LocalDateTime dateTime) {
    super(location, dateTime);
  }
}
