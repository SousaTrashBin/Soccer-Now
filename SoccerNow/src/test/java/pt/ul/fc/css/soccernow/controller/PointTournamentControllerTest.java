package pt.ul.fc.css.soccernow.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import pt.ul.fc.css.soccernow.domain.dto.games.CardInfoDTO;
import pt.ul.fc.css.soccernow.domain.dto.games.GameDTO;
import pt.ul.fc.css.soccernow.domain.dto.games.GamePlayerDTO;
import pt.ul.fc.css.soccernow.domain.dto.games.PlayerGameStatsDTO;
import pt.ul.fc.css.soccernow.domain.dto.tournament.PlacementInfoDTO;
import pt.ul.fc.css.soccernow.domain.dto.tournament.PointTournamentDTO;
import pt.ul.fc.css.soccernow.domain.dto.tournament.TeamPointsDTO;
import pt.ul.fc.css.soccernow.domain.dto.user.PlayerDTO;
import pt.ul.fc.css.soccernow.domain.dto.user.PlayerInfoDTO;
import pt.ul.fc.css.soccernow.domain.dto.user.RefereeDTO;
import pt.ul.fc.css.soccernow.domain.dto.user.RefereeInfoDTO;
import pt.ul.fc.css.soccernow.domain.entities.Address;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.game.Game;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.domain.entities.user.Referee;
import pt.ul.fc.css.soccernow.mapper.*;
import pt.ul.fc.css.soccernow.util.CardEnum;
import pt.ul.fc.css.soccernow.util.PlacementEnum;
import pt.ul.fc.css.soccernow.util.TournamentStatusEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pt.ul.fc.css.soccernow.utils.GameTeamDataUtil.createAddress;
import static pt.ul.fc.css.soccernow.utils.GameTeamDataUtil.createGameTeam;
import static pt.ul.fc.css.soccernow.utils.PlayerTestDataUtil.getPlayers;
import static pt.ul.fc.css.soccernow.utils.RefereeTestDataUtil.getCertificatedReferees;
import static pt.ul.fc.css.soccernow.utils.RefereeTestDataUtil.getUncertificatedReferees;
import static pt.ul.fc.css.soccernow.utils.TeamTestDataUtil.getTeams;
import static pt.ul.fc.css.soccernow.utils.UserTestDataUtil.SEED;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PointTournamentControllerTest {
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
    private TournamentMapper tournamentMapper;

    @Autowired
    public PointTournamentControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.controllerUtil = new ControllerUtils(mockMvc, objectMapper);
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
    public void testIfSetupIsWorking() throws Exception {
    }

    @Test
    public void testIfValidTournamentCanBeCreated() throws Exception {
        createTournament();
    }

    @Test
    public void testIfValidTournamentCanBeUpdated() throws Exception {
        PointTournamentDTO tournament = createTournament();
        assert tournament.getId() != null;

        fillTournamentWithTenTeams(tournament);
        removeFirstTeam(tournament);

        PointTournamentDTO pointTournamentDTO = getUpdatedTournament(tournament);
        assert pointTournamentDTO.getTeamPoints().size() == 9;

        UUID teamId = pointTournamentDTO.getTeamPoints().get(0).getTeam().getId();
        verifyThatTeamIsInTournament(teamId, tournament);
        pointTournamentDTO = closeRegistrationOfTournament(tournament);
        assert pointTournamentDTO.getStatus() == TournamentStatusEnum.IN_PROGRESS;

        createAndCloseTournamentGame(pointTournamentDTO);
        pointTournamentDTO = getUpdatedTournament(tournament);
        TeamPointsDTO firstPlaceTeamPoints = pointTournamentDTO.getTeamPoints().get(0);

        endTournament(tournament);

        verifyThatFirstTeamActuallyGotFirstPlace(firstPlaceTeamPoints);
    }

    private void endTournament(PointTournamentDTO tournament) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/point-tournaments/" + tournament.getId() + "/end")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private void verifyThatFirstTeamActuallyGotFirstPlace(TeamPointsDTO firstPlaceTeamPoints) throws Exception {
        String teamJson = mockMvc.perform(MockMvcRequestBuilders.get("/api/teams/" + firstPlaceTeamPoints.getTeam().getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        TeamDTO teamDTO = objectMapper.readValue(teamJson, TeamDTO.class);

        PlacementInfoDTO placementInfoDTO = teamDTO.getPlacements().stream().findFirst().get();
        assert placementInfoDTO.getValue().equals(PlacementEnum.FIRST);
    }

    private PointTournamentDTO getUpdatedTournament(PointTournamentDTO tournament) throws Exception {
        String updatedJson;
        PointTournamentDTO pointTournamentDTO;
        updatedJson = mockMvc.perform(MockMvcRequestBuilders.get("/api/point-tournaments/" + tournament.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        pointTournamentDTO = objectMapper.readValue(updatedJson, PointTournamentDTO.class);
        return pointTournamentDTO;
    }

    private void createAndCloseTournamentGame(PointTournamentDTO pointTournamentDTO) throws Exception {
        Game game = new Game();
        game.setPrimaryReferee(certificatedReferees.get(0));
        game.setGameTeamOne(createGameTeam(teams.get(1)));
        game.setGameTeamTwo(createGameTeam(teams.get(3)));
        game.setHappensIn(LocalDateTime.now().plusYears(1));

        Address fakeAddress = createAddress();
        game.setLocatedIn(fakeAddress);
        GameDTO gameDTO = gameMapper.toDTO(game);
        String gameDTOJSON = objectMapper.writeValueAsString(gameDTO);

        String jsonResponse = mockMvc.perform(MockMvcRequestBuilders.post("/api/games/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameDTOJSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        GameDTO jsonResponseGameDTO = parseDTO(jsonResponse, GameDTO.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/point-tournaments/" + pointTournamentDTO.getId() + "/games/" + jsonResponseGameDTO.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<UUID> teamOnePlayerUUIDs = jsonResponseGameDTO.getGameTeamOne().getGamePlayers().stream()
                .map(GamePlayerDTO::getPlayer)
                .map(PlayerInfoDTO::getId)
                .sorted()
                .toList();

        List<UUID> teamTwoPlayerUUIDs = jsonResponseGameDTO.getGameTeamTwo().getGamePlayers().stream()
                .map(GamePlayerDTO::getPlayer)
                .map(PlayerInfoDTO::getId)
                .sorted()
                .toList();

        Set<PlayerGameStatsDTO> playerGameStatsDTOSet = Set.of(
                new PlayerGameStatsDTO(
                        2,
                        new PlayerInfoDTO(teamOnePlayerUUIDs.get(0)),
                        Set.of(new CardInfoDTO(CardEnum.YELLOW, new RefereeInfoDTO(certificatedReferees.get(0).getId())))
                ),
                new PlayerGameStatsDTO(
                        4,
                        new PlayerInfoDTO(teamOnePlayerUUIDs.get(1)),
                        Set.of(new CardInfoDTO(CardEnum.RED, new RefereeInfoDTO(certificatedReferees.get(0).getId())))
                ),
                new PlayerGameStatsDTO(
                        7,
                        new PlayerInfoDTO(teamTwoPlayerUUIDs.get(0)),
                        Set.of(new CardInfoDTO(CardEnum.RED, new RefereeInfoDTO(certificatedReferees.get(0).getId())))
                ),
                new PlayerGameStatsDTO(
                        1,
                        new PlayerInfoDTO(teamTwoPlayerUUIDs.get(1)),
                        Set.of(new CardInfoDTO(CardEnum.YELLOW, new RefereeInfoDTO(certificatedReferees.get(0).getId())))
                )
        );


        mockMvc.perform(MockMvcRequestBuilders.post("/api/games/" + jsonResponseGameDTO.getId() + "/close")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerGameStatsDTOSet)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    private PointTournamentDTO closeRegistrationOfTournament(PointTournamentDTO tournament) throws Exception {
        String JSON = mockMvc.perform(MockMvcRequestBuilders.patch("/api/point-tournaments/" + tournament.getId() + "/close-registrations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(JSON, PointTournamentDTO.class);
    }

    private void verifyThatTeamIsInTournament(UUID teamId, PointTournamentDTO tournament) throws Exception {
        String teamJson = mockMvc.perform(MockMvcRequestBuilders.get("/api/teams/" + teamId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        TeamDTO teamDTO = objectMapper.readValue(teamJson, TeamDTO.class);
        assert teamDTO.getPlacements().size() == 1;
        PlacementInfoDTO placementInfoDTO = teamDTO.getPlacements().stream().findFirst().get();
        assert placementInfoDTO.getTournament().getId().equals(tournament.getId())
                && placementInfoDTO.getValue() == PlacementEnum.PENDING;
        System.out.println(teamDTO.getPlacements());
    }

    private void removeFirstTeam(PointTournamentDTO tournament) throws Exception {
        Team team = teams.get(0);
        UUID id = team.getId();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/point-tournaments/" + tournament.getId() + "/teams/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    private void fillTournamentWithTenTeams(PointTournamentDTO tournament) throws Exception {
        for (int i = 0; i < 10; i++) {
            Team team = teams.get(i);
            UUID id = team.getId();
            mockMvc.perform(MockMvcRequestBuilders.post("/api/point-tournaments/" + tournament.getId() + "/teams/" + id))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
        }
    }

    private PointTournamentDTO createTournament() throws Exception {
        String name = "Champions League";
        PointTournamentController.CreatePointTournamentDTO createPointTournamentDTO = new PointTournamentController.CreatePointTournamentDTO(name);
        String createDTOJson = objectMapper.writeValueAsString(createPointTournamentDTO);
        String jsonResponse = mockMvc.perform(MockMvcRequestBuilders.post("/api/point-tournaments/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createDTOJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        assert !jsonResponse.isBlank();
        return objectMapper.readValue(jsonResponse, PointTournamentDTO.class);
    }

    private <T> T parseDTO(String json, Class<T> dtoClass) {
        try {
            return objectMapper.readValue(json, dtoClass);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
