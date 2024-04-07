package edu.ntnu.idatt2105.SpringbootBackend.controller;

import edu.ntnu.idatt2105.SpringbootBackend.dto.*;
import edu.ntnu.idatt2105.SpringbootBackend.security.AuthenticationResponse;
import edu.ntnu.idatt2105.SpringbootBackend.security.AuthenticationRequest;
import edu.ntnu.idatt2105.SpringbootBackend.service.AuthenticationService;
import edu.ntnu.idatt2105.SpringbootBackend.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


/**
 * Controller for handling user authentication requests such as login and registration.
 * Utilizes {@link AuthenticationService} to process the authentication logic
 * and generate JWT tokens upon successful authentication.
 * This class serves as an entry point for the client-side to access user authentication-related functionalities.
 *
 * @author Vegard Johnsen, Sander R. Skofsrud
 * @see AuthenticationService
 * @since 0.1
 * @version 0.1
 */
@Tag(name = "User Authentication" , description = "API for user authentication")
@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final AuthenticationService authService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);


    /**
     * Registers a new user in the system.
     * This method takes user registration details,
     * processes them through the {@link AuthenticationService},
     * and upon successful registration, returns a JWT token for future authentication.
     *
     * @param userDto The User Creation DTO containing the new user's registration details such as username, password, and email.
     * @return A ResponseEntity containing the {@link AuthenticationResponse} with a JWT token
     * if registration is successful;
     * otherwise, a bad request status.
     * @see UserCreationDTO
     * @see AuthenticationResponse
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
     * Authenticates a user based on the provided login credentials.
     * Upon successful authentication,
     * this method returns a JWT token that the client can use for later authenticated requests.
     *
     * @param userDto The User DTO containing the login credentials such as username and password.
     * @return A ResponseEntity containing the {@link AuthenticationResponse} with a JWT token if authentication is successful;
     * otherwise, an internal server error status.
     * @see UserDTO
     * @see AuthenticationResponse
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

    @ApiResponse(responseCode = "200", description = "User details fetched successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @Operation(summary = "Get user details", description = "Fetches the details of the currently authenticated user")
    @GetMapping("/details")
    public ResponseEntity<UserDetailsDTO> getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        logger.info("Fetching user details for user with username: " + username);

        UserDetailsDTO userDetails = userService.getUserDetails(username);

        if (userDetails == null) {
            logger.error("User not found: " + username);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDetails);
    }

    @Operation(summary = "Get all users", description = "Fetches all users in the system with their details")
    @ApiResponse(responseCode = "200", description = "Users fetched successfully")
    @ApiResponse(responseCode = "404", description = "No users found")
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Iterable<UserDetailsDTO>> getAllUsers() {
        logger.info("Fetching all users");
        Iterable<UserDetailsDTO> users = userService.getAllUsers();

        if (users == null) {
            logger.error("No users found");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Delete user", description = "Deletes a user from the system")
    @ApiResponse(responseCode = "200", description = "User deleted successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        logger.info("Deleting user with username: " + username);
        boolean isDeleted = userService.deleteUser(username);

        if (!isDeleted) {
            logger.error("User not found: " + username);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update password", description = "Updates a user's password")
    @ApiResponse(responseCode = "200", description = "Password updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid password")
    @PutMapping("/update/password")
    public ResponseEntity<?> updatePassword(Authentication authentication, @RequestBody PasswordUpdateDTO passwordUpdateDTO) {
        userService.updatePassword(authentication.getName(), passwordUpdateDTO);
        return ResponseEntity.ok().build();
    }

}
