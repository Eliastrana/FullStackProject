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
