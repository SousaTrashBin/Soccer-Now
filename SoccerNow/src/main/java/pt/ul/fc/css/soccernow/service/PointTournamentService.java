package pt.ul.fc.css.soccernow.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.soccernow.domain.entities.tournament.point.PointTournament;
import pt.ul.fc.css.soccernow.util.TournamentSearchParams;

import java.util.List;
import java.util.UUID;

public interface PointTournamentService extends CrudService<PointTournament> {
    List<PointTournament> findAllNotDeleted(TournamentSearchParams params);

    @Transactional
    PointTournament addGameToTournament(UUID gameId, UUID tournamentId);

    @Transactional
    PointTournament removeGameFromTournament(@NotNull UUID gameId, @NotNull UUID tournamentId);

    @Transactional
    PointTournament addTeamToTournament(@NotNull UUID teamId, @NotNull UUID tournamentId);

    @Transactional
    PointTournament removeTeamFromTournament(@NotNull UUID teamId, @NotNull UUID tournamentId);

    @Transactional
    PointTournament closeRegistrations(@NotNull UUID tournamentId);

    @Transactional
    PointTournament endTournament(@NotNull UUID tournamentId);
}
