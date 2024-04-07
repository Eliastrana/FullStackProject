package edu.ntnu.idatt2105.SpringbootBackend.controller;

import edu.ntnu.idatt2105.SpringbootBackend.dto.UserQuizAttemptDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.UserQuizAttemptNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.service.UserQuizAttemptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "UserQuizAttempt Management")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/userQuizAttempts")
public class UserQuizAttemptController {

    private final UserQuizAttemptService userQuizAttemptService;

    @Autowired
    public UserQuizAttemptController(UserQuizAttemptService userQuizAttemptService) {
        this.userQuizAttemptService = userQuizAttemptService;
    }

    @Operation(summary = "Create User Quiz Attempt", description = "Creates a new user quiz attempt", responses = {
        @ApiResponse(responseCode = "201", description = "User Quiz Attempt created successfully", content = @Content(schema = @Schema(implementation = UserQuizAttemptDTO.class)))})
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserQuizAttemptDTO> createUserQuizAttempt(@RequestBody UserQuizAttemptDTO userQuizAttemptDTO) {
        UserQuizAttemptDTO createdAttempt = userQuizAttemptService.createUserQuizAttempt(userQuizAttemptDTO);
        return new ResponseEntity<>(createdAttempt, HttpStatus.CREATED);
    }

    @Operation(summary = "Get User Quiz Attempts by User ID", description = "Retrieves all quiz attempts for a specific user", responses = {
        @ApiResponse(responseCode = "200", description = "User Quiz Attempts fetched successfully", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserQuizAttemptDTO.class))))})
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserQuizAttemptDTO>> getUserAttempts(@PathVariable UUID userId) {
        List<UserQuizAttemptDTO> attempts = userQuizAttemptService.getUserAttempts(userId);
        return ResponseEntity.ok(attempts);
    }

    @Operation(summary = "Get User Quiz Attempt by ID", description = "Retrieves a specific user quiz attempt by its ID", responses = {
        @ApiResponse(responseCode = "200", description = "User Quiz Attempt fetched successfully", content = @Content(schema = @Schema(implementation = UserQuizAttemptDTO.class))),
        @ApiResponse(responseCode = "404", description = "User Quiz Attempt not found")})
    @GetMapping("/{id}")
    public ResponseEntity<UserQuizAttemptDTO> getUserQuizAttemptById(@PathVariable UUID id) {
        try {
            UserQuizAttemptDTO attempt = userQuizAttemptService.getUserQuizAttemptById(id);
            return ResponseEntity.ok(attempt);
        } catch (UserQuizAttemptNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Update User Quiz Attempt", description = "Updates an existing user quiz attempt", responses = {
        @ApiResponse(responseCode = "200", description = "User Quiz Attempt updated successfully", content = @Content(schema = @Schema(implementation = UserQuizAttemptDTO.class))),
        @ApiResponse(responseCode = "404", description = "User Quiz Attempt not found")})
    @PutMapping("/{id}")
    public ResponseEntity<UserQuizAttemptDTO> updateUserQuizAttempt(@PathVariable UUID id, @RequestBody UserQuizAttemptDTO userQuizAttemptDTO) {
        try {
            UserQuizAttemptDTO updatedAttempt = userQuizAttemptService.updateUserQuizAttempt(id, userQuizAttemptDTO);
            return ResponseEntity.ok(updatedAttempt);
        } catch (UserQuizAttemptNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete User Quiz Attempt", description = "Deletes a specific user quiz attempt by its ID", responses = {
        @ApiResponse(responseCode = "204", description = "User Quiz Attempt deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User Quiz Attempt not found")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserQuizAttempt(@PathVariable UUID id) {
        try {
            userQuizAttemptService.deleteUserQuizAttempt(id);
            return ResponseEntity.noContent().build();
        } catch (UserQuizAttemptNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
