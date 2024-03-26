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


@Tag(name = "Quiz Management")
@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quiz")
public class QuizController {
    private final UserService userService;
    private final QuizService quizService;
    private final Logger logger = LoggerFactory.getLogger(QuizController.class);

    @Operation(summary = "Create a new quiz", description = "Creates a new quiz with the provided details")
    @ApiResponse(responseCode = "200", description = "Successfully created the quiz")
    @ApiResponse(responseCode = "400", description = "Error creating the quiz")
    @PostMapping("/create")
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


    @Operation(summary = "Fetch all quizzes", description = "Retrieves all available quizzes")
    @ApiResponse(responseCode = "200", description = "Successfully fetched quizzes")
    @GetMapping("/all")
    public ResponseEntity<List<QuizDTO>> getAllQuizzes() {
        try {
            List<QuizDTO> quizzes = quizService.getAllQuizzes();
            return ResponseEntity.ok(quizzes);
        } catch (Exception e) {
            logger.error("Error fetching quizzes: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

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
