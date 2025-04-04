package pt.ul.fc.css.soccernow.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ul.fc.css.soccernow.service.GameService;

@Tag(name = "Game", description = "Game operations")
@RestController
@RequestMapping("/api/games/")
public class GameController {

  private final GameService gameService;

  public GameController(GameService gameService) {
    this.gameService = gameService;
  }
}
