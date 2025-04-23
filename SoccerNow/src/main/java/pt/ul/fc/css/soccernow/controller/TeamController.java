package pt.ul.fc.css.soccernow.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.soccernow.domain.dto.teams.reference.TeamDto;
import pt.ul.fc.css.soccernow.service.TeamService;

import java.util.List;

@Tag(name = "Team", description = "Team operations")
@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    @ApiOperation(value = "Register a team", notes = "Returns the team registered")
    public ResponseEntity<TeamDto> registerTeam(@RequestBody TeamDto team) {
        // TODO
        return null;
    }

    @GetMapping("/{teamId}")
    @ApiOperation(value = "Get team by ID", notes = "Returns a team by its ID")
    public ResponseEntity<TeamDto> getTeamById(@PathVariable("teamId") long teamId) {
        // TODO
        return null;
    }

    @GetMapping
    @ApiOperation(value = "Get all teams", notes = "Returns a list of all teams")
    public ResponseEntity<List<TeamDto>> getAllTeams() {
        // TODO
        return null;
    }

    @DeleteMapping("/{teamId}")
    @ApiOperation(value = "Delete a team with given ID", notes = "Returns the deleted team")
    public ResponseEntity<TeamDto> deleteTeamById(@PathVariable("teamId") long teamId) {
        // TODO
        return null;
    }

    @PutMapping("/{teamId}")
    @ApiOperation(value = "Update a team with given ID", notes = "Returns the updated team")
    public ResponseEntity<TeamDto> updateTeamById(@PathVariable("teamId") long teamId) {
        // TODO
        return null;
    }
}
