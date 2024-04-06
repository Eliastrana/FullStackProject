package edu.ntnu.idatt2105.SpringbootBackend.controller;

import edu.ntnu.idatt2105.SpringbootBackend.dto.UserQuizAttemptDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.UserQuizAttemptNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.service.UserQuizAttemptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * The {@link UserQuizAttemptController} class handles HTTP requests related to user quiz attempts.
 * It supports operations such as creating, retrieving, updating, and deleting quiz attempts for authenticated users.
 * This controller ensures that only authenticated users can perform these operations, leveraging Spring Security
 * for authorization checks.
 */
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

    /**
     * Creates a new {@link UserQuizAttemptDTO} for a quiz attempt made by an authenticated user.
     * This endpoint requires the user to be authenticated and submits their quiz attempt details
     * to be saved in the system. On successful creation, it returns the created quiz attempt
     * data along with HTTP status 201 (Created).
     *
     * @param userQuizAttemptDTO The DTO containing quiz attempt details submitted by the user.
     * @return A {@link ResponseEntity} containing the created {@link UserQuizAttemptDTO} with HTTP status {@link HttpStatus#CREATED}.
     * In case of failure, it may return various HTTP statuses like {@link HttpStatus#BAD_REQUEST} depending on the error.
     */
    @Operation(summary = "Create User Quiz Attempt", description = "Creates a new user quiz attempt")
    @ApiResponse(responseCode = "201", description = "User Quiz Attempt created successfully")
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserQuizAttemptDTO> createUserQuizAttempt(@RequestBody UserQuizAttemptDTO userQuizAttemptDTO) {
        UserQuizAttemptDTO createdAttempt = userQuizAttemptService.createUserQuizAttempt(userQuizAttemptDTO);
        return new ResponseEntity<>(createdAttempt, HttpStatus.CREATED);
    }

    /**
     * Retrieves all quiz attempts made by a specified user. The user is identified by a UUID.
     * Only authenticated users can retrieve their quiz attempts. This method ensures data privacy
     * by allowing users to access only their quiz attempts.
     *
     * @param userId The UUID of the user whose quiz attempts are to be retrieved.
     * @return A {@link ResponseEntity} with a list of {@link UserQuizAttemptDTO} containing the user's quiz attempts
     * and HTTP status {@link HttpStatus#OK}. If the user has no quiz attempts, this method returns an empty list.
     */
    @Operation(summary = "Get User Quiz Attempts by User ID", description = "Retrieves all quiz attempts for a specific user")
    @ApiResponse(responseCode = "200", description = "User Quiz Attempts fetched successfully")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserQuizAttemptDTO>> getUserAttempts(@PathVariable UUID userId) {
        List<UserQuizAttemptDTO> attempts = userQuizAttemptService.getUserAttempts(userId);
        return ResponseEntity.ok(attempts);
    }

    /**
     * Retrieves a specific quiz attempt by its unique identifier (ID).
     * This method looks for a quiz attempt associated with the provided ID.
     * If found, it returns the quiz attempt details to the requester.
     *
     * @param id The UUID of the quiz attempt to retrieve.
     * @return A {@link ResponseEntity} with {@link UserQuizAttemptDTO} containing the quiz attempt details
     *         and HTTP status {@link HttpStatus#OK} if found; otherwise, HTTP status {@link HttpStatus#NOT_FOUND} if the quiz attempt does not exist.
     */
    @Operation(summary = "Get User Quiz Attempt by ID", description = "Retrieves a specific user quiz attempt by its ID")
    @ApiResponse(responseCode = "200", description = "User Quiz Attempt fetched successfully")
    @GetMapping("/{id}")
    public ResponseEntity<UserQuizAttemptDTO> getUserQuizAttemptById(@PathVariable UUID id) {
        try {
            UserQuizAttemptDTO attempt = userQuizAttemptService.getUserQuizAttemptById(id);
            return ResponseEntity.ok(attempt);
        } catch (UserQuizAttemptNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates an existing quiz attempt with new information provided by the user.
     * The method checks if the quiz attempt with the given ID exists and updates it accordingly.
     * If successful, it returns the updated quiz attempt details.
     *
     * @param id The UUID of the quiz attempt to update.
     * @param userQuizAttemptDTO The DTO containing the updated quiz attempt information.
     * @return A {@link ResponseEntity} with the updated {@link UserQuizAttemptDTO} and HTTP status {@link HttpStatus#OK};
     * HTTP status {@link HttpStatus#NOT_FOUND} if the quiz attempt does not exist.
     */
    @Operation(summary = "Update User Quiz Attempt", description = "Updates an existing user quiz attempt")
    @ApiResponse(responseCode = "200", description = "User Quiz Attempt updated successfully")
    @PutMapping("/{id}")
    public ResponseEntity<UserQuizAttemptDTO> updateUserQuizAttempt(@PathVariable UUID id, @RequestBody UserQuizAttemptDTO userQuizAttemptDTO) {
        try {
            UserQuizAttemptDTO updatedAttempt = userQuizAttemptService.updateUserQuizAttempt(id, userQuizAttemptDTO);
            return ResponseEntity.ok(updatedAttempt);
        } catch (UserQuizAttemptNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a quiz attempt identified by its unique ID.
     * This method removes a specific quiz attempt from the system.
     * If successful, it returns an HTTP status indicating that the content has been deleted (204: User Quiz Attempt deleted successfully).
     *
     * @param id The UUID of the quiz attempt to delete.
     * @return A {@link ResponseEntity} with HTTP status {@link HttpStatus#NO_CONTENT} if the deletion is successful;
     * HTTP status {@link HttpStatus#NOT_FOUND} if the quiz attempt does not exist.
     */
    @Operation(summary = "Delete User Quiz Attempt", description = "Deletes a specific user quiz attempt by its ID")
    @ApiResponse(responseCode = "204", description = "User Quiz Attempt deleted successfully")
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
