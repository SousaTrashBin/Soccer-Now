package pt.ul.fc.css.soccernow.controller;

import org.springframework.web.bind.annotation.RestController;
import pt.ul.fc.css.soccernow.service.GameService;

@RestController("/api/games/")
public class GameController {

  private final GameService gameService;

  public GameController(GameService gameService) {
    this.gameService = gameService;
  }
}
