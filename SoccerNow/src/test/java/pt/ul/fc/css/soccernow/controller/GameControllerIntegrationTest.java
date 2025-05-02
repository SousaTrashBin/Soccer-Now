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
import pt.ul.fc.css.soccernow.domain.dto.games.GameDTO;
import pt.ul.fc.css.soccernow.domain.dto.games.PlayerGameStatsDTO;
import pt.ul.fc.css.soccernow.domain.dto.user.PlayerDTO;
import pt.ul.fc.css.soccernow.domain.dto.user.RefereeDTO;
import pt.ul.fc.css.soccernow.domain.entities.Address;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.game.Game;
import pt.ul.fc.css.soccernow.domain.entities.game.GameTeam;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.domain.entities.user.Referee;
import pt.ul.fc.css.soccernow.mapper.GameMapper;
import pt.ul.fc.css.soccernow.mapper.PlayerMapper;
import pt.ul.fc.css.soccernow.mapper.RefereeMapper;
import pt.ul.fc.css.soccernow.mapper.TeamMapper;
import pt.ul.fc.css.soccernow.util.CardEnum;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private final ControllerUtils controllerUtil;
    Random random = new Random(SEED);
    @Autowired
    private GameMapper gameMapper;

    @Autowired
    public GameControllerIntegrationTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        controllerUtil = new ControllerUtils(mockMvc, objectMapper);
    }

    @BeforeEach
    public void setUp() throws Exception {
        players = getPlayers();
        players = controllerUtil.createObjectFromDTOtoURL("/api/players/", players, (p -> playerMapper.toDTO(p)), (p -> playerMapper.toEntity(p)), PlayerDTO.class);

        uncertificatedReferees = getUncertificatedReferees();
        uncertificatedReferees = controllerUtil.createObjectFromDTOtoURL("/api/referees/", uncertificatedReferees, (r -> refereeMapper.toDTO(r)), (r -> refereeMapper.toEntity(r)), RefereeDTO.class);

        certificatedReferees = getCertificatedReferees();
        certificatedReferees = controllerUtil.createObjectFromDTOtoURL("/api/referees/", certificatedReferees, (r -> refereeMapper.toDTO(r)), (r -> refereeMapper.toEntity(r)), RefereeDTO.class);

        teams = getTeams();
        teams = controllerUtil.createObjectFromDTOtoURL("/api/teams/", teams, (t -> teamMapper.toDTO(t)), (t -> teamMapper.toEntity(t)), TeamDTO.class);

        controllerUtil.initializeTeams(teams, players);
        teams = controllerUtil.getUpdatedEntities("/api/teams/", (x -> teamMapper.toEntity(x)), TeamDTO.class);
        players = controllerUtil.getUpdatedEntities("/api/players/", (x -> playerMapper.toEntity(x)), PlayerDTO.class);
    }


    @Test
    public void testIfSetupIsWorking() throws Exception {
        System.out.println(mockMvc.perform(MockMvcRequestBuilders.get("/api/games/")).andReturn().getResponse().getContentAsString());
    }

    @Test
    public void testIfValidGameCanBeCreated() throws Exception {
        Game game = new Game();
        GameTeam firstGameTeam = createGameTeam(teams.get(0));
        GameTeam secondGameTeam = createGameTeam(teams.get(9));
        game.setPrimaryReferee(certificatedReferees.get(0));
        game.setGameTeamOne(firstGameTeam);
        game.setGameTeamTwo(secondGameTeam);
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
        Set<UUID> gamePlayerIDs = new HashSet<>();
        List.of(jsonResponseGameDTO.getGameTeamOne(), jsonResponseGameDTO.getGameTeamTwo())
                .forEach(gameTeam -> gamePlayerIDs.addAll(gameTeam.getGamePlayers().stream()
                        .map(gamePlayerInfoDTO -> gamePlayerInfoDTO.getPlayer().getId())
                        .collect(Collectors.toSet()))
                );

        List<PlayerDTO> savedPlayerDTOs = gamePlayerIDs.stream()
                .map(id -> fetchJson("/api/players/", id))
                .filter(Objects::nonNull)
                .map(response -> parseDTO(response, PlayerDTO.class))
                .filter(Objects::nonNull)
                .filter(this::hasGames)
                .peek(System.out::println)
                .toList();
        assert savedPlayerDTOs.size() == 10;
        assert !savedPlayerDTOs.stream().map(PlayerDTO::getId).map(id -> deleteEntity("/api/players/", id)).reduce(Boolean::logicalOr).orElse(false);

        List<TeamDTO> savedTeamDTOs = Stream.of(gameDTO.getGameTeamOne(), gameDTO.getGameTeamTwo())
                .map(gameTeamInfoDTO -> gameTeamInfoDTO.getTeam().getId())
                .map(id -> fetchJson("/api/teams/", id))
                .filter(Objects::nonNull)
                .map(response -> parseDTO(response, TeamDTO.class))
                .filter(Objects::nonNull)
                .filter(teamDTO -> teamDTO.getGames().stream().findFirst().isPresent())
                .peek(System.out::println)
                .toList();

        assert savedTeamDTOs.size() == 2;
        assert !Stream.of(gameDTO.getGameTeamOne(), gameDTO.getGameTeamTwo()).map(gameDTOTest -> gameDTOTest.getTeam().getId()).map(id -> deleteEntity("/api/teams/", id)).reduce(Boolean::logicalOr).orElse(false);

    }

    @Test
    public void testIfGameCanBeClosedWithStats() throws Exception {
        Game game = new Game();
        GameTeam firstGameTeam = createGameTeam(teams.get(0));
        GameTeam secondGameTeam = createGameTeam(teams.get(6));
        game.setPrimaryReferee(certificatedReferees.get(0));
        game.setGameTeamOne(firstGameTeam);
        game.setGameTeamTwo(secondGameTeam);
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

        List<UUID> teamOnePlayerUUIDs = jsonResponseGameDTO.getGameTeamOne().getGamePlayers().stream()
                .map(GameDTO.GameTeamInfoDTO.GamePlayerInfoDTO::getPlayer)
                .map(GameDTO.GameTeamInfoDTO.GamePlayerInfoDTO.PlayerInfoDTO::getId)
                .sorted()
                .toList();

        List<UUID> teamTwoPlayerUUIDs = jsonResponseGameDTO.getGameTeamTwo().getGamePlayers().stream()
                .map(GameDTO.GameTeamInfoDTO.GamePlayerInfoDTO::getPlayer)
                .map(GameDTO.GameTeamInfoDTO.GamePlayerInfoDTO.PlayerInfoDTO::getId)
                .sorted()
                .toList();

        Set<PlayerGameStatsDTO> playerGameStatsDTOSet = Set.of(
                new PlayerGameStatsDTO(CardEnum.YELLOW, 2, new PlayerGameStatsDTO.PlayerInfoDTO(teamOnePlayerUUIDs.get(0))),
                new PlayerGameStatsDTO(CardEnum.RED, 4, new PlayerGameStatsDTO.PlayerInfoDTO(teamOnePlayerUUIDs.get(1))),
                new PlayerGameStatsDTO(CardEnum.RED, 7, new PlayerGameStatsDTO.PlayerInfoDTO(teamTwoPlayerUUIDs.get(0))),
                new PlayerGameStatsDTO(CardEnum.YELLOW, 1, new PlayerGameStatsDTO.PlayerInfoDTO(teamTwoPlayerUUIDs.get(1)))
        );


        mockMvc.perform(MockMvcRequestBuilders.post("/api/games/" + jsonResponseGameDTO.getId() + "/close")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerGameStatsDTOSet)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testIfGameCanBeClosedWithoutStats() throws Exception {
        Game game = new Game();
        GameTeam firstGameTeam = createGameTeam(teams.get(0));
        GameTeam secondGameTeam = createGameTeam(teams.get(5));
        game.setPrimaryReferee(certificatedReferees.get(0));
        game.setGameTeamOne(firstGameTeam);
        game.setGameTeamTwo(secondGameTeam);
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
        mockMvc.perform(MockMvcRequestBuilders.post("/api/games/" + jsonResponseGameDTO.getId() + "/close")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    private String fetchJson(String url, UUID id) {
        try {
            return mockMvc.perform(MockMvcRequestBuilders.get(url + id))
                    .andReturn()
                    .getResponse()
                    .getContentAsString();
        } catch (Exception e) {
            return null;
        }
    }

    private boolean deleteEntity(String url, UUID id) {
        try {
            return mockMvc.perform(MockMvcRequestBuilders.delete(url + id))
                    .andReturn()
                    .getResponse().getStatus() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    private <T> T parseDTO(String json, Class<T> dtoClass) {
        try {
            return objectMapper.readValue(json, dtoClass);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    private boolean hasGames(PlayerDTO player) {
        return player.getTeams().stream()
                .anyMatch(team -> !team.getGames().isEmpty());
    }
}
