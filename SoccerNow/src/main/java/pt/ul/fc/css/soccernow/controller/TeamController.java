package pt.ul.fc.css.soccernow.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.soccernow.domain.dto.TeamDTO;
import pt.ul.fc.css.soccernow.domain.dto.user.PlayerDTO;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.mapper.PlayerMapper;
import pt.ul.fc.css.soccernow.mapper.TeamMapper;
import pt.ul.fc.css.soccernow.service.PlayerService;
import pt.ul.fc.css.soccernow.service.TeamService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

@Tag(name = "Team", description = "Team operations")
@RestController
@RequestMapping("/api/teams")
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
    @ApiOperation(value = "Register a team", notes = "Returns the team registered")
    public ResponseEntity<TeamDTO> registerTeam(@RequestBody @Validated @NotNull TeamDTO teamDTO) {
        if (teamDTO.getName() == null){
            return ResponseEntity.badRequest().build();
        }
        Team team = teamMapper.toEntity(teamDTO);
        Team savedTeam = teamService.add(team);
        return ResponseEntity.status(HttpStatus.CREATED).body(teamMapper.toDTO(savedTeam));
    }

    @GetMapping("/{teamId}")
    @ApiOperation(value = "Get team by ID", notes = "Returns a team by its ID")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable("teamId") @NotNull UUID teamId) {
        Team team = teamService.findNotDeletedById(teamId);
        return ResponseEntity.ok(teamMapper.toDTO(team));
    }

    @GetMapping
    @ApiOperation(value = "Get all teams", notes = "Returns a list of all teams")
    public ResponseEntity<List<TeamDTO>> getAllTeams(@RequestParam(name = "maxPlayers", required = false) @Min(0) Integer maxPlayers,
                                                     @RequestParam(name = "order", required = false) String order,
                                                     @RequestParam(name = "sortBy", required = false) String sortBy) {
        Comparator<Team> cardComparator = Comparator.comparing(Team::getPlayersCardCount);
        Comparator<Team> victoryComparator = Comparator.comparing(Team::getVictoryCount);
        Predicate<TeamDTO> filterPredicate = maxPlayers == null
                ? team -> true
                : team -> team.getPlayers().size() <= maxPlayers;

        Optional<Comparator<Team>> comparator = switch (sortBy) {
            case "playerCards" -> Optional.of(cardComparator);
            case "victories" -> Optional.of(victoryComparator);
            default -> Optional.empty();
        };

        Optional<Comparator<Team>> optionalTeamComparator = switch (order) {
            case "asc" -> Optional.of(victoryComparator.reversed());
            case "dsc" -> Optional.of(victoryComparator);
            default -> Optional.empty();
        };

        List<TeamDTO> teams = teamService.findAllNotDeleted()
                                         .stream()
                                         .map(teamMapper::toDTO)
                .filter(filterPredicate)
                                         .toList();
        return ResponseEntity.ok(teams);
    }

    @DeleteMapping("/{teamId}")
    @ApiOperation(value = "Delete a team with given ID")
    public ResponseEntity<String> deleteTeamById(@PathVariable("teamId") @NotNull UUID teamId) {
        teamService.softDelete(teamId);
        return ResponseEntity.ok("Team deleted successfully");
    }

    @PutMapping("/{teamId}")
    @ApiOperation(value = "Update a team with given ID", notes = "Returns the updated team")
    public ResponseEntity<TeamDTO> updateTeamById(
            @PathVariable("teamId") @NotNull UUID teamId,
            @RequestBody @Validated @NotNull TeamDTO teamDTO
    ) {
        teamDTO.setId(teamId);
        Team team = teamMapper.toEntity(teamDTO);
        Team savedTeam = teamService.update(team);
        return ResponseEntity.ok(teamMapper.toDTO(savedTeam));
    }

    @DeleteMapping("/{teamId}/players/{playerId}")
    @ApiOperation(value = "Removes a player from a team")
    public ResponseEntity<String> removePlayerFromTeam(
            @PathVariable("teamId") @NotNull UUID teamId,
            @PathVariable("playerId") @NotNull UUID playerId
    ) {
        Team team = teamService.findNotDeletedById(teamId);
        Player player = playerService.findNotDeletedById(playerId);
        teamService.removePlayerFromTeam(player, team);
        return ResponseEntity.ok("Player removed from team successfully.");
    }

    @GetMapping("/{teamId}/players")
    @ApiOperation(value = "Get the players from a team by ID", notes = "Returns the players from a team by its ID")
    public ResponseEntity<List<PlayerDTO>> getTeamPlayers(@PathVariable("teamId") @NotNull UUID teamId) {
        Team team = teamService.findNotDeletedById(teamId);
        List<PlayerDTO> players = team.getPlayers()
                                      .stream()
                                      .map(playerMapper::toDTO)
                                      .toList();
        return ResponseEntity.ok(players);
    }

//    @GetMapping("/{teamId}/placements")
//    @ApiOperation(value = "Get the placements from a team by ID", notes = "Returns the placements from a team by its ID")
//    public ResponseEntity<List<PlacementsDTO>> getTeamPlayers(@PathVariable("teamId") @NotNull UUID teamId) {
//        Team team = teamService.findNotDeletedById(teamId);
//        List<PlacementsDTO> placements = team.getPlacements()
//                .stream()
//                .map(placementMapper::toDTO)
//                .toList();
//        return ResponseEntity.ok(placements);
//    }
}
