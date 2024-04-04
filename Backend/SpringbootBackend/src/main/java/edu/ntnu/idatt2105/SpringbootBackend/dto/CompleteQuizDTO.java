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
    
    @Schema(description = "The title of the quiz.", example = "General Science Quiz")
    private String title;
    
    @Schema(description = "A detailed description of the quiz.", example = "A quiz covering various topics in science.")
    private String description;
    
    @Schema(description = "The unique identifier of the creator of the quiz.", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    private UUID creatorId;
    
    @Schema(description = "The name of the category under which the quiz falls.", example = "Science")
    private String categoryName;
    
    @Schema(description = "The difficulty level of the quiz.", example = "EASY")
    private Difficulty difficulty;
    
    @Schema(description = "A set of questions included in the quiz.")
    private Set<CompleteQuestionDTO> questions;

    // Image fields
    @Schema(description = "The name of the image file associated with the quiz.", example = "quiz_cover.png")
    private String imageName;
    
    @Schema(description = "The type of the image file.", example = "image/png")
    private String imageType;
    
    @Schema(description = "The base64 encoded string of the image data.", example = "data:image/png;base64,iVBORw0KG...")
    private String imageData;
}
<<<<<<< HEAD
=======
// 1b40d2c6-91d6-4e01-b247-ef2c10a879d4
>>>>>>> c765c60 (fix: :bug: Changed the mapping in questionDTO to include answers.)
