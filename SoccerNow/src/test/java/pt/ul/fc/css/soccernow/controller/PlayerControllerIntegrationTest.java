package pt.ul.fc.css.soccernow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pt.ul.fc.css.soccernow.domain.dto.user.PlayerDTO;
import pt.ul.fc.css.soccernow.domain.entities.user.Player;
import pt.ul.fc.css.soccernow.mapper.PlayerMapper;
import pt.ul.fc.css.soccernow.util.FutsalPositionEnum;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pt.ul.fc.css.soccernow.utils.UserTestDataUtil.SEED;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PlayerControllerIntegrationTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final PlayerMapper playerMapper;
    Random random = new Random(SEED);

    @Autowired
    public PlayerControllerIntegrationTest(MockMvc mockMvc, ObjectMapper objectMapper, PlayerMapper playerMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.playerMapper = playerMapper;
    }

    @Test
    void testIfValidPlayerCanBeRegistered() throws Exception {
        Player player = new Player();
        player.setName("TEST");
        player.setPreferredPosition(FutsalPositionEnum.GOALIE);
        PlayerDTO playerDTO = playerMapper.toDTO(player);
        String playerDTOJSON = objectMapper.writeValueAsString(playerDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/players/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerDTOJSON)
        ).andDo(print()).andExpect(status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.jsonPath("$.name").value("TEST")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.preferredPosition").value("GOALIE")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").exists()
        );
    }

    @Test
    void testIfInValidPlayerCantBeRegistered() throws Exception {
        Player player = new Player();
        player.setName("TEST123");
        player.setPreferredPosition(FutsalPositionEnum.GOALIE);
        PlayerDTO playerDTO = playerMapper.toDTO(player);
        String playerDTOJSON = objectMapper.writeValueAsString(playerDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/players/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerDTOJSON)
        ).andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    void testIfValidPlayerCanBeRegisteredAndUpdated() throws Exception {
        Player player = new Player();
        player.setName("Sofia Reia");
        player.setPreferredPosition(FutsalPositionEnum.FORWARD);
        PlayerDTO playerDTO = playerMapper.toDTO(player);
        String playerDTOJSON = objectMapper.writeValueAsString(playerDTO);

        String jsonResponse = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/players/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerDTOJSON)
        ).andDo(print()).andExpect(status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Sofia Reia")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.preferredPosition").value("FORWARD")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").exists()
        ).andReturn().getResponse().getContentAsString();

        PlayerDTO savedDTO = objectMapper.readValue(jsonResponse, PlayerDTO.class);
        savedDTO.setName("Vania Mendonça");
        savedDTO.setPreferredPosition(FutsalPositionEnum.LEFT_WINGER);
        String updatedPlayerJSON = objectMapper.writeValueAsString(savedDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/players/" + savedDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedPlayerJSON)
        ).andDo(print()).andExpect(status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Vania Mendonça")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.preferredPosition").value("LEFT_WINGER")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").exists()
        );
    }

    @Test
    void testIfValidPlayerCanBeAddedAndRemoved() throws Exception {
        Player player = new Player();
        player.setName("Sofia Reia");
        player.setPreferredPosition(FutsalPositionEnum.FORWARD);
        PlayerDTO playerDTO = playerMapper.toDTO(player);
        String playerDTOJSON = objectMapper.writeValueAsString(playerDTO);

        String jsonResponse = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/players/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerDTOJSON)
        ).andDo(print()).andExpect(status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Sofia Reia")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.preferredPosition").value("FORWARD")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").exists()
        ).andReturn().getResponse().getContentAsString();
        PlayerDTO savedDTO = objectMapper.readValue(jsonResponse, PlayerDTO.class);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/players/" + savedDTO.getId())
        ).andDo(print()).andExpect(status().is2xxSuccessful());
        String response = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/players/")
        ).andReturn().getResponse().getContentAsString();

        List<?> players = objectMapper.readValue(response, List.class);
        assertTrue(players.isEmpty());
    }
}
