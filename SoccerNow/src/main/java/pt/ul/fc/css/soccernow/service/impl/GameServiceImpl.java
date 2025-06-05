package pt.ul.fc.css.soccernow.service.impl;

import org.springframework.stereotype.Service;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.game.*;
import pt.ul.fc.css.soccernow.domain.entities.tournament.Tournament;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.domain.entities.user.Referee;
import pt.ul.fc.css.soccernow.domain.entities.user.User;
import pt.ul.fc.css.soccernow.exception.BadRequestException;
import pt.ul.fc.css.soccernow.exception.ResourceCouldNotBeDeletedException;
import pt.ul.fc.css.soccernow.exception.ResourceDoesNotExistException;
import pt.ul.fc.css.soccernow.repository.GameRepository;
import pt.ul.fc.css.soccernow.service.GameService;
import pt.ul.fc.css.soccernow.service.PlayerService;
import pt.ul.fc.css.soccernow.service.RefereeService;
import pt.ul.fc.css.soccernow.service.TeamService;
import pt.ul.fc.css.soccernow.util.GameSearchParams;
import pt.ul.fc.css.soccernow.util.GameStatusEnum;
import pt.ul.fc.css.soccernow.util.TournamentStatusEnum;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static pt.ul.fc.css.soccernow.domain.entities.game.GameTeam.FUTSAL_TEAM_SIZE;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final PlayerService playerService;
    private final TeamService teamService;
    private final RefereeService refereeService;

    public GameServiceImpl(GameRepository gameRepository,
                           PlayerService playerService,
                           TeamService teamService,
                           RefereeService refereeService) {
        this.gameRepository = gameRepository;
        this.playerService = playerService;
        this.teamService = teamService;
        this.refereeService = refereeService;
    }

    @Override
    public Game cancelTournamentGame(UUID gameId) {
        Game savedGame = findNotDeletedById(gameId);
        if (savedGame.getTournament() == null) {
            throw new BadRequestException("Game is not part of a tournament");
        }
        if (savedGame.getTournament().getStatus() != TournamentStatusEnum.IN_PROGRESS) {
            throw new BadRequestException("Tournament must be in progress");
        }
        savedGame.cancel();
        return gameRepository.save(savedGame);
    }

    @Override
    public Game add(Game entity) {
        Game newGame = new Game();
        newGame.setLocatedIn(entity.getLocatedIn());
        newGame.setHappensIn(entity.getHappensIn());

        UpdatedAndValidatedRefereesResult refereesResult = updatedAndValidatedReferees(entity);
        Referee primaryReferee = refereesResult.primaryReferee;
        Set<Referee> secondaryReferees = refereesResult.secondaryReferees;
        newGame.registerPrimaryReferee(primaryReferee);
        secondaryReferees.forEach(newGame::registerSecondaryReferee);

        GameTeam validatedGameTeamOne = prepareAndValidateGameTeam(entity.getGameTeamOne());
        validatedGameTeamOne.getTeam().addGame(newGame);
        GameTeam validatedGameTeamTwo = prepareAndValidateGameTeam(entity.getGameTeamTwo());
        validatedGameTeamTwo.getTeam().addGame(newGame);

        verifyNoPlayerOverlap(validatedGameTeamOne, validatedGameTeamTwo);
        newGame.registerGameTeams(validatedGameTeamOne, validatedGameTeamTwo);

        return gameRepository.save(newGame);
    }


    private void verifyNoPlayerOverlap(GameTeam validatedGameTeamOne, GameTeam validatedGameTeamTwo) {
        if (validatedGameTeamOne.getPlayers().stream().anyMatch(validatedGameTeamTwo::hasPlayer)) {
            throw new BadRequestException("There are overlapped players between both game teams");
        }
    }


    private UpdatedAndValidatedRefereesResult updatedAndValidatedReferees(Game entity) {
        Referee primaryReferee = refereeService.findNotDeletedById(entity.getPrimaryReferee()
                .getId());
        Set<Referee> secondaryReferees = entity.getSecondaryReferees()
                .stream()
                .map(referee -> refereeService.findNotDeletedById(referee.getId()))
                .collect(Collectors.toSet());
        boolean hasAtLeastOneRefereeWithCertificate = primaryReferee.getHasCertificate() ||
                secondaryReferees.stream().anyMatch(Referee::getHasCertificate);
        if (entity.isACompetitiveGame() && !hasAtLeastOneRefereeWithCertificate) {
            throw new BadRequestException("At least one referee needs to have a certificate");
        }
        return new UpdatedAndValidatedRefereesResult(primaryReferee, secondaryReferees);
    }

    private GameTeam prepareAndValidateGameTeam(GameTeam gameTeam) {
        if (!gameTeam.hasTheRightSize()) {
            throw new BadRequestException("Game teams must have %d elements".formatted(FUTSAL_TEAM_SIZE));
        }

        if (!gameTeam.hasExactlyOneGoalKeeper()) {
            throw new BadRequestException("Game teams must contain exactly one goal keeper");
        }

        Team team = teamService.findNotDeletedById(gameTeam.getTeam().getId());
        if (!gameTeam.hasAllPlayersOnTeam(team)) {
            throw new BadRequestException("All players must be on team %s".formatted(team.getName()));
        }

        gameTeam.getGamePlayers().forEach(gamePlayer -> gamePlayer.setPlayer(playerService.findNotDeletedById(gamePlayer.getPlayer().getId())));
        GameTeam newGameTeam = new GameTeam();
        newGameTeam.registerTeam(team);
        newGameTeam.setGamePlayers(gameTeam.getGamePlayers());
        return newGameTeam;
    }

    @Override
    public Game findById(UUID entityId) {
        return gameRepository.findById(entityId).orElseThrow(() -> new ResourceDoesNotExistException("Game", "id", entityId));
    }


    @Override //for this phase this use case doesn't need to be implemented
    public Game update(Game entity) {
        return null;
    }

    @Override
    public void softDelete(UUID entityId) {
        Game gameToDelete = findNotDeletedById(entityId);
        if (!gameToDelete.getStatus().equals(GameStatusEnum.CLOSED)) {
            throw new ResourceCouldNotBeDeletedException("Game", "id", entityId);
        }
        gameToDelete.delete();
        gameRepository.save(gameToDelete);
    }

    @Override
    public List<Game> findAllNotDeleted() {
        return gameRepository.findAllNotDeleted();
    }

    @Override
    public Game findNotDeletedById(UUID entityId) {
        Game game = findById(entityId);
        if (game.isDeleted()) {
            throw new ResourceDoesNotExistException("Game", "id", entityId);
        }
        return game;
    }

    public Game closeGame(UUID gameID, Set<PlayerGameStats> incomingPlayerStats) {
        Game game = validateGameIsOpenAndExists(gameID);
        valitePlayerGameStatsDTO(game, incomingPlayerStats);
        GameStats gameStats = calculateGameStats(game, incomingPlayerStats);
        game.setGameStats(gameStats);
        game.close();
        updateTournamentScore(game);
        return gameRepository.save(game);
    }

    private void updateTournamentScore(Game game) {
        Tournament tournament = game.getTournament();
        if (tournament == null) {
            return;
        }

        GameTeam gameTeamOne = game.getGameTeamOne();
        GameTeam gameTeamTwo = game.getGameTeamTwo();

        GameStats gameStats = game.getGameStats();
        tournament.updateScore(gameTeamOne, gameTeamTwo, gameStats);
    }

    @Override
    public List<Game> findAllNotDeleted(GameSearchParams params) {
        return gameRepository.findAllNotDeleted(params);
    }

    private void valitePlayerGameStatsDTO(Game game, Set<PlayerGameStats> playerGameStatsDTOs) {
        boolean hasDuplicatePlayers = playerGameStatsDTOs.stream()
                .map(PlayerGameStats::getPlayer)
                .map(User::getId)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .values()
                .stream()
                .anyMatch(count -> count > 1);
        if (hasDuplicatePlayers) {
            throw new BadRequestException("Please remove the duplicate players present on the player stats");
        }
        for (Referee referee : playerGameStatsDTOs.stream().flatMap(playerGameStats -> playerGameStats.getReceivedCards().stream())
                .map(Card::getReferee)
                .toList()) {
            referee = refereeService.findNotDeletedById(referee.getId());
            if (!game.hasReferee(referee)) {
                throw new BadRequestException("Only referees registered in the game can give cards");
            }
        }
    }

    private Game validateGameIsOpenAndExists(UUID gameID) {
        Game game = findNotDeletedById(gameID);
        if (game.getStatus().equals(GameStatusEnum.CLOSED)) {
            throw new BadRequestException("Game is already closed");
        }
        return game;
    }

    private GameStats calculateGameStats(Game game, Set<PlayerGameStats> playerStats) {
        GoalsScored goalsScored = new GoalsScored();
        GameStats gameStats = new GameStats();
        Set<Player> playersInGame = game.getPlayers();
        Map<UUID, Player> playersById = playersInGame.stream()
                .collect(Collectors.toMap(Player::getId, Function.identity()));

        for (PlayerGameStats playerStat : playerStats) {
            Player saved = playersById.get(playerStat.getPlayer().getId());

            PlayerGameStats playerGameStats = new PlayerGameStats();
            Integer scoredGoals = playerStat.getScoredGoals();
            playerGameStats.setScoredGoals(scoredGoals);
            playerGameStats.setPlayer(playerStat.getPlayer());
            Set<Card> cards = new LinkedHashSet<>();
            for (Card card : playerStat.getReceivedCards()) {
                Card newCard = new Card();
                newCard.setCardType(card.getCardType());
                newCard.setPlayer(playerGameStats);

                Referee referee = refereeService.findNotDeletedById(card.getReferee().getId());
                newCard.setReferee(referee);
                referee.addIssuedCard(newCard);
                cards.add(newCard);
            }
            playerGameStats.setReceivedCards(cards);
            playerGameStats.setGame(game);

            saved.registerGameStats(playerGameStats);
            gameStats.registerPlayerGameStats(playerGameStats);

            goalsScored = goalsScored.addGoalsToPlayer(saved, game, scoredGoals);
            playersInGame.remove(saved);
        }

        for (Player player : playersInGame) {
            PlayerGameStats defaultStats = new PlayerGameStats();
            defaultStats.setGame(game);
            player.registerGameStats(defaultStats);
            gameStats.registerPlayerGameStats(defaultStats);
        }

        gameStats.setTeamOneGoals(goalsScored.teamOneGoals());
        gameStats.setTeamTwoGoals(goalsScored.teamTwoGoals());
        return gameStats;
    }

    private record GoalsScored(int teamOneGoals, int teamTwoGoals) {
        GoalsScored() {
            this(0, 0);
        }

        GoalsScored addTeamOneGoals(int goals) {
            return new GoalsScored(teamOneGoals + goals, teamTwoGoals);
        }

        GoalsScored addTeamTwoGoals(int goals) {
            return new GoalsScored(teamOneGoals, teamTwoGoals + goals);
        }

        GoalsScored addGoalsToPlayer(Player player, Game game, int goals) {
            if (game.getGameTeamOne().hasPlayer(player)) {
                return addTeamOneGoals(goals);
            } else if (game.getGameTeamTwo().hasPlayer(player)) {
                return addTeamTwoGoals(goals);
            } else {
                throw new BadRequestException("Player with ID %s is not playing in the given game".formatted(player.getId()));
            }
        }
    }

    private record UpdatedAndValidatedRefereesResult(Referee primaryReferee, Set<Referee> secondaryReferees) {
    }
}
