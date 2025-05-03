package pt.ul.fc.css.soccernow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pt.ul.fc.css.soccernow.domain.entities.Team;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.domain.entities.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ControllerUtils {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public ControllerUtils(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    public void initializeTeams(List<Team> teams, List<Player> players, Random random) {
        for (Team team : teams) {
            random.ints(0, players.size())
                    .distinct()
                    .limit(10)
                    .mapToObj(players::get)
                    .map(User::getId)
                    .forEach(id -> {
                        try {
                            mockMvc.perform(MockMvcRequestBuilders.post("/api/teams/" + team.getId() + "/players/" + id));
                        } catch (Exception ignored) {
                        }
                    });
        }
    }

    public <T, X> List<T> createObjectFromDTOtoURL(String url, List<T> entityList, Function<T, X> toDTO, Function<X, T> toEntity, Class<X> DTOClass) throws Exception {
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

    public <X, T> List<T> getUpdatedEntities(String url, Function<X, T> toEntity, Class<X> DTOClass) throws Exception {
        String jsonResponse = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        ).andReturn().getResponse().getContentAsString();

        List<X> entityListDTO = objectMapper.readValue(
                jsonResponse,
                objectMapper.getTypeFactory().constructCollectionType(List.class, DTOClass)
        );
        return entityListDTO.stream().map(toEntity).collect(Collectors.toList());
    }
}
