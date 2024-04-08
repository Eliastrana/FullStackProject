package edu.ntnu.idatt2105.SpringbootBackend.controller;

import edu.ntnu.idatt2105.SpringbootBackend.model.Difficulty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code DifficultyController} class is responsible for managing the difficulty levels
 * of quizzes within the system. It allows for the retrieval of all predefined difficulty
 * levels that can be assigned to a quiz.
 *
 * @author Vegard johnsen, Sander R. Skofsrud
 * @version 1.0
 * @since 1.0
 * @see Difficulty
 */
@Tag(name = "Difficulty Management", description = "Handles quiz difficulty levels")
@RestController
@RequestMapping("/api/difficulties")
public class DifficultyController {

    @Operation(summary = "List all difficulties", description = "Retrieves a list of all possible quiz difficulty levels.", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of difficulties", 
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Difficulty.class))))})
    @GetMapping
    public ResponseEntity<List<Difficulty>> getAllDifficulties() {
        List<Difficulty> difficulties = Arrays.stream(Difficulty.values())
                .collect(Collectors.toList());
        return ResponseEntity.ok(difficulties);
    }
}