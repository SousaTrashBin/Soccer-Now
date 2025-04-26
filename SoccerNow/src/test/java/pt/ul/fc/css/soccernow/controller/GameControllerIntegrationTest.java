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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pt.ul.fc.css.soccernow.domain.dto.TeamDTO;
import pt.ul.fc.css.soccernow.domain.dto.user.PlayerDTO;
import pt.ul.fc.css.soccernow.domain.dto.user.RefereeDTO;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.domain.entities.user.Referee;
import pt.ul.fc.css.soccernow.domain.entities.user.User;
import pt.ul.fc.css.soccernow.mapper.PlayerMapper;
import pt.ul.fc.css.soccernow.mapper.RefereeMapper;
import pt.ul.fc.css.soccernow.mapper.TeamMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static pt.ul.fc.css.soccernow.utils.PlayerTestDataUtil.getPlayers;
import static pt.ul.fc.css.soccernow.utils.RefereeTestDataUtil.getCertificatedReferees;
import static pt.ul.fc.css.soccernow.utils.RefereeTestDataUtil.getUncertificatedReferees;
import static pt.ul.fc.css.soccernow.utils.TeamTestDataUtil.getTeams;
import static pt.ul.fc.css.soccernow.utils.UserTestDataUtil.RANDOM;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class GameControllerIntegrationTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
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
    public GameControllerIntegrationTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @BeforeEach
    public void setUp() throws Exception {
        players = getPlayers();
        players = createObjectFromDTOtoURL("/api/players/", players, (p -> playerMapper.toDTO(p)), (p -> playerMapper.toEntity(p)), PlayerDTO.class);

        uncertificatedReferees = getUncertificatedReferees();
        uncertificatedReferees = createObjectFromDTOtoURL("/api/referees/", uncertificatedReferees, (r -> refereeMapper.toDTO(r)), (r -> refereeMapper.toEntity(r)), RefereeDTO.class);

        certificatedReferees = getCertificatedReferees();
        certificatedReferees = createObjectFromDTOtoURL("/api/referees/", certificatedReferees, (r -> refereeMapper.toDTO(r)), (r -> refereeMapper.toEntity(r)), RefereeDTO.class);

        teams = getTeams();
        teams = createObjectFromDTOtoURL("/api/teams/", teams, (t -> teamMapper.toDTO(t)), (t -> teamMapper.toEntity(t)), TeamDTO.class);

        initializeTeams();
    }

    private void initializeTeams() {
        for (Team team : teams) {
            RANDOM.ints(0, players.size())
                    .distinct()
                    .limit(10)
                    .mapToObj(i -> players.get(i))
                    .map(User::getId)
                    .forEach(id -> {
                        try {
                            mockMvc.perform(MockMvcRequestBuilders.post("/api/teams/" + team.getId() + "/players/" + id));
                        } catch (Exception ignored) {
                        }
                    });
        }
    }

    private <T, X> List<T> createObjectFromDTOtoURL(String url, List<T> entityList, Function<T, X> toDTO, Function<X, T> toEntity, Class<X> DTOClass) throws Exception {
        ArrayList<T> newEntityList = new ArrayList<>();
        for (T entity : entityList) {
            String objectJson = objectMapper.writeValueAsString(toDTO.apply(entity));
            String jsonResponse = mockMvc.perform(
                    MockMvcRequestBuilders.post(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectJson)
            ).andReturn().getResponse().getContentAsString();
            X entityDTO = objectMapper.readValue(jsonResponse, DTOClass);
            newEntityList.add(toEntity.apply(entityDTO));
        }
        return newEntityList;
    }

    @Test
    public void testIfSetupIsWorking() throws Exception {
        String teamsResponse = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/teams/")
        ).andReturn().getResponse().getContentAsString();
        System.out.println(teamsResponse);

        String playersResponse = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/players/")
        ).andReturn().getResponse().getContentAsString();
        System.out.println(playersResponse);

        String refereeResponse = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/referees/")
        ).andReturn().getResponse().getContentAsString();
        System.out.println(refereeResponse);
    }

}
