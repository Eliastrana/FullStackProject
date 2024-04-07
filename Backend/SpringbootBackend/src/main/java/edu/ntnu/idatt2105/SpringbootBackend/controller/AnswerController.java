package edu.ntnu.idatt2105.SpringbootBackend.controller;

import edu.ntnu.idatt2105.SpringbootBackend.dto.AnswerDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.AnswerNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.QuestionNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * The {@code AnswerController} class provides RESTful endpoints for managing answers
 * within the quiz system. It supports operations such as creating, retrieving,
 * updating, and deleting answers. This controller ensures that answers are properly
 * managed in relation to their associated questions.
 *
 * @see AnswerService for the service layer used by this controller
 * @see AnswerDTO for the data transfer object used in answer operations
 * @author Vegard Johnsen, sander rom skofsrud
 * @version 0.1
 * @since 0.1
 */

@Tag(name = "Answer Management")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    /**
     * Creates a new answer for a specific question identified by its UUID.
     * This method takes an answer DTO containing the answer details and associates it
     * with a question based on the provided questionId. If the question is found,
     * the answer is created and saved, returning the created answer DTO.
     *
     * @param questionId The UUID of the question to which the answer belongs.
     * @param answerDTO The {@link AnswerDTO} containing the details of the answer.
     * @return A {@link ResponseEntity<AnswerDTO>} with the created answer or an error status.
     */
    @Operation(summary = "Create a new answer", description = "Create a new answer for a specific question")
    @ApiResponse(responseCode = "201", description = "Answer created successfully", content = @Content(schema = @Schema(implementation = AnswerDTO.class)))
    @ApiResponse(responseCode = "404", description = "Question not found")
    @PostMapping("/{questionId}")
    public ResponseEntity<AnswerDTO> createAnswer(@PathVariable UUID questionId, @RequestBody AnswerDTO answerDTO) {
        try {
            AnswerDTO createdAnswer = answerService.createAnswer(questionId, answerDTO);
            return new ResponseEntity<>(createdAnswer, HttpStatus.CREATED);
        } catch (QuestionNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves an answer by its unique identifier (UUID).
     * This method searches for an answer based on the provided answerId. If found,
     * it returns the answer DTO; otherwise, it returns NOT_FOUND status.
     *
     * @param answerId the UUID of the answer to retrieve
     * @return a {@link ResponseEntity} containing the {@link AnswerDTO} if found,
     * or NOT_FOUND if the answer does not exist
     */
    @Operation(summary = "Get an answer by ID", description = "Retrieves an answer by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Answer retrieved successfully", content = @Content(schema = @Schema(implementation = AnswerDTO.class)))
    @ApiResponse(responseCode = "404", description = "Answer not found")
    @GetMapping("/{answerId}")
    public ResponseEntity<AnswerDTO> getAnswerById(@PathVariable UUID answerId) {
        try {
            AnswerDTO answerDTO = answerService.getAnswerById(answerId);
            return ResponseEntity.ok(answerDTO);
        } catch (AnswerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates an existing answer identified by its UUID with new information.
     * This method allows for the modification of an answer's details. If the answer
     * is found, it is updated with the provided answer DTO; otherwise, NOT_FOUND status is returned.
     *
     * @param answerId the UUID of the answer to update
     * @param answerDTO the new answer details to apply
     * @return a {@link ResponseEntity} containing the updated {@link AnswerDTO} if the answer exists,
     * or NOT_FOUND if the answer does not exist
     */
    @Operation(summary = "Update an answer", description = "Updates an existing answer with new information")
    @ApiResponse(responseCode = "200", description = "Answer updated successfully", content = @Content(schema = @Schema(implementation = AnswerDTO.class)))
    @ApiResponse(responseCode = "404", description = "Answer not found")
    @PutMapping("/{answerId}")
    public ResponseEntity<AnswerDTO> updateAnswer(@PathVariable UUID answerId, @RequestBody AnswerDTO answerDTO) {
        try {
            AnswerDTO updatedAnswer = answerService.updateAnswer(answerId, answerDTO);
            return ResponseEntity.ok(updatedAnswer);
        } catch (AnswerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes an answer identified by its UUID.
     * This method removes an answer from the database based on the provided answerId.
     * If the answer is found and successfully deleted, a NO_CONTENT status is returned;
     * otherwise, NOT_FOUND is returned if the answer does not exist.
     *
     * @param answerId the UUID of the answer to delete
     * @return a {@link ResponseEntity} with NO_CONTENT status if the answer is successfully deleted,
     * or NOT_FOUND if the answer does not exist
     */
    @Operation(summary = "Delete an answer", description = "Deletes an answer by its unique identifier")
    @ApiResponse(responseCode = "204", description = "Answer deleted successfully")
    @ApiResponse(responseCode = "404", description = "Answer not found")
    @DeleteMapping("/{answerId}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable UUID answerId) {
        try {
            answerService.deleteAnswer(answerId);
            return ResponseEntity.noContent().build();
        } catch (AnswerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
