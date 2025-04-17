package pt.ul.fc.css.soccernow.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ul.fc.css.soccernow.service.PlayerService;

@Tag(name = "Player", description = "Player operations")
@RestController
@RequestMapping("/api/players/")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }
}
