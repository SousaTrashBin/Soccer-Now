package pt.ul.fc.css.soccernow.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.soccernow.domain.dto.games.reference.GameDto;
import pt.ul.fc.css.soccernow.service.GameService;

import java.util.List;

@Tag(name = "Game", description = "Game operations")
@RestController
@RequestMapping("/api/games/")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    @ApiOperation(value = "Register a game", notes = "Returns the game registered")
    public ResponseEntity<GameDto> registerGame(@RequestBody GameDto game) {
        // TODO
        return null;
    }

    @PostMapping("/{gameId}/result")
    @ApiOperation(value = "Register the result of a game with given ID", notes = "Returns the updated game")
    public ResponseEntity<GameDto> setGameResultById(@PathVariable("gameId") long gameId, @RequestBody GameDto game) {
        // TODO
        return null;
    }

//    @PutMapping("/{gameId}")
//    @ApiOperation(value = "Register the result of a game with given ID", notes = "Returns the updated game")
//    public ResponseEntity<GameDto> setGameResultById(@PathVariable("gameId") long gameId) {
//        // TODO
//        return null;
//    }
}
