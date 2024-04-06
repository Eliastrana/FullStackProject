package edu.ntnu.idatt2105.SpringbootBackend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2105.SpringbootBackend.dto.UserCreationDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.UserDTO;
import edu.ntnu.idatt2105.SpringbootBackend.security.AuthenticationRequest;
import edu.ntnu.idatt2105.SpringbootBackend.security.AuthenticationResponse;
import edu.ntnu.idatt2105.SpringbootBackend.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class UserControllerTest {

    @Mock
    private AuthenticationService authService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        UserController userController = new UserController(authService);
        mockMvc = standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testRegisterUser() throws Exception {
        UserCreationDTO userCreationDTO = new UserCreationDTO("testUser", "testPassword", "test@test.com");
        AuthenticationResponse authResponse = new AuthenticationResponse("dummy-jwt-token");

        given(authService.register(userCreationDTO)).willReturn(authResponse);

        mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreationDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(authResponse)));
    }

    @Test
    void testLoginUser() throws Exception {
        UserDTO userDTO = new UserDTO("testUser", "testPassword");
        AuthenticationResponse authResponse = new AuthenticationResponse("dummy-jwt-token");

        given(authService.authenticate(new AuthenticationRequest(userDTO.getUsername(), userDTO.getPassword()))).willReturn(authResponse);

        mockMvc.perform(post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(authResponse)));
    }

    @Test
    void testRegisterUser_Failure() throws Exception {
        UserCreationDTO userCreationDTO = new UserCreationDTO("", "", ""); // Assuming empty fields are invalid
        // Simulating service throwing an exception due to invalid input
        given(authService.register(any(UserCreationDTO.class))).willThrow(new RuntimeException("Invalid input data"));

        mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreationDTO)))
                .andExpect(status().isBadRequest()); // Expecting a Bad Request response
    }

    @Test
    void testLoginUser_Failure() throws Exception {
        UserDTO userDTO = new UserDTO("nonexistentUser", "wrongPassword"); // Assuming these credentials are invalid
        // Simulating service throwing an exception due to authentication failure
        given(authService.authenticate(any())).willThrow(new RuntimeException("Authentication failed"));

        mockMvc.perform(post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isInternalServerError()); // Expecting an Internal Server Error response
    }
}

