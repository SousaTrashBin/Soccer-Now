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

import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pt.ul.fc.css.soccernow.utils.PlayerTestDataUtil.getPlayers;
import static pt.ul.fc.css.soccernow.utils.TeamTestDataUtil.getTeams;
import static pt.ul.fc.css.soccernow.utils.UserTestDataUtil.SEED;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TeamControllerIntegrationTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final ControllerUtils controllerUtil;
    private List<Player> players;
    private List<Team> teams;
    @Autowired private TeamMapper teamMapper;
    @Autowired private PlayerMapper playerMapper;
    Random random = new Random(SEED);

    @Autowired
    public TeamControllerIntegrationTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        controllerUtil = new ControllerUtils(mockMvc, objectMapper);
    }

    @BeforeEach
    public void setUp() throws Exception {
        players = getPlayers();
        players = controllerUtil.createObjectFromDTOtoURL("/api/players/", players, (p -> playerMapper.toDTO(p)), (p -> playerMapper.toEntity(p)), PlayerDTO.class);

        teams = getTeams();
        teams = controllerUtil.createObjectFromDTOtoURL("/api/teams/", teams, (t -> teamMapper.toDTO(t)), (t -> teamMapper.toEntity(t)), TeamDTO.class);

        controllerUtil.initializeTeams(teams, players);
    }

    @Test
    public void testIfSetupIsWorking() {
    }

    @Test
    public void testGetTeamById() throws Exception {
        Team team = teams.get(0);
        mockMvc.perform(get("/api/teams/" + team.getId()))
                .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(team.getId().toString())).andReturn().getResponse().getContentAsString();
    }

    @Test
    public void testGetAllPlayersFromTeam() throws Exception {
        Team team = teams.get(0);

        mockMvc.perform(get("/api/teams/" + team.getId() + "/players"))
                .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.length()").value(10));
    }

    @Test
    public void testDeletePlayerFromTeam() throws Exception {
        Team team = teams.get(0);

        String playersString = mockMvc.perform(get("/api/teams/" + team.getId() + "/players"))
                                      .andExpect(status().isOk())
                                      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                      .andReturn().getResponse().getContentAsString();

        PlayerDTO playerDTO = objectMapper.readValue(playersString, PlayerDTO[].class)[0];
        Player player = playerMapper.toEntity(playerDTO);

        mockMvc.perform(delete("/api/teams/" + team.getId() + "/players/" + player.getId()))
                .andDo(print())
               .andExpect(status().isOk());

        mockMvc.perform(get("/api/teams/" + team.getId() + "/players"))
                .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.length()").value(9));
    }

    @Test
    public void testGetAllTeams() throws Exception {
        mockMvc.perform(get("/api/teams/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(teams.size()));
    }

    @Test
    public void testDeleteTeamById() throws Exception {
        Team team = teams.get(0);

        mockMvc.perform(delete("/api/teams/" + team.getId()))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/teams/"))
                .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.length()").value(teams.size() - 1));
    }
}
