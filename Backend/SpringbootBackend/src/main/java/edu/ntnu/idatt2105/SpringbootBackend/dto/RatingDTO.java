package edu.ntnu.idatt2105.SpringbootBackend.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
/**
 * Represents a Data Transfer Object (DTO) for a Rating, which is used to rate quizzes and provide feedback on their quality.
 * Ratings help users identify high-quality quizzes and provide creators with valuable insights into their quiz's reception.
 * This DTO structure is utilized for operations involving the creation, retrieval, and updating of rating information.
 * 
 * @author Sander R. Skofsrud
 * @version 0.1
 * @since 0.1
 
 */
@Schema(description = "Rating DTO for creating and displaying rating information")
@Data
@Builder
@AllArgsConstructor
public class RatingDTO {
  /**
   * The unique identifier of the user who rated the quiz. This ID ensures the uniqueness of each user across the system, serving as a key for user-related operations.
   */
  @Schema(required = true, example = "123e4567-e89b-12d3-a456-426614174000", description = "Unique identifier of the user")
  private UUID userId;

  /**
   * The unique identifier of the quiz that was rated. This ID ensures the uniqueness of each quiz across the system, serving as a key for quiz-related operations.
   */ 

  @Schema(required = true, example = "123e4567-e89b-12d3-a456-426614174000", description = "Unique identifier of the quiz")
  private UUID quizId;

  /**
   * The rating given to the quiz by the user. This value represents the user's evaluation of the quiz's quality, with higher ratings indicating better quizzes.
   */
  @Schema(required = true, example = "5", description = "Rating of the quiz")
  private int rating;
}

