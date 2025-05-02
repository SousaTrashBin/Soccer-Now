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
import pt.ul.fc.css.soccernow.domain.dto.user.RefereeDTO;
import pt.ul.fc.css.soccernow.domain.entities.user.Referee;
import pt.ul.fc.css.soccernow.mapper.RefereeMapper;

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
public class RefereeControllerIntegrationTests {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final RefereeMapper refereeMapper;
    Random random = new Random(SEED);

    @Autowired
    public RefereeControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, RefereeMapper refereeMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.refereeMapper = refereeMapper;
    }

    @Test
    void testIfValidRefereeCanBeRegistered() throws Exception {
        Referee referee = new Referee();
        referee.setName("Diogo Sousa");
        String refereeJSON = objectMapper.writeValueAsString(refereeMapper.toDTO(referee));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/referees/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(refereeJSON)
        ).andDo(print()).andExpect(status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Diogo Sousa")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.hasCertificate").value("false")
        );
    }

    @Test
    void testIfInValidRefereeCantBeRegistered() throws Exception {
        Referee referee = new Referee();
        referee.setName("Diogo Sousa123");
        String refereeJSON = objectMapper.writeValueAsString(refereeMapper.toDTO(referee));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/referees/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(refereeJSON)
        ).andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    void testIfValidRefereeCanBeRegisteredAndUpdated() throws Exception {
        Referee referee = new Referee();
        referee.setName("Diogo Sousa");
        String refereeJSON = objectMapper.writeValueAsString(refereeMapper.toDTO(referee));

        String jsonResponse = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/referees/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(refereeJSON)
        ).andDo(print()).andExpect(status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Diogo Sousa")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.hasCertificate").value("false")
        ).andReturn().getResponse().getContentAsString();

        RefereeDTO savedDTO = objectMapper.readValue(jsonResponse, RefereeDTO.class);
        savedDTO.setName("Vania Mendonça");
        savedDTO.setHasCertificate(true);
        String updatedPlayerJSON = objectMapper.writeValueAsString(savedDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/referees/" + savedDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedPlayerJSON)
        ).andDo(print()).andExpect(status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Vania Mendonça")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.hasCertificate").value("true")
        );
    }

    @Test
    void testIfValidRefereeCanBeAddedAndRemoved() throws Exception {
        Referee referee = new Referee();
        referee.setName("Diogo Sousa");
        String refereeJSON = objectMapper.writeValueAsString(refereeMapper.toDTO(referee));

        String jsonResponse = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/referees/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(refereeJSON)
        ).andDo(print()).andExpect(status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Diogo Sousa")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.hasCertificate").value("false")
        ).andReturn().getResponse().getContentAsString();

        RefereeDTO savedDTO = objectMapper.readValue(jsonResponse, RefereeDTO.class);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/referees/" + savedDTO.getId())
        ).andDo(print()).andExpect(status().is2xxSuccessful());
        String response = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/referees/")
        ).andReturn().getResponse().getContentAsString();

        List<?> referees = objectMapper.readValue(response, List.class);
        assertTrue(referees.isEmpty());
    }
}
