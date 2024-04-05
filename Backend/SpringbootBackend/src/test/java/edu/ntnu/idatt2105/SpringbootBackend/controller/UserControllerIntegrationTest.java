  package edu.ntnu.idatt2105.SpringbootBackend.controller;

  import com.fasterxml.jackson.databind.ObjectMapper;
  import edu.ntnu.idatt2105.SpringbootBackend.dto.UserCreationDTO;
  import edu.ntnu.idatt2105.SpringbootBackend.security.AuthenticationResponse;
  import edu.ntnu.idatt2105.SpringbootBackend.service.UserService;
  import org.junit.jupiter.api.BeforeEach;
  import org.junit.jupiter.api.Test;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
  import org.springframework.boot.test.context.SpringBootTest;
  import org.springframework.http.MediaType;
  import org.springframework.security.test.context.support.WithMockUser;
  import org.springframework.test.context.ActiveProfiles;
  import org.springframework.test.web.servlet.MockMvc;

  import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
  import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
  import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
  import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
  import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
  import static org.hamcrest.Matchers.notNullValue;

  @SpringBootTest
  @AutoConfigureMockMvc
  @ActiveProfiles("test")
  class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService; // If you need to set up users before tests

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
      // Setup test data here if necessary
      // For example, if you need to create users in the database before testing
    }

    @Test
    @WithMockUser
    void registerUser_Success() throws Exception {
      UserCreationDTO userCreationDTO = new UserCreationDTO("testuser6", "testpassword", "test6@example.com");

      mockMvc.perform(post("/api/user/register")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(userCreationDTO))
                      .with(csrf())) // Apply CSRF token for POST requests
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.token", notNullValue())); // Verify that a token is present
    }

    @Test
    @WithMockUser
    void loginUser_Success() throws Exception {
      // First, ensure a user is registered in the system
      UserCreationDTO userCreationDTO = new UserCreationDTO("loginTestUser4", "loginTestPassword", "logintest4@example.com");
      mockMvc.perform(post("/api/user/register")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(userCreationDTO))
              .with(csrf()));

      // Construct login request body directly
      String loginRequestBody = "{\"username\":\"loginTestUser4\",\"password\":\"loginTestPassword\"}";

      mockMvc.perform(post("/api/user/login")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(loginRequestBody)
                      .with(csrf()))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.token", notNullValue())); // Verifies that a token is returned in the response
    }


  }
