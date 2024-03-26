package edu.ntnu.idatt2105.SpringbootBackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.UUID;


@Schema(description = "Quiz Create DTO for creating new quizzes")
@Data
public class QuizCreateDTO {
    
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    @Schema(required = true, example = "General Knowledge", description = "Title of the new quiz")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 1, max = 1000, message = "Description must be between 1 and 1000 characters")
    @Schema(required = true, example = "A quiz covering a wide range of topics.", description = "Description of the new quiz")
    private String description;

    @Schema(required = true, example = "123e4567-e89b-12d3-a456-426614174000", description = "Unique identifier of the quiz creator")
    private UUID creatorId;

    public QuizCreateDTO(String title, String description, UUID creatorId) {
        this.title = title;
        this.description = description;
        this.creatorId = creatorId;
    }
}
