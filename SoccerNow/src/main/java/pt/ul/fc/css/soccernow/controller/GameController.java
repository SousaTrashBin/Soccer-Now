package pt.ul.fc.css.soccernow.controller;

import io.swagger.annotations.ApiOperation;
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

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Tag(name = "Game", description = "Game operations")
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

    @PostMapping
    @ApiOperation(value = "Register a game", notes = "Returns the game registered")
    public ResponseEntity<GameDTO> registerGame(@RequestBody @Validated @NotNull GameDTO game) {
        Game entity = gameMapper.toEntity(game);
        gameService.add(entity);
        URI location = URI.create("/api/games/" + entity.getId());
        return ResponseEntity.created(location).body(gameMapper.toDTO(entity));
    }

    @PostMapping("{gameId}/close")
    @ApiOperation(value = "Close game with given ID", notes = "Returns the closed game")
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

}
