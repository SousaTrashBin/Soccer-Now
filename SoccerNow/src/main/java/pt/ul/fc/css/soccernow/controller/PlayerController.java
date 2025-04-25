package pt.ul.fc.css.soccernow.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.soccernow.domain.dto.TeamDTO;
import pt.ul.fc.css.soccernow.domain.dto.games.PlayerGameStatsDTO;
import pt.ul.fc.css.soccernow.domain.dto.user.PlayerDTO;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.mapper.PlayerGameStatsMapper;
import pt.ul.fc.css.soccernow.mapper.PlayerMapper;
import pt.ul.fc.css.soccernow.mapper.TeamMapper;
import pt.ul.fc.css.soccernow.service.PlayerService;

import java.util.List;
import java.util.UUID;

@Tag(name = "Player", description = "Player operations")
@RestController
@RequestMapping("/api/players/")
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerMapper playerMapper;
    private final PlayerGameStatsMapper playerGameStatsMapper;
    private final TeamMapper teamMapper;

    public PlayerController(PlayerService playerService, PlayerMapper playerMapper, PlayerGameStatsMapper playerGameStatsMapper, TeamMapper teamMapper) {
        this.playerService = playerService;
        this.playerMapper = playerMapper;
        this.playerGameStatsMapper = playerGameStatsMapper;
        this.teamMapper = teamMapper;
    }

    @PostMapping
    @ApiOperation(value = "Register a player", notes = "Returns the player registered")
    public ResponseEntity<PlayerDTO> registerPlayer(@RequestBody @Validated @NotNull PlayerDTO playerDTO) {
        if (playerDTO.getName() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Player player = playerMapper.toEntity(playerDTO);
        Player savedPlayer = playerService.add(player);
        return ResponseEntity.status(HttpStatus.CREATED).body(playerMapper.toDTO(savedPlayer));
    }

    @GetMapping("/{playerId}")
    @ApiOperation(value = "Get player by ID", notes = "Returns a player by its ID")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable("playerId") UUID playerId) {
        Player player = playerService.findNotDeletedById(playerId);
        return ResponseEntity.ok(playerMapper.toDTO(player));
    }

    @GetMapping
    @ApiOperation(value = "Get all players", notes = "Returns a list of all players")
    public ResponseEntity<List<PlayerDTO>> getAllPlayers() {
        List<PlayerDTO> players = playerService.findAllNotDeleted()
                                               .stream()
                                               .map(playerMapper::toDTO)
                                               .toList();
        return ResponseEntity.ok(players);
    }

    @DeleteMapping("/{playerId}")
    @ApiOperation(value = "Delete a player with given ID", notes = "Returns the deleted player")
    public ResponseEntity<PlayerDTO> deletePlayerById(@PathVariable("playerId") UUID playerId) {
        Player player = playerService.findNotDeletedById(playerId);
        playerService.softDelete(playerId);
        return ResponseEntity.ok(playerMapper.toDTO(player));
    }

    @PutMapping("/{playerId}")
    @ApiOperation(value = "Update a player with given ID", notes = "Returns the updated player")
    public ResponseEntity<PlayerDTO> updatePlayerById(
            @PathVariable("playerId") UUID playerId,
            @RequestBody @Validated @NotNull PlayerDTO playerDTO
    ) {
        Player player = playerMapper.toEntity(playerDTO);
        Player savedPlayer = playerService.update(player);
        return ResponseEntity.ok(playerMapper.toDTO(savedPlayer));
    }

    @GetMapping("/{playerId}/stats")
    @ApiOperation(value = "Get player's stats", notes = "Returns a player's stats")
    public ResponseEntity<List<PlayerGameStatsDTO>> getPlayerStats(@PathVariable("playerId") UUID playerId) {
        Player player = playerService.findNotDeletedById(playerId);
        List<PlayerGameStatsDTO> stats = player.getPlayerGameStats()
                                               .stream()
                                               .map(playerGameStatsMapper::toDTO)
                                               .toList();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/{playerId}/teams")
    @ApiOperation(value = "Get player's teams", notes = "Returns a player's teams")
    public ResponseEntity<List<TeamDTO>> getPlayerTeams(@PathVariable("playerId") UUID playerId) {
        Player player = playerService.findNotDeletedById(playerId);
        List<TeamDTO> teams = player.getTeams()
                                    .stream()
                                    .map(teamMapper::toDTO)
                                    .toList();
        return ResponseEntity.ok(teams);
    }
}
