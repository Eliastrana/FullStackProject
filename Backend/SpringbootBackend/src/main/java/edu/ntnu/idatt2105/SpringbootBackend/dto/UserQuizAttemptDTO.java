package edu.ntnu.idatt2105.SpringbootBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The UserQuizAttemptDTO class is a Data Transfer Object that encapsulates the details of a user's attempt at a quiz.
 * It includes information such as the unique identifiers for the attempt, the user, and the quiz, as well as the user's
 * score and the status of the attempt. This class facilitates the transfer of user quiz attempt data between the client
 * and the server, enabling the tracking and analysis of quiz attempts within the application.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for a UserQuizAttempt. Represents a user's attempt at a quiz.")
public class UserQuizAttemptDTO {
    @Schema(description = "Unique identifier for the user's quiz attempt. This is typically a UUID.", 
            example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;
    @Schema(description = "Unique identifier for the user who attempted the quiz. This is typically a UUID.", 
            example = "123e4567-e89b-12d3-a456-426614174000") 
    private UUID userId;
    @Schema(description = "Unique identifier for the quiz that the user attempted. This is typically a UUID.", 
            example = "123e4567-e89b-12d3-a456-426614174000") 
    private UUID quizId;
    @Schema(description = "The score achieved by the user in the quiz attempt.", 
            example = "8") 
    private int score;
    @Schema(description = "The status of the quiz attempt, which can be 'completed', 'in progress', or 'not started'.", 
            example = "completed") 
    private String status; 
}
