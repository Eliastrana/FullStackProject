package edu.ntnu.idatt2105.SpringbootBackend.controller;

import edu.ntnu.idatt2105.SpringbootBackend.dto.QuizCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuizDTO;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.service.QuizService;
import edu.ntnu.idatt2105.SpringbootBackend.exception.QuizNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;


/**
 * The {@code QuizController} class handles HTTP requests related to quiz management.
 * It supports creating, retrieving, updating, and deleting quizzes. This class
 * interacts with {@link QuizService} for quiz operations and {@link UserService}
 * for retrieving user details.
 *
 * @see QuizService
 * @see UserService
 * @see QuizDTO
 * @see QuizCreateDTO
 */
@Tag(name = "Quiz Management")
@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quiz")
public class QuizController {
    private final UserService userService;
    private final QuizService quizService;
    private final Logger logger = LoggerFactory.getLogger(QuizController.class);

    /**
     * Creates a new quiz with details provided in {@link QuizCreateDTO}. The quiz
     * is associated with the currently authenticated user. Upon successful creation,
     * returns the created quiz details as {@link QuizDTO}.
     *
     * @param quizCreateDTO The quiz creation details.
     * @return A {@link ResponseEntity} containing the created {@link QuizDTO}.
     */
    @Operation(summary = "Create a new quiz", description = "Creates a new quiz with the provided details")
    @ApiResponse(responseCode = "200", description = "Successfully created the quiz")
    @ApiResponse(responseCode = "400", description = "Error creating the quiz")
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<QuizDTO> createQuiz(@RequestBody QuizCreateDTO quizCreateDTO) {
        logger.info("Creating new quiz with title: " + quizCreateDTO.getTitle());
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            User user = userService.loadUserByUsername(username);
            quizCreateDTO.setCreatorId(user.getId());

            QuizDTO createdQuiz = quizService.createQuiz(quizCreateDTO);
            return ResponseEntity.ok(createdQuiz);
        } catch (Exception e) {
            logger.error("Error creating quiz: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }


    /**
     * Retrieves all available quizzes. This method queries all quizzes without any filters
     * and returns a list of {@link QuizDTO} representing each quiz.
     *
     * @return A {@link ResponseEntity} containing a list of all {@link QuizDTO}.
     */
    @Operation(summary = "Fetch all quizzes", description = "Retrieves all available quizzes")
    @ApiResponse(responseCode = "200", description = "Successfully fetched quizzes")
    @GetMapping
    public ResponseEntity<List<QuizDTO>> getAllQuizzes() {
        try {
            List<QuizDTO> quizzes = quizService.getAllQuizzes();
            return ResponseEntity.ok(quizzes);
        } catch (Exception e) {
            logger.error("Error fetching quizzes: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Retrieves a specific quiz by its unique identifier ({@code id}). If the quiz is found,
     * its details are returned as {@link QuizDTO}; otherwise, a not found status is returned.
     *
     * @param id The unique identifier of the quiz to retrieve.
     * @return A {@link ResponseEntity} with the {@link QuizDTO} if found, or a not found status.
     */
    @Operation(summary = "Fetch a specific quiz", description = "Retrieves a quiz by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Successfully fetched the quiz")
    @ApiResponse(responseCode = "404", description = "Quiz not found")
    @GetMapping("/{id}")
    public ResponseEntity<QuizDTO> getQuizById(@PathVariable UUID id) {
        logger.info("Fetching quiz with ID: " + id);
        try {
            QuizDTO quiz = quizService.getQuizById(id);
            if (quiz != null) {
                return ResponseEntity.ok(quiz);
            } else {
                logger.error("Quiz with ID: " + id + " not found.");
                return ResponseEntity.notFound().build();
            }
        } catch (QuizNotFoundException e) {
            logger.error("Error fetching quiz with ID: " + id + ": " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            logger.error("Error fetching quiz with ID: " + id + ": " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Updates an existing quiz identified by its unique identifier ({@code id}). The updated
     * quiz details are provided in a {@link QuizDTO}. If the quiz is found, it is updated with
     * the new details and the updated {@link QuizDTO} is returned; otherwise, a not found status
     * is returned.
     *
     * @param id The unique identifier of the quiz to update.
     * @param quizUpdateDTO The updated quiz details.
     * @return A {@link ResponseEntity} with the updated {@link QuizDTO} if found, or a not found status.
     */
    @Operation(summary = "Update an existing quiz", description = "Updates quiz details by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Successfully updated the quiz")
    @ApiResponse(responseCode = "404", description = "Quiz not found")
    @ApiResponse(responseCode = "400", description = "Error updating the quiz")
    @PutMapping("/{id}")
    public ResponseEntity<QuizDTO> updateQuiz(@PathVariable UUID id, @RequestBody QuizDTO quizUpdateDTO) {
        logger.info("Updating quiz with ID: " + id);
        try {
            QuizDTO updatedQuiz = quizService.updateQuiz(id, quizUpdateDTO);
            return ResponseEntity.ok(updatedQuiz);
        } catch (QuizNotFoundException e) {
            logger.error("Quiz with ID: " + id + " not found.");
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error updating quiz with ID: " + id + ": " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Deletes a quiz by its unique identifier ({@code id}). If the quiz is found, it is deleted
     * and a no content status is returned; otherwise, a not found status is returned.
     *
     * @param id The unique identifier of the quiz to delete.
     * @return A {@link ResponseEntity} with a no content status if the quiz is successfully deleted,
     * or a not found status if the quiz is not found.
     */
    @Operation(summary = "Delete a quiz", description = "Deletes a quiz by its unique identifier")
    @ApiResponse(responseCode = "204", description = "Successfully deleted the quiz")
    @ApiResponse(responseCode = "404", description = "Quiz not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable UUID id) {
        logger.info("Deleting quiz with ID: " + id);
        try {
            quizService.deleteQuiz(id);
            return ResponseEntity.noContent().build();
        } catch (QuizNotFoundException e) {
            logger.error("Quiz with ID: " + id + " not found.");
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error deleting quiz with ID: " + id + ": " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
