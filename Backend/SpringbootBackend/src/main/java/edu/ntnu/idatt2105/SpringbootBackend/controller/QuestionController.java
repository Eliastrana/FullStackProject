package edu.ntnu.idatt2105.SpringbootBackend.controller;

import edu.ntnu.idatt2105.SpringbootBackend.dto.QuestionCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuestionDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.QuestionNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.QuizNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Create a new question", description = "Create a new question for a specific quiz")
    @ApiResponse(responseCode = "201", description = "Question created", content = @Content(schema = @Schema(implementation = QuestionDTO.class)))
    @ApiResponse(responseCode = "404", description = "Quiz not found")
    @ApiResponse(responseCode = "400", description = "Bad request")
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

    @Operation(summary = "Get question by id", description = "Get a question by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Question found", content = @Content(schema = @Schema(implementation = QuestionDTO.class)))
    @ApiResponse(responseCode = "404", description = "Question not found")
    @ApiResponse(responseCode = "400", description = "Bad request")
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

    @Operation(summary = "Update question", description = "Update a question by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Question updated", content = @Content(schema = @Schema(implementation = QuestionDTO.class)))
    @ApiResponse(responseCode = "404", description = "Question not found")
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

    @Operation(summary = "Delete question", description = "Delete a question by its unique identifier")
    @ApiResponse(responseCode = "204", description = "Question deleted")
    @ApiResponse(responseCode = "404", description = "Question not found")
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

    @Operation(summary = "Get questions by quiz id", description = "Get all questions for a specific quiz")
    @ApiResponse(responseCode = "200", description = "Questions found", content = @Content(schema = @Schema(implementation = QuestionDTO.class)))
    @ApiResponse(responseCode = "404", description = "Questions not found")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByQuizId(@PathVariable UUID quizId) {
        List<QuestionDTO> questions = questionService.getQuestionsByQuizId(quizId);
        return ResponseEntity.ok(questions);
    }
}
