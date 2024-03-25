package edu.ntnu.idatt2105.SpringbootBackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2105.SpringbootBackend.dto.UserCreationDTO;
import edu.ntnu.idatt2105.SpringbootBackend.security.AuthenticationResponse;
import edu.ntnu.idatt2105.SpringbootBackend.security.JWTAuthFilter;
import edu.ntnu.idatt2105.SpringbootBackend.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

// Static imports
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(controllers = UserController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {JWTAuthFilter.class}))
@ActiveProfiles("test")
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext context;

  @MockBean
  private AuthenticationService authService;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  public void setup() {
    // Initialize MockMvc with CSRF and Spring Security configuration
    mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity()) // Apply Spring Security configuration
            .build();
  }

  @Test
  @WithMockUser // Mock a user to bypass Spring Security authentication
  void registerUser_Success() throws Exception {
    UserCreationDTO userCreationDTO = new UserCreationDTO("testuser", "testpassword", "test@example.com");
    AuthenticationResponse response = new AuthenticationResponse("dummyToken");

    given(authService.register(userCreationDTO)).willReturn(response);

    mockMvc.perform(post("/api/user/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userCreationDTO))
                    .with(csrf())) // Apply CSRF token
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(response)));
  }

  // Additional test methods...
}
