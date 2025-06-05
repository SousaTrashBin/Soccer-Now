package pt.ul.fc.css.soccernow.service.impl;

import org.springframework.stereotype.Service;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.game.Game;
import pt.ul.fc.css.soccernow.domain.entities.tournament.point.PointTournament;
import pt.ul.fc.css.soccernow.domain.entities.tournament.point.TeamPoints;
import pt.ul.fc.css.soccernow.exception.BadRequestException;
import pt.ul.fc.css.soccernow.exception.ResourceDoesNotExistException;
import pt.ul.fc.css.soccernow.repository.PointTournamentRepository;
import pt.ul.fc.css.soccernow.service.GameService;
import pt.ul.fc.css.soccernow.service.PointTournamentService;
import pt.ul.fc.css.soccernow.service.TeamService;
import pt.ul.fc.css.soccernow.util.GameStatusEnum;
import pt.ul.fc.css.soccernow.util.TournamentSearchParams;
import pt.ul.fc.css.soccernow.util.TournamentStatusEnum;

import java.util.List;
import java.util.UUID;

@Service
public class PointTournamentServiceImpl implements PointTournamentService {
    private final PointTournamentRepository pointTournamentRepository;
    private final GameService gameService;
    private final TeamService teamService;

    public PointTournamentServiceImpl(PointTournamentRepository pointTournamentRepository, GameService gameService, TeamService teamService) {
        this.pointTournamentRepository = pointTournamentRepository;
        this.gameService = gameService;
        this.teamService = teamService;
    }

    @Override
    public PointTournament add(PointTournament entity) {
        PointTournament pointTournament = new PointTournament();
        pointTournament.setName(entity.getName());
        return pointTournamentRepository.save(pointTournament);
    }

    @Override
    public PointTournament update(PointTournament entity) {
        return null; // doesn't need to be implemented for now i think
    }

    @Override
    public void softDelete(UUID entityId) {
        // doesn't need to be implemented for now i think
    }

    @Override
    public List<PointTournament> findAllNotDeleted() {
        return pointTournamentRepository.findAllNotDeleted();
    }

    @Override
    public PointTournament findById(UUID entityId) {
        return pointTournamentRepository.findById(entityId)
                .orElseThrow(() -> new ResourceDoesNotExistException("Point Tournament", "id", entityId));
    }

    @Override
    public PointTournament findNotDeletedById(UUID entityId) {
        PointTournament pointTournament = findById(entityId);
        if (pointTournament.isDeleted()) {
            throw new ResourceDoesNotExistException("Point Tournament", "id", pointTournament);
        }
        return pointTournament;
    }

    @Override
    public List<PointTournament> findAllNotDeleted(TournamentSearchParams params) {
        return pointTournamentRepository.findAllNotDeleted(params);
    }

    @Override
    public PointTournament addGameToTournament(UUID gameId, UUID tournamentId) {
        PointTournament savedPointTournament = findNotDeletedById(tournamentId);
        if (savedPointTournament.getStatus() != TournamentStatusEnum.IN_PROGRESS) {
            throw new BadRequestException("Tournament must be in progress in order to add new games to it");
        }
        Game savedGame = gameService.findNotDeletedById(gameId);
        if (savedGame.getStatus() != GameStatusEnum.OPENED) {
            throw new BadRequestException("Game must be opened in order to be added to a tournament");
        }
        if (savedPointTournament.hasGame(savedGame)) {
            throw new BadRequestException("Game is already in the tournament");
        }
        if (!savedPointTournament.hasTeam(savedGame.getGameTeamOne().getTeam())
                || !savedPointTournament.hasTeam(savedGame.getGameTeamTwo().getTeam())) {
            throw new BadRequestException("A tournament game must be between teams that are registered on it");
        }
        savedPointTournament.addGame(savedGame);
        return pointTournamentRepository.save(savedPointTournament);
    }

