package edu.ntnu.idatt2105.SpringbootBackend.controller;

import edu.ntnu.idatt2105.SpringbootBackend.dto.UserCreationDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.UserDTO;
import edu.ntnu.idatt2105.SpringbootBackend.security.AuthenticationResponse;
import edu.ntnu.idatt2105.SpringbootBackend.security.AuthenticationRequest;
import edu.ntnu.idatt2105.SpringbootBackend.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


/**
 * Handles user authentication-related HTTP requests, including user registration and login.
 * Utilizes {@link AuthenticationService} for the authentication process
 * and provides JWT tokens upon successful authentication.
 *
 * @author Vegard Johnsen, Sander R. Skofsrud
 */
@Tag(name = "User Authentication")
@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final AuthenticationService authService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);


    /**
     * Registers a new user with the provided user details.
     * Upon successful registration, returns a JWT token for authentication.
     * Log the registration attempt and outcome.
     *
     * @param userDto the user details for registration, encapsulated in {@link UserCreationDTO}.
     * @return {@link ResponseEntity<AuthenticationResponse>} containing the JWT token on success,
     * or an error message on failure.
     */
    @Operation(summary = "Register a new user", description = "Registers a user and returns a JWT token for authentication")
    @ApiResponse(responseCode = "200", description = "Successfully registered user")
    @ApiResponse(responseCode = "400", description = "Error registering user")
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
     * Authenticates a user with the provided login credentials.
     * Upon successful authentication, returns a JWT token.
     * Logs the login attempt and outcome.
     *
     * @param userDto the login credentials of the user, encapsulated in {@link UserDTO}.
     * @return {@link ResponseEntity<AuthenticationResponse>} containing the JWT token on success,
     * or an error message on failure.
     */
    @Operation(summary = "Log in a user", description = "Logs in a user and returns a JWT token for authentication")
    @ApiResponse(responseCode = "200", description = "User logged in successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody UserDTO userDto) {
        logger.info("Logging in user with username: " + userDto.getUsername());
        try {
            AuthenticationResponse authResponse = authService.authenticate(AuthenticationRequest.builder().username(userDto.getUsername()).password(userDto.getPassword()).build());
            logger.info("Authenticated user with username: " + userDto.getUsername());
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            logger.error("Error logging in user: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
