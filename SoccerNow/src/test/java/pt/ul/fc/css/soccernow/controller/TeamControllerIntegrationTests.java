package pt.ul.fc.css.soccernow.controller;

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
import pt.ul.fc.css.soccernow.domain.dto.TeamDTO;
import pt.ul.fc.css.soccernow.domain.dto.user.PlayerDTO;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.mapper.PlayerMapper;
import pt.ul.fc.css.soccernow.mapper.TeamMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pt.ul.fc.css.soccernow.utils.PlayerTestDataUtil.getPlayers;
import static pt.ul.fc.css.soccernow.utils.TeamTestDataUtil.getTeams;
import static pt.ul.fc.css.soccernow.utils.UserTestDataUtil.SEED;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TeamControllerIntegrationTests {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final ControllerUtils controllerUtil;
    private List<Player> players;
    private List<Team> teams;
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private PlayerMapper playerMapper;

    @Autowired
    public TeamControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        controllerUtil = new ControllerUtils(mockMvc, objectMapper);
    }

    @BeforeEach
    public void setUp() throws Exception {
        Random random = new Random(SEED);
        players = getPlayers(random);
        players = controllerUtil.createObjectFromDTOtoURL("/api/players/", players, (p -> playerMapper.toDTO(p)), (p -> playerMapper.toEntity(p)), PlayerDTO.class);

        teams = getTeams(random);
        teams = controllerUtil.createObjectFromDTOtoURL("/api/teams/", teams, (t -> teamMapper.toDTO(t)), (t -> teamMapper.toEntity(t)), TeamDTO.class);

        controllerUtil.initializeTeams(teams, players, random);

        teams = controllerUtil.getUpdatedEntities("/api/teams/", (t -> teamMapper.toEntity(t)), TeamDTO.class);
        players = controllerUtil.getUpdatedEntities("/api/players/", (p -> playerMapper.toEntity(p)), PlayerDTO.class);
    }

    @Test
    public void testIfSetupIsWorking() {
    }

    @Test
    public void testIfSetupPopulatedCorrectly() throws Exception {
        for (Team team : teams) {
            for (Player player : fetchPlayersFromTeam(team)) {
                assert player.getTeams().contains(team);
            }
        }
    }

    @Test
    public void testGetTeamById() throws Exception {
        Team team = teams.get(0);
        Team fetchedTeam = fetchTeam(team);
        assert fetchedTeam.equals(team);
    }

    @Test
    public void testGetAllPlayersFromTeam() throws Exception {
        Team team = teams.get(0);
        assert fetchPlayersFromTeam(team).size() == team.getPlayers().size();
    }

    @Test
    public void testDeletePlayerFromTeam() throws Exception {
        Team team = teams.get(0);
        Player player = fetchPlayersFromTeam(team).get(0);
        assert fetchTeamsFromPlayer(player).contains(team);
        deletePlayerFromTeam(team, player);
        assert !fetchPlayersFromTeam(team).contains(player);
        assert !fetchTeamsFromPlayer(player).contains(team);
    }

    @Test
    public void testGetAllTeams() throws Exception {
        assert fetchAllTeams().size() == teams.size();
    }

    @Test
    public void testDeleteTeamById() throws Exception {
        Team team = teams.get(0);
        List<Player> playersFromTeam = fetchPlayersFromTeam(team);
        deleteTeam(team);
        List<Team> fetchedTeams = fetchAllTeams();
        assert !fetchedTeams.contains(team);
        for (Player player : playersFromTeam)
            assert !fetchTeamsFromPlayer(player).contains(team);
    }

    private void deleteTeam(Team team) throws Exception {
        mockMvc.perform(delete("/api/teams/" + team.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private void deletePlayerFromTeam(Team team, Player player) throws Exception {
        mockMvc.perform(delete("/api/teams/" + team.getId() + "/players/" + player.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private List<Team> fetchTeamsFromPlayer(Player player) throws Exception {
        String playersJson = mockMvc.perform(get("/api/players/" + player.getId() + "/teams"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        return Arrays.stream(objectMapper.readValue(playersJson, TeamDTO[].class))
                .map(teamMapper::toEntity)
                .toList();
    }

    private List<Player> fetchPlayersFromTeam(Team team) throws Exception {
        String playersJson = mockMvc.perform(get("/api/teams/" + team.getId() + "/players"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        return Arrays.stream(objectMapper.readValue(playersJson, PlayerDTO[].class))
                .map(playerMapper::toEntity)
                .toList();
    }

    private Team fetchTeam(Team team) throws Exception {
        String playersJson = mockMvc.perform(get("/api/teams/" + team.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        return teamMapper.toEntity(objectMapper.readValue(playersJson, TeamDTO.class));
    }

    private List<Team> fetchAllTeams() throws Exception {
        String playersJson = mockMvc.perform(get("/api/teams/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        return Arrays.stream(objectMapper.readValue(playersJson, TeamDTO[].class))
                .map(teamMapper::toEntity)
                .toList();
    }
}
