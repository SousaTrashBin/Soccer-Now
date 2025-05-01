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
import pt.ul.fc.css.soccernow.util.FutsalPositionEnum;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PlayerControllerIntegrationTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public PlayerControllerIntegrationTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    void testIfValidPlayerCanBeRegistered() throws Exception {
        PlayerDTO playerDTO = new PlayerDTO()
                .setName("TEST")
                .setPreferredPosition(FutsalPositionEnum.GOALIE);
        String playerDTOJSON = objectMapper.writeValueAsString(playerDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/players/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerDTOJSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("TEST")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.preferredPosition").value("GOALIE")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").exists()
        );
    }
}
