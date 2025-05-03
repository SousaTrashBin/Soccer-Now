package pt.ul.fc.css.soccernow.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.soccernow.domain.dto.TeamDTO;
import pt.ul.fc.css.soccernow.domain.dto.games.PlayerGameStatsDTO;
import pt.ul.fc.css.soccernow.domain.dto.user.PlayerDTO;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.exception.BadRequestException;
import pt.ul.fc.css.soccernow.mapper.PlayerGameStatsMapper;
import pt.ul.fc.css.soccernow.mapper.PlayerMapper;
import pt.ul.fc.css.soccernow.mapper.TeamMapper;
import pt.ul.fc.css.soccernow.service.PlayerService;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

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
            throw new BadRequestException("Team name is required");
        }
        Player player = playerMapper.toEntity(playerDTO);
        Player savedPlayer = playerService.add(player);
        URI location = URI.create("/api/players/" + savedPlayer.getId());
        return ResponseEntity.created(location).body(playerMapper.toDTO(savedPlayer));
    }

    @GetMapping("{playerId}")
    @ApiOperation(value = "Get player by ID", notes = "Returns a player by its ID")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable("playerId") @NotNull UUID playerId) {
        Player player = playerService.findNotDeletedById(playerId);
        return ResponseEntity.ok(playerMapper.toDTO(player));
    }

    @GetMapping("average-goals")
    @ApiOperation(value = "Get average player goals by player name", notes = "Returns a player's average goals by name")
    public ResponseEntity<List<AverageGoalsResponse>> getAverageGoalsById(@RequestParam(name = "playerName", required = false) String playerName) {
        List<Player> players = playerName != null ? playerService.findNotDeletedByName(playerName) : playerService.findAllNotDeleted();
        return ResponseEntity.ok(players.stream().map(player -> new AverageGoalsResponse(player.getId(), player.getAverageGoals())).toList());
    }

    @GetMapping
    @ApiOperation(value = "Get all players", notes = "Returns a list of all players")
    public ResponseEntity<List<PlayerDTO>> getAllPlayers(@RequestParam(name = "size", required = false) @Min(0) Integer size,
                                                         @RequestParam(name = "order", required = false) String order,
                                                         @RequestParam(name = "playerName", required = false) String name) {
        Comparator<Player> redCardComparator = Comparator.comparing(Player::getRedCardCount);
        Optional<Comparator<Player>> optionalPlayerComparator = Optional.ofNullable(order).map(
                orderValue -> orderValue.equals("asc")
                        ? redCardComparator
                        : redCardComparator.reversed()
        );
        Stream<Player> playerStream = name == null ? playerService.findAllNotDeleted().stream() : playerService.findNotDeletedByName(name).stream();
        if (optionalPlayerComparator.isPresent()) {
            playerStream = playerStream.sorted(optionalPlayerComparator.get());
        }

        Stream<PlayerDTO> playerDTOStream = playerStream.map(playerMapper::toDTO);
        List<PlayerDTO> players = size != null ? playerDTOStream.limit(size).toList() : playerDTOStream.toList();
        return ResponseEntity.ok(players);
    }

    public record AverageGoalsResponse(UUID id, Float goals) {
    }

    @DeleteMapping("{playerId}")
    @ApiOperation(value = "Delete a player with given ID")
    public ResponseEntity<String> deletePlayerById(@PathVariable("playerId") @NotNull UUID playerId) {
        playerService.softDelete(playerId);
        return ResponseEntity.ok("Player deleted successfully");
    }

    @PutMapping("{playerId}")
    @ApiOperation(value = "Update a player with given ID", notes = "Returns the updated player")
    public ResponseEntity<PlayerDTO> updatePlayerById(
            @PathVariable("playerId") @NotNull UUID playerId,
            @RequestBody @Validated @NotNull PlayerDTO playerDTO
    ) {
        playerDTO.setId(playerId);
        Player player = playerMapper.toEntity(playerDTO);
        Player savedPlayer = playerService.update(player);
        return ResponseEntity.ok(playerMapper.toDTO(savedPlayer));
    }

    @GetMapping("{playerId}/stats")
    @ApiOperation(value = "Get player's stats", notes = "Returns a player's stats")
    public ResponseEntity<List<PlayerGameStatsDTO>> getPlayerStats(@PathVariable("playerId") @NotNull UUID playerId) {
        Player player = playerService.findNotDeletedById(playerId);
        List<PlayerGameStatsDTO> stats = player.getPlayerGameStats()
                                               .stream()
                                               .map(playerGameStatsMapper::toDTO)
                                               .toList();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("{playerId}/teams")
    @ApiOperation(value = "Get player's teams", notes = "Returns a player's teams")
    public ResponseEntity<List<TeamDTO>> getPlayerTeams(@PathVariable("playerId") @NotNull UUID playerId) {
        Player player = playerService.findNotDeletedById(playerId);
        List<TeamDTO> teams = player.getTeams()
                                    .stream()
                                    .map(teamMapper::toDTO)
                                    .toList();
        return ResponseEntity.ok(teams);
    }
}
