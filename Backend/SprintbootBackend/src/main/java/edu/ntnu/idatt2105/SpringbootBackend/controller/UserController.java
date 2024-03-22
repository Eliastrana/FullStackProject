package edu.ntnu.idatt2105.SpringbootBackend.controller;

import edu.ntnu.idatt2105.SpringbootBackend.dto.UserCreationDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.UserDTO;
import edu.ntnu.idatt2105.SpringbootBackend.security.AuthenticationResponse;
import edu.ntnu.idatt2105.SpringbootBackend.security.AuthenticationRequest;
import edu.ntnu.idatt2105.SpringbootBackend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Handles requests related to user authentication, including registering and logging in users.
 */
@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final AuthenticationService authService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);


    /**
     * Registers a new user with the provided user details.
     *
     * @param userDto The user to register.
     * @return The response containing the user's JWT token.
     */

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody UserCreationDTO userDto) {
        logger.info("Registering user with username: " + userDto.getUsername());
        try {
            AuthenticationResponse response = authService.register(userDto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error registering user: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Logs in a user with the provided user details.
     *
     * @param userDto The DTO containing the user´s login credentials
     * @return a response entity with an authentication response or error message
     */

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody UserDTO userDto) {
        logger.info("Logging in user with username: " + userDto.getUsername());
        try {
            AuthenticationResponse authResponse = authService.authenticate(AuthenticationRequest
                    .builder()
                    .username(userDto.getUsername())
                    .password(userDto.getPassword())
                    .build());
            logger.info("Authenticated user with username: " + userDto.getUsername());
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            logger.error("Error logging in user: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}