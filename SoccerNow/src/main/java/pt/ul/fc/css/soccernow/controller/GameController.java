package pt.ul.fc.css.soccernow.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.soccernow.domain.dto.games.GameDTO;
import pt.ul.fc.css.soccernow.domain.dto.games.PlayerGameStatsDTO;
import pt.ul.fc.css.soccernow.mapper.PlayerGameStatsMapper;
import pt.ul.fc.css.soccernow.service.GameService;

import java.util.List;
import java.util.UUID;

@Tag(name = "Game", description = "Game operations")
@RestController
@RequestMapping("/api/games/")
public class GameController {

    private final GameService gameService;
    private final PlayerGameStatsMapper playerGameStatsMapper;

    public GameController(GameService gameService,
                          PlayerGameStatsMapper playerGameStatsMapper) {
        this.gameService = gameService;
        this.playerGameStatsMapper = playerGameStatsMapper;
    }

    @PostMapping
    @ApiOperation(value = "Register a game", notes = "Returns the game registered")
    public ResponseEntity<GameDTO> registerGame(@RequestBody @Validated @NotNull GameDTO game) {
        // TODO
        return null;
    }

    @PostMapping("/{gameId}/result")
    @ApiOperation(value = "Register the result of a game with given ID", notes = "Returns the updated game")
    public ResponseEntity<GameDTO> closeGameById(
            @PathVariable("gameId") @NotNull UUID gameId,
            @RequestBody @Validated @NotNull List<PlayerGameStatsDTO> playerGameStatsDTOS
    ) {
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
