package pt.ul.fc.css.soccernow.domain.entities.game;

import java.time.LocalDateTime;

public class FriendlyGame extends Game {
  public FriendlyGame(String location, LocalDateTime dateTime) {
    super(location, dateTime);
  }
}
