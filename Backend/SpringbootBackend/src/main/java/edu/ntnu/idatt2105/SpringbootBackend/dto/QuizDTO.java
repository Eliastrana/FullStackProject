package edu.ntnu.idatt2105.SpringbootBackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Schema(description = "Quiz DTO for displaying quiz information")
@Data
@Builder
public class QuizDTO {
    @Schema(required = true, example = "123e4567-e89b-12d3-a456-426614174000", description = "Unique identifier of the quiz")
    private UUID id;

    @Schema(required = true, example = "General Knowledge", description = "Title of the quiz")
    private String title;

    @Schema(required = true, example = "A quiz covering a wide range of topics.", description = "Description of the quiz")
    private String description;

    @Schema(description = "CategoryId of the quiz")
    private UUID categoryId;

    @Schema(required = true, example = "123e4567-e89b-12d3-a456-426614174000", description = "Unique identifier of the quiz creator")
    private UUID creatorId;
}
