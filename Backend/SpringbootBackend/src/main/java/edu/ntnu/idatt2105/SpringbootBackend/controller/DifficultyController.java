package edu.ntnu.idatt2105.SpringbootBackend.controller;

import edu.ntnu.idatt2105.SpringbootBackend.model.Difficulty;
import io.swagger.v3.oas.annotations.Operation;
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

    /**
     * Retrieves a list of all possible {@link Difficulty} levels defined in the system.
     * This endpoint allows clients to understand what difficulty levels can be associated
     * with quizzes and therefore, can be used to filter or categorize quizzes based on their
     * difficulty.
     *
     * @return A {@link ResponseEntity} object containing a list of all {@link Difficulty} levels.
     * The HTTP status code is set to OK (200) indicating the request was successfully processed.
     */
    @GetMapping("/")
    @Operation(summary = "List all difficulties", description = "Retrieves a list of all possible quiz difficulty levels.")
    public ResponseEntity<List<Difficulty>> getAllDifficulties() {
        List<Difficulty> difficulties = Arrays.stream(Difficulty.values())
                .collect(Collectors.toList());
        return ResponseEntity.ok(difficulties);
    }
}
