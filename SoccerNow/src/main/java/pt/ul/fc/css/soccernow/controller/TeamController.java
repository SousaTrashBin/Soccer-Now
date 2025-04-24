package pt.ul.fc.css.soccernow.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.soccernow.domain.dto.TeamDTO;
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
    public ResponseEntity<TeamDTO> registerTeam(@RequestBody TeamDTO team) {
        // TODO
        return null;
    }

    @GetMapping("/{teamId}")
    @ApiOperation(value = "Get team by ID", notes = "Returns a team by its ID")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable("teamId") UUID teamId) {
        // TODO
        return null;
    }

    @GetMapping
    @ApiOperation(value = "Get all teams", notes = "Returns a list of all teams")
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        // TODO
        return null;
    }

    @DeleteMapping("/{teamId}")
    @ApiOperation(value = "Delete a team with given ID", notes = "Returns the deleted team")
    public ResponseEntity<TeamDTO> deleteTeamById(@PathVariable("teamId") UUID teamId) {
        // TODO
        return null;
    }

    @PutMapping("/{teamId}")
    @ApiOperation(value = "Update a team with given ID", notes = "Returns the updated team")
    public ResponseEntity<TeamDTO> updateTeamById(@PathVariable("teamId") UUID teamId) {
        // TODO
        return null;
    }
}