    @Override
    public PointTournament removeGameFromTournament(UUID gameId, UUID tournamentId) {
        PointTournament savedPointTournament = findNotDeletedById(tournamentId);
        if (savedPointTournament.getStatus() != TournamentStatusEnum.IN_PROGRESS) {
            throw new BadRequestException("Tournament must be in progress in order to add new games to it");
        }
        Game savedGame = gameService.findNotDeletedById(gameId);
        if (savedGame.getStatus() != GameStatusEnum.OPENED) {
            throw new BadRequestException("Game must be opened in order to be added to a tournament");
        }
        if (!savedPointTournament.hasGame(savedGame)) {
            throw new BadRequestException("Game is not part of the tournament");
        }
        savedPointTournament.removeGame(savedGame);
        return pointTournamentRepository.save(savedPointTournament);
    }


    @Override
    public PointTournament addTeamToTournament(UUID teamId, UUID tournamentId) {
        PointTournament savedPointTournament = findNotDeletedById(tournamentId);
        if (savedPointTournament.getStatus() != TournamentStatusEnum.OPEN) {
            throw new BadRequestException("Tournament must be opened in order to add new teams to it");
        }
        Team savedTeam = teamService.findNotDeletedById(teamId);
        if (savedPointTournament.hasTeam(savedTeam)) {
            throw new BadRequestException("Team is already in the tournament");
        }
        savedPointTournament.addTeam(savedTeam);
        return pointTournamentRepository.save(savedPointTournament);
    }

    @Override
    public PointTournament removeTeamFromTournament(UUID teamId, UUID tournamentId) {
        PointTournament savedPointTournament = findNotDeletedById(tournamentId);
        if (savedPointTournament.getStatus() != TournamentStatusEnum.OPEN) {
            throw new BadRequestException("Tournament must be opened in order to add new teams to it");
        }
        Team savedTeam = teamService.findNotDeletedById(teamId);
        if (!savedPointTournament.hasTeam(savedTeam)) {
            throw new BadRequestException("Team is not part of the tournament");
        }
        savedPointTournament.removeTeam(savedTeam);
        return pointTournamentRepository.save(savedPointTournament);
    }

    @Override
    public PointTournament closeRegistrations(UUID tournamentId) {
        PointTournament tournament = findNotDeletedById(tournamentId);
        if (tournament.getStatus() != TournamentStatusEnum.OPEN) {
            throw new BadRequestException("Tournament is not open for registration");
        }
        if (!tournament.hasMinimumTeams()) {
            throw new BadRequestException("Tournament must have at least " + PointTournament.MINIMUM_TEAM_SIZE + " teams in order to close registrations");
        }
        tournament.setStatus(TournamentStatusEnum.IN_PROGRESS);
        return pointTournamentRepository.save(tournament);
    }

    @Override
    public PointTournament endTournament(UUID tournamentId) {
        PointTournament tournament = findNotDeletedById(tournamentId);
        if (!tournament.getStatus().equals(TournamentStatusEnum.IN_PROGRESS)) {
            throw new BadRequestException("Tournament is not in progress.");
        }
        if(tournament.getGames().stream().noneMatch(game -> game.getStatus() == GameStatusEnum.OPENED)){
            throw new BadRequestException("A tournament with opened games cannot end, please cancel or finish pending games first.");
        };
        tournament.setStatus(TournamentStatusEnum.CLOSED);

        List<TeamPoints> sortedTeamPoints = tournament.getTeamPoints();

        for (int i = 0; i < sortedTeamPoints.size(); i++) {
            TeamPoints teamPoints = sortedTeamPoints.get(i);
            teamPoints.updatePlacementForTournament(tournament, i);
        }

        return pointTournamentRepository.save(tournament);
    }

    @Override
    public void deleteTournament(UUID tournamentId) {
        PointTournament savedPointTournament = findNotDeletedById(tournamentId);
        if (savedPointTournament.getStatus() != TournamentStatusEnum.CLOSED) {
            throw new BadRequestException("Tournament must be closed in order to be deleted");
        }
        savedPointTournament.delete();
        pointTournamentRepository.save(savedPointTournament);
    }

}
