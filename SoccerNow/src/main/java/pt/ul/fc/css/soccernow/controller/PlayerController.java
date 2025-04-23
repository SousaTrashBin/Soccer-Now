package pt.ul.fc.css.soccernow.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.soccernow.domain.dto.user.PlayerDTO;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.mapper.PlayerMapper;
import pt.ul.fc.css.soccernow.service.PlayerService;

import java.util.List;
import java.util.UUID;

@Tag(name = "Player", description = "Player operations")
@RestController
@RequestMapping("/api/players/")
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerMapper playerMapper;

    public PlayerController(PlayerService playerService, PlayerMapper playerMapper) {
        this.playerService = playerService;
        this.playerMapper = playerMapper;
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
        // TODO
        return null;
    }

    @GetMapping
    @ApiOperation(value = "Get all players", notes = "Returns a list of all players")
    public ResponseEntity<List<PlayerDTO>> getAllPlayers() {
        // TODO
        return null;
    }

    @DeleteMapping("/{playerId}")
    @ApiOperation(value = "Delete a player with given ID", notes = "Returns the deleted player")
    public ResponseEntity<PlayerDTO> deletePlayerById(@PathVariable("playerId") UUID playerId) {
        // TODO
        return null;
    }

    @PutMapping("/{playerId}")
    @ApiOperation(value = "Update a player with given ID", notes = "Returns the updated player")
    public ResponseEntity<PlayerDTO> updatePlayerById(@PathVariable("playerId") UUID playerId) {
        // TODO
        return null;
    }
}
