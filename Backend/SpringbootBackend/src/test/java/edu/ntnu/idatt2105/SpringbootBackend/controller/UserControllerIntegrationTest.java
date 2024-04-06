  package edu.ntnu.idatt2105.SpringbootBackend.controller;

  import com.fasterxml.jackson.databind.ObjectMapper;
  import edu.ntnu.idatt2105.SpringbootBackend.dto.UserCreationDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.UserDTO;
import edu.ntnu.idatt2105.SpringbootBackend.repository.UserRepository;
import edu.ntnu.idatt2105.SpringbootBackend.security.AuthenticationResponse;
  import edu.ntnu.idatt2105.SpringbootBackend.service.UserService;
  import org.junit.jupiter.api.BeforeEach;
  import org.junit.jupiter.api.Test;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
  import org.springframework.boot.test.context.SpringBootTest;
  import org.springframework.http.MediaType;
  import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
  import org.springframework.test.web.servlet.MockMvc;

  import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
  import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
  import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
  import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

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

    @Autowired
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
      // Setup test data here if necessary
      // For example, if you need to create users in the database before testing
    }

    @Test
    @WithMockUser
    @DirtiesContext
    void registerUser_Success() throws Exception {
      UUID username = UUID.randomUUID();
      String usernameString = username.toString();
      UUID email = UUID.randomUUID();
      String emailString = email.toString();
      UserCreationDTO userCreationDTO = new UserCreationDTO(usernameString, "testpassword", "sffr@example.com");

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

      UserDTO userDTO = new UserDTO("testUser", "password");
      mockMvc.perform(post("/api/user/login")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(userDTO))
                      .with(csrf()))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.token", notNullValue())); // Verifies that a token is returned in the response
    }

    @Test
    @DirtiesContext
    void registerUser_WithExistingUsername_Failure() throws Exception {
    // Assuming "testuser7" already exists
    UserCreationDTO userCreationDTO = new UserCreationDTO("testuser", "password", "newemail@example.com");

    mockMvc.perform(post("/api/user/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreationDTO))
            .with(csrf()))
            .andExpect(status().isBadRequest());
}
@Test
@DirtiesContext
void registerUser_WithNullUsername_Failure() throws Exception {
    UserCreationDTO userCreationDTO = new UserCreationDTO(null, "somepassword", "email@example.com");
    mockMvc.perform(post("/api/user/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userCreationDTO))
                    .with(csrf()))
            .andExpect(status().isBadRequest());
  }
}
