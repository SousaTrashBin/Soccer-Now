package pt.ul.fc.css.soccernow.util;

import org.springframework.stereotype.Component;
import pt.ul.fc.css.soccernow.domain.entities.Address;
import pt.ul.fc.css.soccernow.domain.entities.Credentials;
import pt.ul.fc.css.soccernow.domain.entities.CredentialsRepository;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.game.Game;
import pt.ul.fc.css.soccernow.domain.entities.game.GameTeam;
import pt.ul.fc.css.soccernow.domain.entities.game.PlayerGameStats;
import pt.ul.fc.css.soccernow.domain.entities.tournament.GamePlayer;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.domain.entities.user.Referee;
import pt.ul.fc.css.soccernow.service.GameService;
import pt.ul.fc.css.soccernow.service.PlayerService;
import pt.ul.fc.css.soccernow.service.RefereeService;
import pt.ul.fc.css.soccernow.service.TeamService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DummyDataGenerator {

    private final GameService gameService;
    private final PlayerService playerService;
    private final RefereeService refereeService;
    private final TeamService teamService;
    private final CredentialsRepository credentialsRepository;

    private static final String[] PLAYERS_NAMES = {
        "Lucas",
        "Gabriel",
        "Miguel",
        "João",
        "Pedro",
        "Mateus",
        "Henrique",
        "Gustavo",
        "Caio",
        "Felipe"
    };

    private static final String[] REF_NAMES = {
        "Rafael",
        "Bruno",
        "Tiago"
    };

    public DummyDataGenerator(GameService gameService, PlayerService playerService, RefereeService refereeService, TeamService teamService,
                              CredentialsRepository credentialsRepository) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.refereeService = refereeService;
        this.teamService = teamService;
        this.credentialsRepository = credentialsRepository;
    }

    public void generateDummyData() {
        Team firstTeam = createTeamWithName("A");
        Team secondTeam = createTeamWithName("B");
        List<Player> firstTeamPlayers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            firstTeamPlayers.add(createPlayerWithName(PLAYERS_NAMES[i], firstTeam));
        }
        List<Player> secondTeamPlayers = new ArrayList<>();
        for (int i = 5; i < 10; i++) {
            secondTeamPlayers.add(createPlayerWithName(PLAYERS_NAMES[i], secondTeam));
        }
        List<Referee> referees = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            referees.add(createRefereeWithName(REF_NAMES[i]));
        }
//        createNewGameWith(firstTeamPlayers, secondTeamPlayers, firstTeam, secondTeam, referees);
//        Game closedGame = createNewGameWith(firstTeamPlayers, secondTeamPlayers, firstTeam, secondTeam, referees);
//        gameService.closeGame(closedGame.getId(), getPlayerStats(firstTeamPlayers, secondTeamPlayers, closedGame));

        Credentials adminCredentials = new Credentials();
        adminCredentials.setUsername("admin");
        adminCredentials.setPassword("admin");
        credentialsRepository.save(adminCredentials);
    }

    private Set<PlayerGameStats> getPlayerStats(List<Player> firstTeamPlayers, List<Player> secondTeamPlayers, Game game) {
        PlayerGameStats playerOneGameStats = new PlayerGameStats();
        playerOneGameStats.setPlayer(firstTeamPlayers.get(0));
        //playerOneGameStats.setGivenCard(CardEnum.RED);
        playerOneGameStats.setScoredGoals(10);
        playerOneGameStats.setGame(game);

        PlayerGameStats playerTwoGameStats = new PlayerGameStats();
        playerTwoGameStats.setPlayer(secondTeamPlayers.get(0));
        //playerTwoGameStats.setGivenCard(CardEnum.YELLOW);
        playerTwoGameStats.setScoredGoals(5);
        playerTwoGameStats.setGame(game);

        return Set.of(playerOneGameStats, playerTwoGameStats);
    }

    private Game createNewGameWith(List<Player> aPlayers, List<Player> bPlayers, Team firstTeam, Team secondTeam, List<Referee> referees) {
        Game game = new Game();
        game.setHappensIn(LocalDateTime.now().plusHours(1));

        Address address = new Address();
        address.setStreet("Alverca");
        address.setCity("Lisboa");
        address.setCountry("Portugal");
        address.setPostalCode("2612-234");
        game.setLocatedIn(address);

        game.setPrimaryReferee(referees.get(new Random().nextInt(referees.size())));

        GameTeam gameTeamOne = getGameTeam(aPlayers, firstTeam);
        game.setGameTeamOne(gameTeamOne);

        GameTeam gameTeamTwo = getGameTeam(bPlayers, secondTeam);
        game.setGameTeamTwo(gameTeamTwo);

        return gameService.add(game);
    }

    private GameTeam getGameTeam(List<Player> players, Team team) {
        HashSet<GamePlayer> gamePlayers = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            FutsalPositionEnum futsalPosition = FutsalPositionEnum.values()[i];
            GamePlayer gamePlayer = new GamePlayer();
            gamePlayer.setPlayedInPosition(futsalPosition);
            gamePlayer.setPlayer(players.get(i));
            gamePlayers.add(gamePlayer);
        }
        GameTeam gameTeamOne = new GameTeam();
        gameTeamOne.setTeam(team);
        gameTeamOne.setGamePlayers(gamePlayers);
        return gameTeamOne;
    }

    private Referee createRefereeWithName(String name) {
        Referee referee = new Referee();
        referee.setName(name);
        referee.setHasCertificate(new Random().nextBoolean());
        return refereeService.add(referee);
    }

    public Player createPlayerWithName(String name, Team team) {
        Player player = new Player();
        player.setName(name);

        FutsalPositionEnum[] vals = FutsalPositionEnum.values();
        int index = ThreadLocalRandom.current().nextInt(vals.length);
        player.setPreferredPosition(vals[index]);

        Player saved = playerService.add(player);
        teamService.addPlayerToTeam(saved, team);
        return saved;
    }

    public Team createTeamWithName(String name) {
        Team team = new Team();
        team.setName(name);
        return teamService.add(team);
    }
}
