package edu.ntnu.idatt2105.SpringbootBackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

import edu.ntnu.idatt2105.SpringbootBackend.model.Difficulty;

@Schema(description = "Quiz Create DTO for creating new quizzes")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizCreateDTO {

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    @Schema(required = true, example = "General Knowledge", description = "Title of the new quiz")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 1, max = 1000, message = "Description must be between 1 and 1000 characters")
    @Schema(required = true, example = "A quiz covering a wide range of topics.", description = "Description of the new quiz")
    private String description;

    @NotBlank(message = "Difficulty is required")
    @Schema(required = true, example = "EASY", description = "Difficulty of the new quiz")
    private Difficulty difficulty;

    @NotBlank(message = "isPublic is required")
    @Schema(required = true, example = "true", description = "Boolean value indicating whether the new quiz is public or private")
    private boolean isPublic;

    @NotBlank(message = "CreatorId is required")
    @Schema(required = true, example = "123e4567-e89b-12d3-a456-426614174000", description = "Unique identifier of the quiz creator")
    private UUID creatorId;

    @NotBlank(message = "CategoryId is required")
    @Schema(description = "CategoryId of the quiz", example = "23e4567-e89b-12d3-a456-426614174000")
    private UUID categoryId;


    @Schema(description = "List of questions for the new quiz", 
    example = "[{\"text\":\"What is the capital of Norway?\",\"questionType\":\"MULTIPLE_CHOICE\",\"answers\":[{\"text\":\"Oslo\",\"isCorrect\":true},{\"text\":\"Stockholm\",\"isCorrect\":false},{\"text\":\"Copenhagen\",\"isCorrect\":false},{\"text\":\"Helsinki\",\"isCorrect\":false}]}]")
    private List<QuestionCreateDTO> questions;

    @Schema(description = "ImageId of the quiz")
    private UUID imageId;
}
