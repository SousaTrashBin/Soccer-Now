package pt.ul.fc.css.soccernow.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.soccernow.domain.dto.TeamDTO;
import pt.ul.fc.css.soccernow.domain.dto.user.PlayerDTO;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.exception.BadRequestException;
import pt.ul.fc.css.soccernow.mapper.PlayerMapper;
import pt.ul.fc.css.soccernow.mapper.TeamMapper;
import pt.ul.fc.css.soccernow.service.PlayerService;
import pt.ul.fc.css.soccernow.service.TeamService;
import pt.ul.fc.css.soccernow.util.TeamSearchParams;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Tag(name = "Team", description = "Team related operations")
@RestController
@RequestMapping("/api/teams/")
public class TeamController {

    private final TeamService teamService;
    private final TeamMapper teamMapper;
    private final PlayerService playerService;
    private final PlayerMapper playerMapper;

    public TeamController(TeamService teamService, TeamMapper teamMapper, PlayerService playerService, PlayerMapper playerMapper) {
        this.teamService = teamService;
        this.teamMapper = teamMapper;
        this.playerService = playerService;
        this.playerMapper = playerMapper;
    }

    @PostMapping
    @Operation(
            summary = "Register a team",
            description = "Registers a new team and returns the details of the registered team."
    )
    public ResponseEntity<TeamDTO> registerTeam(@RequestBody @Validated @NotNull TeamDTO teamDTO) {
        if (teamDTO.getName() == null) {
            throw new BadRequestException("Team name is required");
        }
        Team team = teamMapper.toEntity(teamDTO);
        Team savedTeam = teamService.add(team);
        URI location = URI.create("/api/teams/" + savedTeam.getId());
        return ResponseEntity.created(location).body(teamMapper.toDTO(savedTeam));
    }

    @GetMapping("{teamId}")
    @Operation(
            summary = "Get team by ID",
            description = "Returns the details of a team identified by the given UUID."
    )
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable("teamId") @NotNull UUID teamId) {
        Team team = teamService.findNotDeletedById(teamId);
        return ResponseEntity.ok(teamMapper.toDTO(team));
    }

    @GetMapping
    @Operation(
            summary = "Get all teams",
            description = "Returns a list of all teams. Supports optional result size, presentation order and filtering by name, and sorting by specified fields, like player cards or number of victories."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "maxPlayers", value = "Maximum number of players in a team", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Maximum number of teams to return", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "Sort order (asc or desc)", paramType = "query"),
            @ApiImplicitParam(name = "sortBy", value = "Field to sort by (playerCards or victories)", paramType = "query")
    })

    public ResponseEntity<List<TeamDTO>> getAllTeams(@ModelAttribute TeamSearchParams params,
                                                     @Parameter(description = "Ordem de apresentação: 'asc' para ordem crescente, 'dsc' para ordem decrescente", schema = @Schema(allowableValues = {"asc", "dsc"})) @RequestParam(name = "order", required = false, defaultValue = "dsc") String order,
                                                     @Parameter(description = "Tipo de ordenação: 'playerCards' para ordenar por tipo de cartas, 'victories' para ordenar por número de vitórias", schema = @Schema(allowableValues = {"playerCards", "victories"})) @RequestParam(name = "sortBy", required = false) String sortBy) {

        Stream<Team> teamStream = teamService.findAllNotDeleted(params).stream();

        teamStream = Optional.ofNullable(sortBy)
                .filter(s -> List.of("playerCards", "victories").contains(s))
                .map(s -> s.equals("playerCards")
                        ? Comparator.comparing(Team::getPlayersCardCount)
                        : Comparator.comparing(Team::getVictoryCount))
                .map(comp -> "asc".equals(order) ? comp : comp.reversed())
                .map(teamStream::sorted)
                .orElse(teamStream);

        return ResponseEntity.ok(teamStream.map(teamMapper::toDTO).toList());
    }

    @DeleteMapping("{teamId}")
    @Operation(
            summary = "Delete team by ID",
            description = "Performs a soft delete of the team identified by the given UUID. The team will be soft deleted, marked as deleted but not permanently removed."
    )
    public ResponseEntity<String> deleteTeamById(@PathVariable("teamId") @NotNull UUID teamId) {
        teamService.softDelete(teamId);
        return ResponseEntity.ok("Team deleted successfully");
    }

    @PutMapping("{teamId}")
    @Operation(
            summary = "Update team by ID",
            description = "Updates the details of a team identified by the given UUID and returns the updated team."
    )
    public ResponseEntity<TeamDTO> updateTeamById(
            @PathVariable("teamId") @NotNull UUID teamId,
            @RequestBody @Validated @NotNull TeamDTO teamDTO
    ) {
        teamDTO.setId(teamId);
        Team team = teamMapper.toEntity(teamDTO);
        Team savedTeam = teamService.update(team);
        return ResponseEntity.ok(teamMapper.toDTO(savedTeam));
    }

    @DeleteMapping("{teamId}/players/{playerId}")
    @Operation(
            summary = "Remove player from team by IDs",
            description = "Removes a player from a team identified by the given UUIDs for the team and player."
    )
    public ResponseEntity<String> removePlayerFromTeam(
            @PathVariable("teamId") @NotNull UUID teamId,
            @PathVariable("playerId") @NotNull UUID playerId
    ) {
        Team team = teamService.findNotDeletedById(teamId);
        Player player = playerService.findNotDeletedById(playerId);
        teamService.removePlayerFromTeam(player, team);
        return ResponseEntity.ok("Player removed from team successfully.");
    }

    @PostMapping("{teamId}/players/{playerId}")
    @Operation(
            summary = "Add player to team by IDs",
            description = "Adds a player to a team identified by the given UUIDs for the team and player."
    )
    public ResponseEntity<String> addPlayerToTeam(
            @PathVariable("teamId") @NotNull UUID teamId,
            @PathVariable("playerId") @NotNull UUID playerId
    ) {
        Team team = teamService.findNotDeletedById(teamId);
        Player player = playerService.findNotDeletedById(playerId);
        teamService.addPlayerToTeam(player, team);
        return ResponseEntity.ok("Player added to team successfully.");
    }

    @GetMapping("{teamId}/players")
    @Operation(
            summary = "Get players from a team by ID",
            description = "Returns a list of players belonging to a team identified by the given team ID."
    )
    public ResponseEntity<List<PlayerDTO>> getTeamPlayers(@PathVariable("teamId") @NotNull UUID teamId) {
        Team team = teamService.findNotDeletedById(teamId);
        List<PlayerDTO> players = team.getPlayers()
                .stream()
                .map(playerMapper::toDTO)
                .toList();
        return ResponseEntity.ok(players);
    }
}
