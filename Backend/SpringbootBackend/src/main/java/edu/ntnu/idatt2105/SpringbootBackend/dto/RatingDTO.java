package edu.ntnu.idatt2105.SpringbootBackend.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Schema(description = "Rating DTO for creating and displaying rating information")
@Data
@Builder
@AllArgsConstructor
public class RatingDTO {
  @Schema(required = true, example = "123e4567-e89b-12d3-a456-426614174000", description = "Unique identifier of the user")
  private UUID userId;

  @Schema(required = true, example = "123e4567-e89b-12d3-a456-426614174000", description = "Unique identifier of the quiz")
  private UUID quizId;

  @Schema(required = true, example = "5", description = "Rating of the quiz")
  private int rating;
}

