package pt.ul.fc.css.soccernow.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.game.Game;
import pt.ul.fc.css.soccernow.domain.entities.game.GameTeam;
import pt.ul.fc.css.soccernow.domain.entities.game.PlayerGameStats;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.domain.entities.user.Referee;
import pt.ul.fc.css.soccernow.util.CardEnum;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static pt.ul.fc.css.soccernow.utils.GameTeamDataUtil.createAddress;
import static pt.ul.fc.css.soccernow.utils.GameTeamDataUtil.createGameTeam;
import static pt.ul.fc.css.soccernow.utils.PlayerTestDataUtil.getPlayers;
import static pt.ul.fc.css.soccernow.utils.RefereeTestDataUtil.getCertificatedReferees;
import static pt.ul.fc.css.soccernow.utils.RefereeTestDataUtil.getUncertificatedReferees;
import static pt.ul.fc.css.soccernow.utils.TeamTestDataUtil.createTeams;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GameServiceIntegrationTest {
    List<Player> players;
    List<Referee> certificatedReferees;
    List<Referee> uncertificatedReferees;
    List<Team> teams;

    @Autowired
    private GameService underTest;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private RefereeService refereeService;
    @Autowired
    private TeamService teamService;

    @BeforeEach
    public void setUp() {
        players = getPlayers();
        players = players.stream().map(playerService::add).toList();

        uncertificatedReferees = getUncertificatedReferees();
        uncertificatedReferees = uncertificatedReferees.stream().map(refereeService::add).toList();

        certificatedReferees = getCertificatedReferees();
        certificatedReferees = certificatedReferees.stream().map(refereeService::add).toList();

        teams = createTeams(new HashSet<>(players));
        teams = teams.stream().map(teamService::add).toList();
    }

    @Test
    @Transactional
    public void testIfValidGameCanBeAdded() {
        Game game = new Game();

        GameTeam gameTeamOne = createGameTeam(teams.get(0));
        game.setGameTeamOne(gameTeamOne);

        GameTeam gameTeamTwo = createGameTeam(teams.get(1));
        game.setGameTeamTwo(gameTeamTwo);

        game.setPrimaryReferee(uncertificatedReferees.get(7));
        game.setSecondaryReferees(new HashSet<>(certificatedReferees.subList(0, 4)));
        game.setLocatedIn(createAddress());
        game.setHappensIn(LocalDateTime.now().plusYears(10));
        Game savedGame = underTest.add(game);
        System.out.println(savedGame.getGameTeamTwo());
        System.out.println(savedGame.getGameTeamOne().getGamePlayers().stream().findAny().get());
        System.out.printf("Saved game %s\n", savedGame.getId());

        Set<PlayerGameStats> playerStats = new HashSet<>();

        Player teamOnePlayer = game.getGameTeamOne().getPlayers().stream().findFirst().get();
        PlayerGameStats teamOneStats = new PlayerGameStats();
        teamOneStats.setPlayer(teamOnePlayer);
        teamOneStats.setGivenCard(CardEnum.YELLOW);
        teamOneStats.setScoredGoals(10);
        playerStats.add(teamOneStats);

        Player teamTwoPlayer = game.getGameTeamTwo().getPlayers().stream().findFirst().get();
        PlayerGameStats playerTwoStats = new PlayerGameStats();
        playerTwoStats.setPlayer(teamTwoPlayer);
        playerTwoStats.setGivenCard(CardEnum.RED);
        playerTwoStats.setScoredGoals(15);
        playerStats.add(playerTwoStats);

        Game closedGame = underTest.closeGame(savedGame.getId(), playerStats);

        assertNotNull(closedGame.getGameStats());
        System.out.println(closedGame.getGameStats().getPlayerGameStats());
        assertEquals(Integer.valueOf(25), closedGame.getGameStats().getTeamOneGoals() + closedGame.getGameStats().getTeamTwoGoals());
        System.out.println(playerService.findById(game.getPlayers().stream().skip(0).findFirst().get().getId()).getPlayerGameStats());
    }
}
