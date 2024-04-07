package edu.ntnu.idatt2105.SpringbootBackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuizCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuizDTO;
import edu.ntnu.idatt2105.SpringbootBackend.model.Difficulty;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.repository.UserRepository;
import edu.ntnu.idatt2105.SpringbootBackend.service.QuizService;
import edu.ntnu.idatt2105.SpringbootBackend.service.UserService;
import lombok.With;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class QuizControllerIntegrationTest {

    @MockBean
    private QuizService quizService;

    @MockBean
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

 
    @BeforeEach
    public void setup() {
        // Setup test data here if necessary
        // For example, if you need to create users in the database before testing
}

    

    @Test
    @WithMockUser
    @DirtiesContext
    public void createQuiz_ReturnsQuizDTO_WhenSuccessful() throws Exception {
        User newUser = new User();
        newUser.setId(UUID.randomUUID()); // Use this ID in your test
        newUser.setUsername(UUID.randomUUID().toString());
        newUser.setPassword("password"); // Ensure this is encoded if your security config expects it
        // Set other required fields...
        userRepository.save(newUser);
        UUID testUserId = newUser.getId();
        QuizCreateDTO quizCreateDTO = QuizCreateDTO.builder()
                .title("General Knowledge")
                .description("A quiz covering a wide range of topics.")
                .difficulty(Difficulty.EASY)
                .isPublic(true)
                .creatorId(testUserId)
                .build();

        QuizDTO expectedQuizDTO = QuizDTO.builder()
                .id(UUID.randomUUID())
                .title(quizCreateDTO.getTitle())
                .description(quizCreateDTO.getDescription())
                .difficulty(quizCreateDTO.getDifficulty())
                .isPublic(quizCreateDTO.isPublic())
                .creatorId(quizCreateDTO.getCreatorId())
                .build();

        Mockito.when(quizService.createQuiz(Mockito.any(QuizCreateDTO.class))).thenReturn(expectedQuizDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/quiz")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(quizCreateDTO))
            .with(csrf()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(expectedQuizDTO.getTitle()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(expectedQuizDTO.getDescription()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.difficulty").value(expectedQuizDTO.getDifficulty().toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.isPublic").value(expectedQuizDTO.isPublic()));
    }
}
