package edu.ntnu.idatt2105.SpringbootBackend.controller;

/* 
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuizCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuizDTO;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.security.JWTAuthFilter;
import edu.ntnu.idatt2105.SpringbootBackend.security.SecurityConfig;
import edu.ntnu.idatt2105.SpringbootBackend.service.QuizService;
import edu.ntnu.idatt2105.SpringbootBackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = QuizController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {JWTAuthFilter.class, SecurityConfig.class}))
@ActiveProfiles("test")
class QuizControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private QuizService quizService;

  @MockBean
  private UserService userService;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  @WithMockUser
    // Mock a user for bypassing Spring Security authentication
  void createQuiz_Success() throws Exception {
    UUID creatorId = UUID.randomUUID();
    QuizCreateDTO quizCreateDTO = QuizCreateDTO.builder()
            .title("Test Title")
            .description("Test Description")
            .creatorId(creatorId)
            .build();

    QuizDTO quizDTO = QuizDTO.builder()
            .id(UUID.randomUUID())
            .title("Test Title")
            .description("Test Description")
            .creatorId(creatorId)
            .build();

    // Mock User object to return
    User mockUser = new User();
    mockUser.setId(creatorId); // Set the ID or any required fields

    // When userService.loadUserByUsername(anyString()) is called, return mockUser
    given(userService.loadUserByUsername(anyString())).willReturn(mockUser);

    // Mock quizService.createQuiz to return quizDTO
    given(quizService.createQuiz(any(QuizCreateDTO.class))).willReturn(quizDTO);

    mockMvc.perform(post("/api/quiz/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(quizCreateDTO))
                    .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(quizDTO)));
  }
}
*/