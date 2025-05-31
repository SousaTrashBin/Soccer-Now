package pt.ul.fc.css.soccernow.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.soccernow.domain.dto.games.GameDTO;
import pt.ul.fc.css.soccernow.domain.dto.games.PlayerGameStatsDTO;
import pt.ul.fc.css.soccernow.domain.entities.game.Game;
import pt.ul.fc.css.soccernow.domain.entities.game.PlayerGameStats;
import pt.ul.fc.css.soccernow.mapper.GameMapper;
import pt.ul.fc.css.soccernow.mapper.PlayerGameStatsMapper;
import pt.ul.fc.css.soccernow.service.GameService;
import pt.ul.fc.css.soccernow.util.GameSearchParams;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Tag(name = "Game", description = "Game related operations")
@RestController
@RequestMapping("/api/games/")
public class GameController {
    private final GameService gameService;
    private final PlayerGameStatsMapper playerGameStatsMapper;
    private final GameMapper gameMapper;

    public GameController(GameService gameService,
                          PlayerGameStatsMapper playerGameStatsMapper,
                          GameMapper gameMapper) {
        this.gameService = gameService;
        this.playerGameStatsMapper = playerGameStatsMapper;
        this.gameMapper = gameMapper;
    }

    @GetMapping("{gameId}")
    @Operation(
            summary = "Get game by ID",
            description = "Returns the details of a game identified by the given UUID."
    )
    public ResponseEntity<GameDTO> getGameById(@PathVariable("gameId") @NotNull UUID gameId) {
        Game game = gameService.findNotDeletedById(gameId);
        return ResponseEntity.ok(gameMapper.toDTO(game));
    }

    @PostMapping
    @Operation(
            summary = "Register a game",
            description = "Registers a new game and returns the details of the registered game."
    )
    public ResponseEntity<GameDTO> registerGame(@RequestBody @Validated @NotNull GameDTO game) {
        Game entity = gameMapper.toEntity(game);
        Game savedGame = gameService.add(entity);
        URI location = URI.create("/api/games/" + savedGame.getId());
        return ResponseEntity.created(location).body(gameMapper.toDTO(savedGame));
    }

    @PostMapping("{gameId}/close")
    @Operation(
            summary = "Close game by ID",
            description = "Closes the game identified by the given game ID and returns the closed game details. Optionally accepts player game stats."
    )
    public ResponseEntity<GameDTO> closeGameById(
            @PathVariable("gameId") @NotNull UUID gameId,
            @RequestBody(required = false) @Validated Set<PlayerGameStatsDTO> playerGameStatsDTOs
    ) {
        playerGameStatsDTOs = playerGameStatsDTOs != null ? playerGameStatsDTOs : new HashSet<>();
        Set<PlayerGameStats> playerGameStats = playerGameStatsDTOs.stream()
                .map(playerGameStatsMapper::toEntity)
                .collect(Collectors.toSet());
        Game closedGame = gameService.closeGame(gameId, playerGameStats);
        return ResponseEntity.ok(gameMapper.toDTO(closedGame));
    }

    @GetMapping
    @Operation(
            summary = "Get all non deleted games",
            description = "Returns the details of all non deleted games."
    )
    public ResponseEntity<List<GameDTO>> getAllGames(@ModelAttribute GameSearchParams params) {
        List<Game> allNotDeleted = gameService.findAllNotDeleted(params);
        return ResponseEntity.ok(allNotDeleted.stream().map(gameMapper::toDTO).toList());
    }

}
