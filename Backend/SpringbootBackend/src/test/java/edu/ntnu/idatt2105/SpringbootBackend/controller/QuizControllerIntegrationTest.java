package edu.ntnu.idatt2105.SpringbootBackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuizDTO;
import edu.ntnu.idatt2105.SpringbootBackend.model.Difficulty;
import edu.ntnu.idatt2105.SpringbootBackend.service.QuizService;
import edu.ntnu.idatt2105.SpringbootBackend.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class QuizControllerIntegrationTest {

    @MockBean
    private QuizService quizService;

    @MockBean
    private UserService userService;


    @Autowired
    private MockMvc mockMvc;

    private QuizDTO expectedQuizDTO;


    private final ObjectMapper objectMapper = new ObjectMapper();

 

        @BeforeEach
public void setup() {
    // Initialize expectedQuizDTO
    expectedQuizDTO = QuizDTO.builder()
        .id(UUID.randomUUID())
        .title("Test Quiz")
        .description("This is a test quiz.")
        .difficulty(Difficulty.EASY)
        .isPublic(true)
        .creatorId(UUID.randomUUID())
        .categoryId(UUID.randomUUID())
        .imageId(UUID.randomUUID())
        .build();
}

    

    @Test
    @WithMockUser
    public void getAllQuizzes_ReturnsListOfQuizzes_WhenSuccessful() throws Exception {
    // Setup mock response
    List<QuizDTO> expectedQuizzes = new ArrayList<>();
    expectedQuizzes.add(expectedQuizDTO);
    Mockito.when(quizService.getAllQuizzes()).thenReturn(expectedQuizzes);

    // Perform request and check response
    mockMvc.perform(MockMvcRequestBuilders.get("/api/quiz"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(expectedQuizDTO.getTitle()));
}

@Test
@WithMockUser
public void getQuizById_ReturnsQuizDTO_WhenSuccessful() throws Exception {
    // Setup mock response
    UUID quizId = UUID.randomUUID();
    Mockito.when(quizService.getQuizById(quizId)).thenReturn(expectedQuizDTO);

    // Perform request and check response
    mockMvc.perform(MockMvcRequestBuilders.get("/api/quiz/" + quizId))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(expectedQuizDTO.getTitle()));
}

@Test
@WithMockUser
public void updateQuiz_ReturnsUpdatedQuizDTO_WhenSuccessful() throws Exception {
    // Setup mock response
    UUID quizId = UUID.randomUUID();
    Mockito.when(quizService.updateQuiz(Mockito.eq(quizId), Mockito.any(QuizDTO.class))).thenReturn(expectedQuizDTO);

    // Perform request and check response
    mockMvc.perform(MockMvcRequestBuilders.put("/api/quiz/" + quizId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(expectedQuizDTO)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(expectedQuizDTO.getTitle()));
}

@Test
@WithMockUser
public void deleteQuiz_ReturnsNoContent_WhenSuccessful() throws Exception {
    // Setup mock response
    UUID quizId = UUID.randomUUID();
    Mockito.doNothing().when(quizService).deleteQuiz(quizId);

    // Perform request and check response
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/quiz/" + quizId))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
}
}
