package pt.ul.fc.css.soccernow.service.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.game.Game;
import pt.ul.fc.css.soccernow.domain.entities.game.GameTeam;
import pt.ul.fc.css.soccernow.domain.entities.tournament.GamePlayer;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.domain.entities.user.Referee;
import pt.ul.fc.css.soccernow.exception.BadRequestException;
import pt.ul.fc.css.soccernow.exception.ResourceDoesNotExistException;
import pt.ul.fc.css.soccernow.repository.GameRepository;
import pt.ul.fc.css.soccernow.service.GameService;
import pt.ul.fc.css.soccernow.service.PlayerService;
import pt.ul.fc.css.soccernow.service.RefereeService;
import pt.ul.fc.css.soccernow.service.TeamService;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final PlayerService playerService;
    private final TeamService teamService;
    private final RefereeService refereeService;

    public GameServiceImpl(GameRepository gameRepository, @Lazy PlayerService playerService, TeamService teamService, RefereeService refereeService) {
        this.gameRepository = gameRepository;
        this.playerService = playerService;
        this.teamService = teamService;
        this.refereeService = refereeService;
    }

    @Override
    public Game add(Game entity) {
        UpdatedAndValidatedRefereesResult refereesResult = updatedAndValidatedReferees(entity);
        Referee primaryReferee = refereesResult.primaryReferee;
        Set<Referee> secondaryReferees = refereesResult.secondaryReferees;

        GameTeam validatedGameTeamOne = prepareAndValidateGameTeam(entity.getGameTeamOne());
        GameTeam validateGameTeamTwo = prepareAndValidateGameTeam(entity.getGameTeamTwo());

        entity.setGameTeamOne(validatedGameTeamOne);
        entity.setGameTeamTwo(validateGameTeamTwo);
        entity.setPrimaryReferee(primaryReferee);
        entity.setSecondaryReferees(secondaryReferees);
        return gameRepository.save(entity);
    }

    private UpdatedAndValidatedRefereesResult updatedAndValidatedReferees(Game entity) {
        Referee primaryReferee = refereeService.findNotDeletedById(entity.getPrimaryReferee()
                                                                         .getId());
        Set<Referee> secondaryReferees = entity.getSecondaryReferees()
                                               .stream()
                                               .map(referee -> refereeService.findNotDeletedById(referee.getId()))
                                               .collect(Collectors.toSet());
        boolean hasAtLeastOneRefereeWithCertificate = primaryReferee.getHasCertificate() ||
                                                      secondaryReferees.stream()
                                                                       .anyMatch(Referee::getHasCertificate);
        if (!hasAtLeastOneRefereeWithCertificate) {
            throw new BadRequestException("At least one referee needs to have a certificate");
        }
        return new UpdatedAndValidatedRefereesResult(primaryReferee, secondaryReferees);
    }

    private GameTeam prepareAndValidateGameTeam(GameTeam gameTeam) {
        if (!gameTeam.hasTheRightSize()) {
            throw new BadRequestException("Game teams must have %d elements".formatted(GameTeam.FUTSAL_TEAM_SIZE));
        }

        if (!gameTeam.hasExactlyOneGoalKeeper()) {
            throw new BadRequestException("Game teams must contain exactly one goal keeper");
        }

        Team team = teamService.findNotDeletedById(gameTeam.getTeam()
                                                           .getId());
        Set<GamePlayer> savedPlayers = updateGameTeamPlayersWithSavedPlayers(gameTeam);

        boolean allPlayersBelongToTeam = savedPlayers.stream()
                                                     .allMatch(gamePlayer -> team.hasPlayer(gamePlayer.getPlayer()));
        if (!allPlayersBelongToTeam) {
            throw new BadRequestException("All players must be on team %s".formatted(team.getName()));
        }

        GameTeam newGameTeam = new GameTeam();
        newGameTeam.setTeam(team);
        newGameTeam.setGamePlayers(savedPlayers);
        return newGameTeam;
    }

    private Set<GamePlayer> updateGameTeamPlayersWithSavedPlayers(GameTeam gameTeam) {
        return gameTeam.getGamePlayers()
                       .stream()
                       .peek(gamePlayer -> {
                           Player savedPlayer = playerService.findNotDeletedById(
                                   gamePlayer.getPlayer()
                                             .getId()
                           );
                           gamePlayer.setPlayer(savedPlayer);
                       })
                       .collect(Collectors.toSet());
    }

    @Override
    public Game findById(UUID entityId) {
        return gameRepository.findById(entityId)
                             .orElseThrow(() -> new ResourceDoesNotExistException("Game", "id", entityId));
    }


    @Override
    public Game update(Game entity) {
        return null;
    }

    @Override
    public void softDelete(UUID entityId) {

    }

    @Override
    public List<Game> findAllNotDeleted() {
        return List.of();
    }

    @Override
    public Game findNotDeletedById(UUID entityId) {
        Game game = findById(entityId);
        if (!game.isDeleted()) {
            throw new ResourceDoesNotExistException("Game", "id", entityId);
        }
        return game;
    }

    @Override
    public boolean hasPendingGame(Player player, Team team) {
        return team.getGameTeams()
                   .stream()
                   .filter(gameTeam -> !gameTeam.getGame()
                                                .isFinished())
                   .anyMatch(gameTeam -> gameTeam.hasPlayer(player));
    }

    @Override
    public boolean teamHasPendingGame(Team team) {
        return team.getGameTeams()
                   .stream()
                   .anyMatch(gameTeam -> !gameTeam.getGame()
                                                             .isFinished());
    }

    private record UpdatedAndValidatedRefereesResult(Referee primaryReferee, Set<Referee> secondaryReferees) {
    }
}
