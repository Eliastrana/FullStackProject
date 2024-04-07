package edu.ntnu.idatt2105.SpringbootBackend.controller;

import edu.ntnu.idatt2105.SpringbootBackend.dto.RatingDTO;
import edu.ntnu.idatt2105.SpringbootBackend.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Rating Management", description = "API for rating management")
@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/ratings")
public class RatingController {

  private final RatingService ratingService;

  @Operation(summary = "Save or update a rating", description = "Saves a new rating or updates an existing rating for a specific quiz")
  @ApiResponse(responseCode = "200", description = "Rating saved or updated successfully", content = @Content(schema = @Schema(implementation = RatingDTO.class)))
  @ApiResponse(responseCode = "404", description = "Quiz or User not found")
  @PostMapping
  public ResponseEntity<RatingDTO> saveOrUpdateRating(
          @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Rating details to be saved or updated", required = true, content = @Content(schema = @Schema(implementation = RatingDTO.class)))
          @RequestBody RatingDTO ratingDTO) {
    try {
      RatingDTO savedOrUpdatedRating = ratingService.saveOrUpdateRating(ratingDTO.getUserId(), ratingDTO.getQuizId(), ratingDTO.getRating());
      return new ResponseEntity<>(savedOrUpdatedRating, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Operation(summary = "Get average rating for a quiz", description = "Retrieves the average rating of a quiz")
  @ApiResponse(responseCode = "200", description = "Average rating retrieved successfully")
  @GetMapping("/average/{quizId}")
  public ResponseEntity<Double> getAverageRating(
          @Parameter(description = "Unique identifier of the quiz", required = true)
          @PathVariable UUID quizId) {
    double averageRating = ratingService.getAverageRatingForQuiz(quizId);
    return ResponseEntity.ok(averageRating);
  }

  @Operation(summary = "Get all ratings by a user", description = "Lists all quizzes rated by a user along with the scores")
  @ApiResponse(responseCode = "200", description = "Ratings retrieved successfully", content = @Content(schema = @Schema(implementation = RatingDTO.class)))
  @GetMapping("/user/{userId}")
  public ResponseEntity<List<RatingDTO>> getRatingsByUser(
          @Parameter(description = "Unique identifier of the user", required = true)
          @PathVariable UUID userId) {
    List<RatingDTO> ratings = ratingService.getRatingsByUserId(userId);
    return ResponseEntity.ok(ratings);
  }
}
