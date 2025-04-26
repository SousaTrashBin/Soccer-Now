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
import pt.ul.fc.css.soccernow.exception.ResourceCouldNotBeDeletedException;
import pt.ul.fc.css.soccernow.util.CardEnum;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static pt.ul.fc.css.soccernow.utils.GameTeamDataUtil.createAddress;
import static pt.ul.fc.css.soccernow.utils.GameTeamDataUtil.createGameTeam;
import static pt.ul.fc.css.soccernow.utils.PlayerTestDataUtil.getPlayers;
import static pt.ul.fc.css.soccernow.utils.RefereeTestDataUtil.getCertificatedReferees;
import static pt.ul.fc.css.soccernow.utils.RefereeTestDataUtil.getUncertificatedReferees;

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

    }

    @Test
    @Transactional
    public void testIfValidGameCanBeAdded() {
        Game game = new Game();

        GameTeam gameTeamOne = createGameTeam(teams.get(0));
        game.setGameTeamOne(gameTeamOne);

        GameTeam gameTeamTwo = createGameTeam(teams.get(1));
        game.setGameTeamTwo(gameTeamTwo);

        Referee primaryReferee = uncertificatedReferees.get(7);
        game.setPrimaryReferee(primaryReferee);
        game.setSecondaryReferees(new HashSet<>(certificatedReferees.subList(0, 4)));
        game.setLocatedIn(createAddress());
        game.setHappensIn(LocalDateTime.now().plusYears(10));
        Game savedGame = underTest.add(game);

        Set<PlayerGameStats> playerStats = new HashSet<>();

        Player teamOnePlayer = game.getGameTeamOne().getPlayers().stream().findFirst().get();
        PlayerGameStats teamOneStats = new PlayerGameStats();
        teamOneStats.setPlayer(teamOnePlayer);
        teamOneStats.setGivenCard(CardEnum.YELLOW);
        teamOneStats.setScoredGoals(10);
        playerStats.add(teamOneStats);

        assertThrows(ResourceCouldNotBeDeletedException.class, () -> teamService.softDelete(savedGame.getGameTeamOne().getTeam().getId()));
        assertThrows(ResourceCouldNotBeDeletedException.class, () -> refereeService.softDelete(primaryReferee.getId()));
        assertThrows(ResourceCouldNotBeDeletedException.class, () -> playerService.softDelete(teamOnePlayer.getId()));

        Player teamTwoPlayer = game.getGameTeamTwo().getPlayers().stream().findFirst().get();
        PlayerGameStats playerTwoStats = new PlayerGameStats();
        playerTwoStats.setPlayer(teamTwoPlayer);
        playerTwoStats.setGivenCard(CardEnum.RED);
        playerTwoStats.setScoredGoals(15);
        playerStats.add(playerTwoStats);

        Game closedGame = underTest.closeGame(savedGame.getId(), playerStats);

        assertNotNull(closedGame.getGameStats());
        assertEquals(Integer.valueOf(25), closedGame.getGameStats().getTeamOneGoals() + closedGame.getGameStats().getTeamTwoGoals());
    }
}
