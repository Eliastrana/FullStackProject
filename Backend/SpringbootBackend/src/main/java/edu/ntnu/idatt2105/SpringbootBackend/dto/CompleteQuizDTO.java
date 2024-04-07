package edu.ntnu.idatt2105.SpringbootBackend.dto;

import edu.ntnu.idatt2105.SpringbootBackend.model.Difficulty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for representing a complete quiz, including its questions and associated metadata.")
public class CompleteQuizDTO {
    
    @Schema(description = "The title of the quiz.", example = "Sport Quiz")
    private String title;
    
    @Schema(description = "A detailed description of the quiz.", example = "A quiz covering various topics in sports.")
    private String description;
    
    @Schema(description = "The unique identifier of the creator of the quiz.", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    private UUID creatorId;

    @Schema(description = "The unique identifier of the category under which the quiz falls.", example = "23e4567-e89b-12d3-a456-426614174000")
    private UUID categoryId;

    @Schema(description = "The difficulty level of the quiz.", example = "EASY")
    private Difficulty difficulty;

    @Schema(description = "A boolean value indicating whether the quiz is public or private.", example = "true")
    private Boolean isPublic;
    
    @Schema(description = "A set of questions included in the quiz.")
    private Set<CompleteQuestionDTO> questions;

    // Image fields
    @Schema(description = "The name of the image file associated with the quiz.", example = "quiz_cover.png")
    private String imageName;
    
    @Schema(description = "The type of the image file.", example = "png")
    private String imageType;
    
    @Schema(description = "The base64 encoded string of the image data.", example = "iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAABjElEQVRIS+2Vv0oDQRDGv7+9g")
    private String imageData;
}
