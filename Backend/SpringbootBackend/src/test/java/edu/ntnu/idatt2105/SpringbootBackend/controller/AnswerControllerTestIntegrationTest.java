package edu.ntnu.idatt2105.SpringbootBackend.controller;

import edu.ntnu.idatt2105.SpringbootBackend.dto.AnswerDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.AnswerNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.service.AnswerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser
public class AnswerControllerTestIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnswerService answerService;

    private AnswerDTO answerDTO;
    private UUID questionId;

    @BeforeEach
    public void setUp() {
        answerDTO = new AnswerDTO();
        answerDTO.setId(UUID.randomUUID());
        answerDTO.setText("Test Answer");
        questionId = UUID.randomUUID();
    }

    @Test
    public void createAnswer_ReturnsCreated_WhenSuccessful() throws Exception {
        when(answerService.createAnswer(any(UUID.class), any(AnswerDTO.class))).thenReturn(answerDTO);

        mockMvc.perform(post("/api/answers/" + questionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(answerDTO)))
                .andExpect(status().isCreated());
    }


    @Test
    public void updateAnswer_ReturnsOk_WhenSuccessful() throws Exception {
        when(answerService.updateAnswer(any(UUID.class), any(AnswerDTO.class))).thenReturn(answerDTO);

        mockMvc.perform(put("/api/answers/" + answerDTO.getId().toString()) // Fix the method call
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(answerDTO)))
                .andExpect(status().isOk());
    }

@Test
public void updateAnswer_ReturnsNotFound_WhenAnswerNotFound() throws Exception {
    when(answerService.updateAnswer(any(UUID.class), any(AnswerDTO.class))).thenThrow(new AnswerNotFoundException("Answer not found"));

    mockMvc.perform(put("/api/answers/" + UUID.randomUUID())
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(answerDTO)))
            .andExpect(status().isNotFound());
}

@Test
public void deleteAnswer_ReturnsNoContent_WhenSuccessful() throws Exception {
    doNothing().when(answerService).deleteAnswer(any(UUID.class));

    mockMvc.perform(delete("/api/answers/" + answerDTO.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
}

@Test
public void deleteAnswer_ReturnsNotFound_WhenAnswerNotFound() throws Exception {
    doThrow(new AnswerNotFoundException("Answer not found")).when(answerService).deleteAnswer(any(UUID.class));

    mockMvc.perform(delete("/api/answers/" + UUID.randomUUID())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
}


}

