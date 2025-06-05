package pt.ul.fc.css.soccernow.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.soccernow.domain.dto.tournament.PointTournamentDTO;
import pt.ul.fc.css.soccernow.domain.entities.tournament.point.PointTournament;
import pt.ul.fc.css.soccernow.mapper.TournamentMapper;
import pt.ul.fc.css.soccernow.service.PointTournamentService;
import pt.ul.fc.css.soccernow.util.TournamentSearchParams;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Tag(name = "Point Tournament", description = "Point Tournament related operations")
@RestController
@RequestMapping("/api/point-tournaments/")
public class PointTournamentController {
    private final PointTournamentService pointTournamentService;
    private final TournamentMapper tournamentMapper;

    public PointTournamentController(PointTournamentService pointTournamentService,
                                     TournamentMapper tournamentMapper) {
        this.pointTournamentService = pointTournamentService;
        this.tournamentMapper = tournamentMapper;
    }

    @GetMapping("{tournamentId}")
    @Operation(
            summary = "Get tournament by ID",
            description = "Returns the details of a tournament identified by the given UUID."
    )
    public ResponseEntity<PointTournamentDTO> getTournamentById(@PathVariable @NotNull UUID tournamentId) {
        PointTournament pointTournament = pointTournamentService.findNotDeletedById(tournamentId);
        return ResponseEntity.ok(tournamentMapper.toDTO(pointTournament));
    }

    @PostMapping
    @Operation(summary = "Create a new point tournament with a name")
    public ResponseEntity<PointTournamentDTO> createTournament(@RequestBody @Valid CreatePointTournamentDTO createPointTournamentDTO) {
        PointTournament savedPointTournament = pointTournamentService.add(createPointTournamentDTO.toEntity());
        URI location = URI.create("/api/point-tournaments/" + savedPointTournament.getId());
        return ResponseEntity.created(location).body(tournamentMapper.toDTO(savedPointTournament));
    }

    @PatchMapping("{tournamentId}/close-registrations")
    @Operation(summary = "Close tournament registrations")
    public ResponseEntity<PointTournamentDTO> closeRegistrations(@PathVariable @NotNull UUID tournamentId) {
        PointTournament updatedPointTournament = pointTournamentService.closeRegistrations(tournamentId);
        return ResponseEntity.ok(tournamentMapper.toDTO(updatedPointTournament));
    }

    @PatchMapping("{tournamentId}/end")
    @Operation(summary = "End tournament")
    public ResponseEntity<PointTournamentDTO> endTournament(@PathVariable @NotNull UUID tournamentId) {
        PointTournament updatedPointTournament = pointTournamentService.endTournament(tournamentId);
        return ResponseEntity.ok(tournamentMapper.toDTO(updatedPointTournament));
    }

    @GetMapping
    @Operation(
            summary = "Get all non deleted point tournaments",
            description = "Returns the details of all non deleted point tournaments."
    )
    public ResponseEntity<List<PointTournamentDTO>> getAllTournaments(@ModelAttribute TournamentSearchParams params) {
        List<PointTournament> allNotDeleted = pointTournamentService.findAllNotDeleted(params);
        return ResponseEntity.ok(allNotDeleted.stream().map(tournamentMapper::toDTO).toList());
    }

    @PostMapping("{tournamentId}/games/{gameId}")
    public ResponseEntity<PointTournamentDTO> addGameToTournament(
            @PathVariable @NotNull UUID tournamentId,
            @PathVariable @NotNull UUID gameId) {
        PointTournament updatedPointTournament = pointTournamentService.addGameToTournament(gameId, tournamentId);
        return ResponseEntity.ok(tournamentMapper.toDTO(updatedPointTournament));
    }

    @DeleteMapping("{tournamentId}/games/{gameId}")
    public ResponseEntity<PointTournamentDTO> removeGameFromTournament(
            @PathVariable @NotNull UUID tournamentId,
            @PathVariable @NotNull UUID gameId) {
        PointTournament updatedPointTournament = pointTournamentService.removeGameFromTournament(gameId, tournamentId);
        return ResponseEntity.ok(tournamentMapper.toDTO(updatedPointTournament));
    }

    @PostMapping("{tournamentId}/teams/{teamId}")
    public ResponseEntity<PointTournamentDTO> addTeamToTournament(
            @PathVariable @NotNull UUID tournamentId,
            @PathVariable @NotNull UUID teamId) {
        PointTournament updatedPointTournament = pointTournamentService.addTeamToTournament(teamId, tournamentId);
        return ResponseEntity.ok(tournamentMapper.toDTO(updatedPointTournament));
    }

    @DeleteMapping("{tournamentId}/teams/{teamId}")
    public ResponseEntity<PointTournamentDTO> removeTeamFromTournament(
            @PathVariable @NotNull UUID tournamentId,
            @PathVariable @NotNull UUID teamId) {
        PointTournament updatedPointTournament = pointTournamentService.removeTeamFromTournament(teamId, tournamentId);
        return ResponseEntity.ok(tournamentMapper.toDTO(updatedPointTournament));
    }

    @DeleteMapping("{tournamentId}")
    @Operation(summary = "Delete a tournament", description = "Marks the tournament as deleted by the given UUID.")
    public ResponseEntity<String> deleteTournament(@PathVariable @NotNull UUID tournamentId) {
        pointTournamentService.deleteTournament(tournamentId);
        return ResponseEntity.ok("Tournament deleted successfully");
    }

    public record CreatePointTournamentDTO(@NotNull String name) {
        PointTournament toEntity() {
            PointTournament pointTournament = new PointTournament();
            pointTournament.setName(name);
            return pointTournament;
        }
    }
}
