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

@Tag(name = "Difficulty Management", description = "Handles quiz difficulty levels")
@RestController
@RequestMapping("/api/difficulties")
public class DifficultyController {

    @GetMapping("/")
    @Operation(summary = "List all difficulties", description = "Retrieves a list of all possible quiz difficulty levels.")
    public ResponseEntity<List<Difficulty>> getAllDifficulties() {
        List<Difficulty> difficulties = Arrays.stream(Difficulty.values())
                .collect(Collectors.toList());
        return ResponseEntity.ok(difficulties);
    }
}