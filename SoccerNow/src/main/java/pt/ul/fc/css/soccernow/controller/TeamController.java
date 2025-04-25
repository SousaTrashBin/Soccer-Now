package pt.ul.fc.css.soccernow.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.soccernow.domain.dto.TeamDTO;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.mapper.TeamMapper;
import pt.ul.fc.css.soccernow.service.TeamService;

import java.util.List;
import java.util.UUID;

@Tag(name = "Team", description = "Team operations")
@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;
    private final TeamMapper teamMapper;

    public TeamController(TeamService teamService, TeamMapper teamMapper) {
        this.teamService = teamService;
        this.teamMapper = teamMapper;
    }

    @PostMapping
    @ApiOperation(value = "Register a team", notes = "Returns the team registered")
    public ResponseEntity<TeamDTO> registerTeam(@RequestBody TeamDTO teamDTO) {
        if (teamDTO.getName() == null){
            return ResponseEntity.badRequest().build();
        }
        Team team = teamMapper.toEntity(teamDTO);
        Team savedTeam = teamService.add(team);
        return ResponseEntity.status(HttpStatus.CREATED).body(teamMapper.toDTO(savedTeam));
    }

    @GetMapping("/{teamId}")
    @ApiOperation(value = "Get team by ID", notes = "Returns a team by its ID")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable("teamId") UUID teamId) {
        Team team = teamService.findNotDeletedById(teamId);
        return ResponseEntity.ok(teamMapper.toDTO(team));
    }

    @GetMapping
    @ApiOperation(value = "Get all teams", notes = "Returns a list of all teams")
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        List<TeamDTO> teams = teamService.findAllNotDeleted()
                                         .stream()
                                         .map(teamMapper::toDTO)
                                         .toList();
        return ResponseEntity.ok(teams);
    }

    @DeleteMapping("/{teamId}")
    @ApiOperation(value = "Delete a team with given ID", notes = "Returns the deleted team")
    public ResponseEntity<TeamDTO> deleteTeamById(@PathVariable("teamId") UUID teamId) {
        Team team = teamService.findNotDeletedById(teamId);
        teamService.softDelete(teamId);
        return ResponseEntity.ok(teamMapper.toDTO(team));
    }

    @PutMapping("/{teamId}")
    @ApiOperation(value = "Update a team with given ID", notes = "Returns the updated team")
    public ResponseEntity<TeamDTO> updateTeamById(
            @PathVariable("teamId") UUID teamId,
            @RequestBody @Validated @NotNull TeamDTO teamDTO
    ) {
        Team team = teamMapper.toEntity(teamDTO);
        Team savedTeam = teamService.update(team);
        return ResponseEntity.ok(teamMapper.toDTO(savedTeam));
    }
}
