package edu.ntnu.idatt2105.SpringbootBackend.dto;

import edu.ntnu.idatt2105.SpringbootBackend.model.Difficulty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

/**
 * Represents a Data Transfer Object (DTO) for encapsulating the comprehensive details of a quiz.
 * It includes metadata such as the title, description, category, and difficulty level,
 * as well as the list of complete questions making up the quiz. Additionally, it provides fields
 * for managing quiz visibility (public or private) and associated imagery, enabling a rich
 * representation of the quiz content.
 *
 * @author Vegard Johnsen
 * @version 0.1
 * @since 0.1
 * @see CompleteQuestionDTO
 * @see Difficulty
 * @see CompleteQuizDTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for representing a complete quiz, including its questions and associated metadata.")
public class CompleteQuizDTO {
    /**
     * The title of the quiz, serving as a brief introduction or summary of the quiz's theme or subject matter.
     */

    @Schema(description = "The title of the quiz.", example = "Sport Quiz")
    private String title;

    /**
     * A detailed description providing further information about the quiz, its purpose, coverage,
     * and any other relevant details that might help users understand what the quiz entails.
     */
    @Schema(description = "A detailed description of the quiz.", example = "A quiz covering various topics in sports.")
    private String description;

    /**
     * The unique identifier of the user who created the quiz. This links the quiz to its creator,
     * enabling features like quiz management and personalization.
     */
    @Schema(description = "The unique identifier of the creator of the quiz.", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    private UUID creatorId;

    /**
     * The category name under which the quiz is classified. Categories help organize quizzes into themes
     * or subject areas, making it easier for users to find quizzes that match their interests.
     */
    @Schema(description = "The unique identifier of the category under which the quiz falls.", example = "23e4567-e89b-12d3-a456-426614174000")
    private UUID categoryId;

    /**
     * The difficulty level of the quiz, indicating the level of challenge users can expect.
     * It helps users select quizzes that match their skill or knowledge level.
     */
    @Schema(description = "The difficulty level of the quiz.", example = "EASY")
    private Difficulty difficulty;

    /**
     * A boolean value indicating the visibility of the quiz. Public quizzes are accessible to all users,
     * while private quizzes are restricted to specific users or groups.
     */
    @Schema(description = "A boolean value indicating whether the quiz is public or private.", example = "true")
    private Boolean isPublic;

    /**
     * The set of questions included in the quiz. Each question is detailed further in its own DTO,
     * including possible answers, tags, and associated media, providing a comprehensive view of the quiz content.
     */
    @Schema(description = "A set of questions included in the quiz.")
    private Set<CompleteQuestionDTO> questions;

    /**
     * Information related to the image associated with the quiz, including the file name, type,
     * and the actual image data encoded in base64. This allows for the inclusion of visual elements
     * in the quiz presentation.
     */
    @Schema(description = "The name of the image file associated with the quiz.", example = "quiz_cover.png")
    private String imageName;

    @Schema(description = "The type of the image file.", example = "png")
    private String imageType;
    
    @Schema(description = "The base64 encoded string of the image data.", example = "iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAABjElEQVRIS+2Vv0oDQRDGv7+9g")
    private String imageData;
}
