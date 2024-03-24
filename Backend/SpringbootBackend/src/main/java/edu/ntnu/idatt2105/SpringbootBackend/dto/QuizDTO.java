package edu.ntnu.idatt2105.SpringbootBackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.UUID;

@Schema(description = "Quiz DTO for displaying quiz information")
@Data
public class QuizDTO {
    @Schema(required = true, example = "123e4567-e89b-12d3-a456-426614174000", description = "Unique identifier of the quiz")
    private UUID id;

    @Schema(required = true, example = "General Knowledge", description = "Title of the quiz")
    private String title;

    @Schema(required = true, example = "A quiz covering a wide range of topics.", description = "Description of the quiz")
    private String description;

    @Schema(required = true, example = "123e4567-e89b-12d3-a456-426614174000", description = "Unique identifier of the quiz creator")
    private UUID creatorId;

    public QuizDTO(UUID id, String title, String description, UUID creatorId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creatorId = creatorId;
    }
}
