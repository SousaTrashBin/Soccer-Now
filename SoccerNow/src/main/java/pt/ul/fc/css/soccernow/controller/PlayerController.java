package pt.ul.fc.css.soccernow.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.soccernow.domain.dto.user.reference.PlayerDto;
import pt.ul.fc.css.soccernow.service.PlayerService;

import java.util.List;

@Tag(name = "Player", description = "Player operations")
@RestController
@RequestMapping("/api/players/")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    @ApiOperation(value = "Register a player", notes = "Returns the player registered")
    public ResponseEntity<PlayerDto> registerPlayer(@RequestBody PlayerDto player) {
        // TODO
        return null;
    }

    @GetMapping("/{playerId}")
    @ApiOperation(value = "Get player by ID", notes = "Returns a player by its ID")
    public ResponseEntity<PlayerDto> getPlayerById(@PathVariable("playerId") long playerId) {
        // TODO
        return null;
    }

    @GetMapping
    @ApiOperation(value = "Get all players", notes = "Returns a list of all players")
    public ResponseEntity<List<PlayerDto>> getAllPlayers() {
        // TODO
        return null;
    }

    @DeleteMapping("/{playerId}")
    @ApiOperation(value = "Delete a player with given ID", notes = "Returns the deleted player")
    public ResponseEntity<PlayerDto> deletePlayerById(@PathVariable("playerId") long playerId) {
        // TODO
        return null;
    }

    @PutMapping("/{playerId}")
    @ApiOperation(value = "Update a player with given ID", notes = "Returns the updated player")
    public ResponseEntity<PlayerDto> updatePlayerById(@PathVariable("playerId") long playerId) {
        // TODO
        return null;
    }
}
