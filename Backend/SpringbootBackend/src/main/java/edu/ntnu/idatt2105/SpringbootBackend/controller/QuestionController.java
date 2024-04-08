package edu.ntnu.idatt2105.SpringbootBackend.controller;

import edu.ntnu.idatt2105.SpringbootBackend.dto.QuestionCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuestionDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.QuestionNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.QuizNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * The {@code QuestionController} class is responsible for managing the questions
 * associated with quizzes within the system. It allows for the creation, retrieval,
 * updating, and deletion of questions, as well as the retrieval of all questions
 * associated with a specific quiz.
 *
 * @author Vegard Johnsen
 * @version 1.0
 * @since 1.0
 * @see QuestionService
 * @see CategoryController
 */
@Tag(name = "Question Management")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * Creates a new question and associates it with a specified quiz. The question details
     * are provided in the form of a {@link QuestionCreateDTO}. If the associated quiz
     * is found, the question is created and a {@link QuestionDTO} is returned.
     *
     * @param questionCreateDTO The question details for creation.
     * @param quizId            The unique identifier of the quiz to which the question will be associated.
     * @return A {@link ResponseEntity} with the created {@link QuestionDTO} if successful,
     *         or an appropriate error status if not.
     */
    @Operation(summary = "Create a new question", description = "Create a new question for a specific quiz", responses = {
        @ApiResponse(responseCode = "201", description = "Question created", content = @Content(schema = @Schema(implementation = QuestionDTO.class))),
        @ApiResponse(responseCode = "404", description = "Quiz not found"),
        @ApiResponse(responseCode = "400", description = "Bad request")})
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody QuestionCreateDTO questionCreateDTO, @RequestParam UUID quizId) {
        try {
            QuestionDTO createdQuestion = questionService.createQuestion(quizId, questionCreateDTO);
            return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
        } catch (QuizNotFoundException e) {
            logger.error("Error creating question: Quiz not found with id: " + quizId, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error creating question", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves a question by its unique identifier. If the question exists, it returns
     * a {@link QuestionDTO} with the question details.
     *
     * @param id The unique identifier of the question to retrieve.
     * @return A {@link ResponseEntity} with the found {@link QuestionDTO}, or an error status if not found.
     */
    @Operation(summary = "Get question by id", description = "Get a question by its unique identifier", responses = {
        @ApiResponse(responseCode = "200", description = "Question found", content = @Content(schema = @Schema(implementation = QuestionDTO.class))),
        @ApiResponse(responseCode = "404", description = "Question not found"),
        @ApiResponse(responseCode = "400", description = "Bad request")})
    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable UUID id) {
        try {
            QuestionDTO question = questionService.getQuestionById(id);
            return ResponseEntity.ok(question);
        } catch (QuestionNotFoundException e) {
            logger.error("Error fetching question: Question not found with id: " + id, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates an existing question identified by its UUID. The updated question details
     * are provided in a {@link QuestionDTO}. If the question exists, it is updated and
     * the updated {@link QuestionDTO} is returned.
     *
     * @param id          The unique identifier of the question to update.
     * @param questionDTO The updated question details.
     * @return A {@link ResponseEntity} with the updated {@link QuestionDTO}, or an error status if not found.
     */
    @Operation(summary = "Update question", description = "Update a question by its unique identifier", responses = {
        @ApiResponse(responseCode = "200", description = "Question updated", content = @Content(schema = @Schema(implementation = QuestionDTO.class))),
        @ApiResponse(responseCode = "404", description = "Question not found")})
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<QuestionDTO> updateQuestion(@PathVariable UUID id, @RequestBody QuestionDTO questionDTO) {
        try {
            QuestionDTO updatedQuestion = questionService.updateQuestion(id, questionDTO);
            return ResponseEntity.ok(updatedQuestion);
        } catch (QuestionNotFoundException e) {
            logger.error("Error updating question: Question not found with id: " + id, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a question by its unique identifier. If the question exists, it is deleted
     * and a NO_CONTENT status is returned. If the question is not found, a NOT_FOUND status
     * is returned.
     *
     * @param id The unique identifier of the question to delete.
     * @return A {@link ResponseEntity} with NO_CONTENT status if the question is deleted,
     *         or an error status if not found.
     */
    @Operation(summary = "Delete question", description = "Delete a question by its unique identifier", responses = {
        @ApiResponse(responseCode = "204", description = "Question deleted"),
        @ApiResponse(responseCode = "404", description = "Question not found")})
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteQuestion(@PathVariable UUID id) {
        try {
            questionService.deleteQuestion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (QuestionNotFoundException e) {
            logger.error("Error deleting question: Question not found with id: " + id, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

/**
     * Retrieves all questions associated with a specified quiz by its unique identifier.
     * If the quiz exists, a list of {@link QuestionDTO} is returned with all questions
     * for that quiz.
     *
     * @param quizId The unique identifier of the quiz for which questions are being requested.
     * @return A {@link ResponseEntity} with a list of {@link QuestionDTO} if the quiz exists, or an error status if not.
     */
    @Operation(summary = "Get questions by quiz id", description = "Get all questions for a specific quiz", responses = {
    @ApiResponse(responseCode = "200", description = "Questions found", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = QuestionDTO.class)))),
    @ApiResponse(responseCode = "404", description = "Questions not found"),
    @ApiResponse(responseCode = "400", description = "Bad request")})
@GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByQuizId(@PathVariable UUID quizId) {
        List<QuestionDTO> questions = questionService.getQuestionsByQuizId(quizId);
        return ResponseEntity.ok(questions);
    }
}