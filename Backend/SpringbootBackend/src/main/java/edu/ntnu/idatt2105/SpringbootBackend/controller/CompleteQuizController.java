package edu.ntnu.idatt2105.SpringbootBackend.controller;

import edu.ntnu.idatt2105.SpringbootBackend.dto.CompleteQuizDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.QuizNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.QuestionNotFoundException;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

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

    @Operation(summary = "Create a complete quiz", description = "Creates a new quiz along with questions and answers.")
    @ApiResponse(responseCode = "201", description = "Complete quiz created successfully.")
    @ApiResponse(responseCode = "400", description = "Bad request due to invalid input.")
    @ApiResponse(responseCode = "403", description = "Forbidden - user not authorized to perform this action.")
   @PostMapping
    @PreAuthorize("isAuthenticated()")
    public void createCompleteQuiz(@RequestBody CompleteQuizDTO completeQuizDTO) {
        try {
            completeQuizService.createCompleteQuiz(completeQuizDTO);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(completeQuizDTO.getId()).toUri();
            ResponseEntity.created(location).build();
        } catch (Exception e) {
            logger.error("Failed to create complete quiz: {}", e.getMessage(), e);
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to create complete quiz", "message", e.getMessage()));
        }
    }


    @Operation(summary = "Get a complete quiz", description = "Fetches a complete quiz with questions and answers.")
    @ApiResponse(responseCode = "200", description = "Complete quiz fetched successfully.")
    @ApiResponse(responseCode = "404", description = "Quiz not found.")
    @GetMapping("/{quizId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getCompleteQuiz(@PathVariable UUID quizId) {
        try {
            CompleteQuizDTO completeQuizDTO = completeQuizService.getCompleteQuizById(quizId);
            return ResponseEntity.ok(completeQuizDTO);

        } catch (QuestionNotFoundException | QuizNotFoundException e) {
            logger.error("Failed to fetch complete quiz: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the quiz.");
        }     
    }

    @Operation(summary = "Update a complete quiz", description = "Updates an existing quiz along with questions and answers.")
    @ApiResponse(responseCode = "200", description = "Complete quiz updated successfully.")
    @ApiResponse(responseCode = "400", description = "Bad request due to invalid input.")
    @ApiResponse(responseCode = "404", description = "Quiz not found.")
    @PutMapping("/{quizId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateCompleteQuiz(@PathVariable UUID quizId, @RequestBody CompleteQuizDTO completeQuizDTO) {
        try {
            completeQuizService.updateCompleteQuiz(quizId, completeQuizDTO);
            return ResponseEntity.ok("Complete quiz updated successfully.");
        } catch (Exception e) {
            logger.error("Failed to update complete quiz: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Failed to update complete quiz: " + e.getMessage());
        }
    }

    @Operation(summary = "Delete a complete quiz", description = "Deletes an existing quiz along with questions and answers.")
    @ApiResponse(responseCode = "204", description = "Complete quiz deleted successfully.")
    @ApiResponse(responseCode = "404", description = "Quiz not found.")
    @DeleteMapping("/{quizId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteCompleteQuiz(@PathVariable UUID quizId) {
        try {
            completeQuizService.deleteCompleteQuiz(quizId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Failed to delete complete quiz: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Failed to delete complete quiz: " + e.getMessage());
        }
    }
}
