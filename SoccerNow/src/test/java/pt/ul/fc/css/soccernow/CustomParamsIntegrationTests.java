package pt.ul.fc.css.soccernow;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pt.ul.fc.css.soccernow.controller.ControllerUtils;
import pt.ul.fc.css.soccernow.controller.PlayerController;
import pt.ul.fc.css.soccernow.domain.dto.TeamDTO;
import pt.ul.fc.css.soccernow.domain.dto.user.PlayerDTO;
import pt.ul.fc.css.soccernow.domain.dto.user.RefereeDTO;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.domain.entities.user.Referee;
import pt.ul.fc.css.soccernow.mapper.GameMapper;
import pt.ul.fc.css.soccernow.mapper.PlayerMapper;
import pt.ul.fc.css.soccernow.mapper.RefereeMapper;
import pt.ul.fc.css.soccernow.mapper.TeamMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pt.ul.fc.css.soccernow.utils.PlayerTestDataUtil.getPlayers;
import static pt.ul.fc.css.soccernow.utils.RefereeTestDataUtil.getCertificatedReferees;
import static pt.ul.fc.css.soccernow.utils.RefereeTestDataUtil.getUncertificatedReferees;
import static pt.ul.fc.css.soccernow.utils.TeamTestDataUtil.getTeams;
import static pt.ul.fc.css.soccernow.utils.UserTestDataUtil.SEED;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CustomParamsIntegrationTests {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final ControllerUtils controllerUtil;
    List<Player> players;
    List<Referee> certificatedReferees;
    List<Referee> uncertificatedReferees;
    List<Team> teams;
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private PlayerMapper playerMapper;
    @Autowired
    private RefereeMapper refereeMapper;
    @Autowired
    private GameMapper gameMapper;

    @Autowired
    public CustomParamsIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        controllerUtil = new ControllerUtils(mockMvc, objectMapper);
    }

    @BeforeEach
    public void setUp() throws Exception {
        Random random = new Random(SEED);
        players = getPlayers(random);
        players = controllerUtil.createObjectFromDTOtoURL("/api/players/", players, (p -> playerMapper.toDTO(p)), (p -> playerMapper.toEntity(p)), PlayerDTO.class);

        uncertificatedReferees = getUncertificatedReferees(random);
        uncertificatedReferees = controllerUtil.createObjectFromDTOtoURL("/api/referees/", uncertificatedReferees, (r -> refereeMapper.toDTO(r)), (r -> refereeMapper.toEntity(r)), RefereeDTO.class);

        certificatedReferees = getCertificatedReferees(random);
        certificatedReferees = controllerUtil.createObjectFromDTOtoURL("/api/referees/", certificatedReferees, (r -> refereeMapper.toDTO(r)), (r -> refereeMapper.toEntity(r)), RefereeDTO.class);

        teams = getTeams(random);
        teams = controllerUtil.createObjectFromDTOtoURL("/api/teams/", teams, (t -> teamMapper.toDTO(t)), (t -> teamMapper.toEntity(t)), TeamDTO.class);

        controllerUtil.initializeTeams(teams, players, random);
        teams = controllerUtil.getUpdatedEntities("/api/teams/", (x -> teamMapper.toEntity(x)), TeamDTO.class);
        players = controllerUtil.getUpdatedEntities("/api/players/", (x -> playerMapper.toEntity(x)), PlayerDTO.class);
    }

    @Test
    public void testFetchTeamsWithLessThanFivePlayers() throws Exception {
        List<Team> teams = fetchTeamsWithQueryString("?maxPlayers=11&?minPlayers=9");
        System.out.println(teams.size());
        System.out.println("teams.get(0).getPlayers().size() = " + teams.get(0).getPlayers().size());
        assert teams.size() == 20;
    }

    @Test
    public void testFetchAverageGoalsFromCertainPlayers() throws Exception {
        List<Player> players = fetchPlayersWithQueryString("?name=Luka");

        for (Player player : players) {
            assert fetchAverageGoalsFromPlayer(player).stream()
                    .allMatch(averageGoalsResponse -> averageGoalsResponse.goals() != null
                            && Float.compare(averageGoalsResponse.goals(), 0f) >= 0
                    );
        }
    }

    @Test
    public void testFetchTeamsWithMostCards() throws Exception {
        List<Team> teams = fetchTeamsWithQueryString("?sortBy=playerCards");
        assert teams.size() == this.teams.size();
    }

    @Test
    public void testFetchTeamsWithMostVictories() throws Exception {
        List<Team> teams = fetchTeamsWithQueryString("?sortBy=victories");
        assert teams.size() == this.teams.size();
    }

    @Test
    public void testFetchRefereesWithMostGames() throws Exception {
        List<Referee> referees = fetchRefereesWithQueryString("?order=desc");
        assert referees.size() == this.uncertificatedReferees.size() + this.certificatedReferees.size();

    }

    @Test
    public void testFetchPlayersWithMostRedCards() throws Exception {
        List<Player> players = fetchPlayersWithQueryString("?order=desc");
        assert players.size() == this.players.size();
    }

    private List<Team> fetchTeamsWithQueryString(String queryString) throws Exception {
        String resultJson = mockMvc.perform(get("/api/teams/" + queryString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        return Arrays.stream(objectMapper.readValue(resultJson, TeamDTO[].class))
                .map(teamMapper::toEntity)
                .toList();
    }

    private List<Player> fetchPlayersWithQueryString(String queryString) throws Exception {
        String resultJson = mockMvc.perform(get("/api/players/" + queryString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        return Arrays.stream(objectMapper.readValue(resultJson, PlayerDTO[].class))
                .map(playerMapper::toEntity)
                .toList();
    }

    private List<Referee> fetchRefereesWithQueryString(String queryString) throws Exception {
        String resultJson = mockMvc.perform(get("/api/referees/" + queryString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        return Arrays.stream(objectMapper.readValue(resultJson, RefereeDTO[].class))
                .map(refereeMapper::toEntity)
                .toList();
    }

    private List<PlayerController.AverageGoalsResponse> fetchAverageGoalsFromPlayer(Player player) throws Exception {
        String resultJson = mockMvc.perform(get("/api/players/average-goals").param("playerName", player.getName()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<PlayerController.AverageGoalsResponse> responses = objectMapper.readValue(
                resultJson,
                new TypeReference<>() {
                }
        );
        return responses;
    }
}
