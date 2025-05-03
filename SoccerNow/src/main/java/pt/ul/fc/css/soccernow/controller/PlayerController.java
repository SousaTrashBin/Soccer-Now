package pt.ul.fc.css.soccernow.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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

@Tag(name = "Player", description = "Player related operations")
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
    @Operation(
            summary = "Register a player",
            description = "Registers a new player and returns the details of the registered player."
    )
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
    @Operation(
            summary = "Get player by ID",
            description = "Returns the details of a player identified by the given UUID."
    )
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable("playerId") @NotNull UUID playerId) {
        Player player = playerService.findNotDeletedById(playerId);
        return ResponseEntity.ok(playerMapper.toDTO(player));
    }

    @GetMapping("average-goals")
    @Operation(
            summary = "Get player's average goals",
            description = "Returns a list of players with their average goals. If 'playerName' is provided, filters by name; otherwise, returns all players."
    )
    public ResponseEntity<List<AverageGoalsResponse>> getAverageGoalsById(@Parameter(description = "Nome do jogador") @RequestParam(name = "playerName", required = false) String playerName) {
        List<Player> players = playerName != null ? playerService.findNotDeletedByName(playerName) : playerService.findAllNotDeleted();
        return ResponseEntity.ok(players.stream().map(player -> new AverageGoalsResponse(player.getId(), player.getAverageGoals())).toList());
    }

    @GetMapping
    @Operation(
            summary = "Get all players",
            description = "Returns a list of all players. Supports optional result size, presentation order and filtering by name."
    )
    public ResponseEntity<List<PlayerDTO>> getAllPlayers(@Parameter(description = "Tamanho do resultado") @RequestParam(name = "size", required = false) @Min(0) Integer size,
                                                         @Parameter(description = "Ordem de apresentação de acordo com o número de cartões vermelhos: 'asc' para ordem crescente, 'dsc' para ordem decrescente", schema = @Schema(allowableValues = {"asc", "dsc"})) @RequestParam(name = "order", required = false) String order,
                                                         @Parameter(description = "Nome do jogador") @RequestParam(name = "playerName", required = false) String name) {
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

    @DeleteMapping("{playerId}")
    @Operation(
            summary = "Delete player by ID",
            description = "Performs a soft delete of the player with the specified UUID. The player will soft deleted, marked as deleted but not permanently removed."
    )
    public ResponseEntity<String> deletePlayerById(@PathVariable("playerId") @NotNull UUID playerId) {
        playerService.softDelete(playerId);
        return ResponseEntity.ok("Player deleted successfully");
    }

    @PutMapping("{playerId}")
    @Operation(
            summary = "Update player by ID",
            description = "Updates the information of a player identified by the given UUID and returns the updated player data."
    )
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
    @Operation(
            summary = "Get player's stats by ID",
            description = "Returns the game stats of a player identified by the given UUID."
    )
    public ResponseEntity<List<PlayerGameStatsDTO>> getPlayerStats(@PathVariable("playerId") @NotNull UUID playerId) {
        Player player = playerService.findNotDeletedById(playerId);
        List<PlayerGameStatsDTO> stats = player.getPlayerGameStats()
                .stream()
                .map(playerGameStatsMapper::toDTO)
                .toList();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("{playerId}/teams")
    @Operation(
            summary = "Get player's teams by ID",
            description = "Returns a list of teams associated with the player identified by the given UUID."
    )
    public ResponseEntity<List<TeamDTO>> getPlayerTeams(@PathVariable("playerId") @NotNull UUID playerId) {
        Player player = playerService.findNotDeletedById(playerId);
        List<TeamDTO> teams = player.getTeams()
                .stream()
                .map(teamMapper::toDTO)
                .toList();
        return ResponseEntity.ok(teams);
    }

    public record AverageGoalsResponse(UUID id, Float goals) {
    }
}
