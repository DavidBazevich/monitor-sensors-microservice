package org.senla.IT;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.senla.dto.creators.UnitCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@WithMockUser(username = "David", roles = {"ADMIN"})
public class UnitControllerIT extends ITBase{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveUnitTest() throws Exception{
        UnitCreateDto unitCreateDto = new UnitCreateDto("Unit example");

        mockMvc.perform(post("/api/v1/unit/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(unitCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Unit example"));
    }

    @Test
    void getUnitByIdTest() throws Exception {
        mockMvc.perform(get("/api/v1/unit/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3));
    }

    @Test
    void getUnitsTest() throws Exception {
        mockMvc.perform(get("/api/v1/unit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void updateUnitTest() throws Exception{
        UnitCreateDto newUnit = new UnitCreateDto("Unit the second example");

        mockMvc.perform(put("/api/v1/unit/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUnit)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Unit the second example"));
    }

    @Test
    void deleteUnitTest() throws Exception {
        mockMvc.perform(delete("/api/v1/unit/1"))
                .andExpect(status().isNoContent());
    }

}
