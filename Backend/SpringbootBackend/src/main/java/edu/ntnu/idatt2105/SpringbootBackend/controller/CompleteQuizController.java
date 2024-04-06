package edu.ntnu.idatt2105.SpringbootBackend.controller;

import edu.ntnu.idatt2105.SpringbootBackend.dto.CompleteQuizDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.QuizNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.service.CompleteQuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Provides endpoints for managing the entire lifecycle of quizzes, including creation, retrieval,
 * updating, and deletion. This controller interacts with the CompleteQuizService to perform
 * operations on quizzes as a whole, encompassing their questions and answers. It aims to
 * facilitate the management of quizzes, from their inception to their consumption and eventual
 * removal, ensuring a comprehensive approach to quiz handling within the system.
 */
@Tag(name = "Complete Quiz Management")
@RestController
@RequestMapping("/api/completeQuiz")
public class CompleteQuizController {

    private final CompleteQuizService completeQuizService;
    private final Logger logger = LoggerFactory.getLogger(CompleteQuizController.class);

    @Autowired
    public CompleteQuizController(CompleteQuizService completeQuizService) {
        this.completeQuizService = completeQuizService;
    }

    /**
     * Creates a new quiz complete with questions and answers. The method expects a
     * {@link CompleteQuizDTO} that encapsulates all the necessary details for a quiz,
     * including its title, description, associated questions, and their answers.
     * Upon successful creation, the method returns the unique identifier of the newly created
     * quiz, which can be used for future references.
     *
     * This endpoint requires the caller to be authenticated, ensuring that only authorized
     * users can create quizzes. The creation process involves validating the input DTO, persisting
     * the quiz information, and generating a unique identifier for the quiz, which is then returned
     * to the caller encapsulated in a response entity.
     *
     * @param completeQuizDTO The DTO containing the complete quiz details to be created.
     * @return A ResponseEntity containing a map with the newly created quiz's unique identifier
     *         and a confirmation message, alongside the HTTP status code for creation (201).
     *         If the creation process fails due to server-side issues, an error message is returned
     *         with an HTTP status code indicating an internal server error (500).
     */
    @Operation(summary = "Create a complete quiz", description = "Creates a new quiz along with questions and answers.")
    @ApiResponse(responseCode = "201", description = "Complete quiz created successfully.")
    @ApiResponse(responseCode = "400", description = "Bad request due to invalid input.")
    @ApiResponse(responseCode = "403", description = "Forbidden - user not authorized to perform this action.")
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createCompleteQuiz(@RequestBody CompleteQuizDTO completeQuizDTO) {
    try {
        UUID createdQuizId = completeQuizService.createCompleteQuiz(completeQuizDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", createdQuizId, "message", "Complete quiz created successfully."));
    } catch (Exception e) {
        logger.error("Failed to create complete quiz: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(Map.of("error", "Failed to create complete quiz", "message", e.getMessage()));
    }
}


    /**
     * Retrieves a complete quiz by its UUID, including all associated questions and their answers.
     * This method facilitates accessing detailed information about a specific quiz.
     * The retrieval process involves searching for the quiz using its UUID, and if found,
     * returning the complete details encapsulated in a DTO.
     *
     * Access to this endpoint is restricted to authenticated users, ensuring that quiz information
     * is protected and only accessible to users with the appropriate permissions. If the quiz is
     * found, its details are returned; otherwise, an HTTP status code indicating not found (404) is
     * returned.
     *
     * @param quizId The UUID of the quiz to retrieve.
     * @return A ResponseEntity containing the CompleteQuizDTO if the quiz is found, or an HTTP
     *         status code indicating not found (404) if the quiz does not exist.
     */
    @Operation(summary = "Get a complete quiz", description = "Fetches a complete quiz with questions and answers.")
    @ApiResponse(responseCode = "200", description = "Complete quiz fetched successfully.")
    @ApiResponse(responseCode = "404", description = "Quiz not found.")
    @GetMapping("/{quizId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getCompleteQuiz(@PathVariable UUID quizId) {
    try {
        CompleteQuizDTO completeQuizDTO = completeQuizService.getCompleteQuizById(quizId);
        return ResponseEntity.ok(completeQuizDTO);
    } catch (QuizNotFoundException e) {
        logger.error("Quiz not found: {}", quizId, e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Quiz not found", "message", e.getMessage()));
    } catch (Exception e) {
        logger.error("Failed to fetch complete quiz: {}", quizId, e.getMessage(), e);
        // Consider a more general error response here or handle specific exceptions as needed
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An error occurred while fetching the quiz", "message", e.getMessage()));
    }     
}


    /**
     * Updates an existing complete quiz identified by the provided quiz ID with new information
     * contained in the CompleteQuizDTO. This method allows for the modification of a quiz's
     * details, including its title, description, questions, and answers.
     *
     * Access to this method is restricted to authenticated users to ensure that only authorized
     * individuals can make changes to quizzes. The method attempts to update the quiz with the
     * new information; if successful, it returns a confirmation message. If the quiz to be updated
     * is not found, a "Quiz not found" error is returned.
     *
     * @param quizId The UUID of the quiz to be updated.
     * @param completeQuizDTO The DTO containing the updated quiz information.
     * @return A ResponseEntity containing a confirmation message if the update is successful,
     *         a "Quiz not found" error if the quiz does not exist, or an internal server error
     *         if an unexpected error occurs during the update process.
     */
    @Operation(summary = "Update a complete quiz", description = "Updates an existing quiz along with questions and answers.")
    @ApiResponse(responseCode = "200", description = "Complete quiz updated successfully.")
    @ApiResponse(responseCode = "400", description = "Bad request due to invalid input.")
    @ApiResponse(responseCode = "404", description = "Quiz not found.")
    @PutMapping("/{quizId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateCompleteQuiz(@PathVariable UUID quizId, @RequestBody CompleteQuizDTO completeQuizDTO) {
        try {
            completeQuizService.updateCompleteQuiz(quizId, completeQuizDTO);
            return ResponseEntity.ok().body(Map.of("message", "Complete quiz updated successfully."));
        } catch (QuizNotFoundException e) {
            logger.error("Quiz not found for update: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Quiz not found for update", "message", e.getMessage()));
        } catch (Exception e) {
            logger.error("Failed to update complete quiz: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to update complete quiz", "message", e.getMessage()));
        }
    }

    /**
     * Deletes an existing quiz along with all its associated questions and answers based on the
     * provided quiz ID. This operation is irreversible and removes the quiz entirely from the
     * system.
     *
     * Only authenticated users are allowed to perform this operation, adding a layer
     * of security to ensure that only users with the necessary permissions can delete quizzes.
     * If the quiz is successfully deleted, a NO_CONTENT status is returned. If the quiz to be
     * deleted is not found, a "Quiz not found" error is returned.
     *
     * @param quizId The UUID of the quiz to be deleted.
     * @return A ResponseEntity with NO_CONTENT status if the quiz is successfully deleted,
     *         or a "Quiz not found" error if the quiz does not exist.
     */

    @Operation(summary = "Delete a complete quiz", description = "Deletes an existing quiz along with questions and answers.")
    @ApiResponse(responseCode = "204", description = "Complete quiz deleted successfully.")
    @ApiResponse(responseCode = "404", description = "Quiz not found.")
    @DeleteMapping("/{quizId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteCompleteQuiz(@PathVariable UUID quizId) {
        try {
            completeQuizService.deleteCompleteQuiz(quizId);
            return ResponseEntity.noContent().build();
        } catch (QuizNotFoundException e) {
            logger.error("Quiz not found for deletion: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Quiz not found for deletion", "message", e.getMessage()));
        } catch (Exception e) {
            logger.error("Failed to delete complete quiz: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to delete complete quiz", "message", e.getMessage()));
        }
    }

    /**
     * Fetches complete quizzes that are associated with a specific tag. This method allows
     * users to retrieve quizzes based on their content or theme as identified by tags. It
     * returns a list of CompleteQuizDTOs that match the specified tag.
     *
     * This endpoint requires authentication to ensure that quiz retrieval is performed
     * securely. If quizzes with the specified tag are found, they are returned in the response;
     * otherwise, a "Quiz not found" error is returned. This method facilitates the discovery
     * of quizzes based on topics of interest to the user.
     *
     * @param tag The tag used to filter quizzes.
     * @return A ResponseEntity containing a list of CompleteQuizDTOs if quizzes with the
     *         specified tag are found, or a "Quiz not found" error if no such quizzes exist.
     */

    @Operation(summary = "Gets a omplete Quiz by its tag", description = "Fetches a complete quiz with questions and answers by its tag.")
    @ApiResponse(responseCode = "200", description = "Complete quiz fetched successfully.")
    @ApiResponse(responseCode = "404", description = "Quiz not found.")
    @GetMapping("/tag/{tag}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getCompleteQuizByTag(@PathVariable String tag) {
        try {
            List<CompleteQuizDTO> completeQuizDTO = completeQuizService.getCompleteQuizzesByTag(tag);
            return ResponseEntity.ok(completeQuizDTO);
        } catch (QuizNotFoundException e) {
            logger.error("Quiz not found: {}", tag, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Quiz not found", "message", e.getMessage()));
        } catch (Exception e) {
            logger.error("Failed to fetch complete quiz: {}", tag, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An error occurred while fetching the quiz", "message", e.getMessage()));
        }
    }
}
