package edu.ntnu.idatt2105.SpringbootBackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

import edu.ntnu.idatt2105.SpringbootBackend.model.Difficulty;

/**
 * QuizDTO is a Data Transfer Object designed to convey detailed quiz information from the server to the client.
 * It encapsulates key attributes of a quiz, such as its unique identifier, title, description, difficulty level,
 * visibility status, category, creator's identity, and an optional image. This structured representation
 * facilitates the efficient transmission of quiz details necessary for display, categorization, and user interaction
 * within a quiz-oriented application context.
 *
 * @author Vegard Johnsen
 * @version 0.1
 * @since 0.1
 * @see Difficulty
 */
@Schema(description = "Quiz DTO for displaying quiz information")
@Data
@Builder
public class QuizDTO {

    /**
     * The unique identifier of the quiz, ensuring precise identification and retrieval of quiz details
     * across the application infrastructure.
     */
    @Schema(required = true, example = "123e4567-e89b-12d3-a456-426614174000", description = "Unique identifier of the quiz")
    private UUID id;

    /**
     * The title of the quiz, providing a succinct and informative overview of the quiz's content or theme,
     * and serving as a primary reference for users selecting quizzes.
     */
    @Schema(required = true, example = "General Knowledge", description = "Title of the quiz")
    private String title;

    /**
     * A comprehensive description of the quiz, outlining its scope, topics covered, and any other relevant
     * information that aids users in understanding the quiz's focus and objectives.
     */
    @Schema(required = true, example = "A quiz covering a wide range of topics.", description = "Description of the quiz")
    private String description;

    /**
     * The difficulty level of the quiz, categorized to align with user expertise and preference,
     * thereby facilitating a tailored quiz-taking experience.
     */
    @Schema(required = true, example = "EASY", description = "Difficulty of the quiz")
    private Difficulty difficulty;

    /**
     * A flag indicating the quiz's accessibility, where a public status allows for broad user engagement,
     * whereas a private setting restricts access to specific user groups or contexts.
     */
    @Schema(required = true, example = "true", description = "Boolean value indicating whether the quiz is public or private")
    private Boolean isPublic;

    /**
     * The unique identifier for the category under which the quiz is classified, aiding in the organization
     * and thematic grouping of quizzes within the platform.
     */
    @Schema(example = "23e4567-e89b-12d3-a456-426614174000",description = "CategoryId of the quiz")
    private UUID categoryId;

    /**
     * The unique identifier of the individual or entity responsible for creating the quiz, ensuring
     * accountability and facilitating potential queries or actions related to quiz management.
     */
    @Schema(required = true, example = "123e4567-e89b-12d3-a456-426614174000", description = "Unique identifier of the quiz creator")
    private UUID creatorId;

    /**
     * An optional unique identifier for an associated image, providing a visual element to complement
     * and enhance the quiz presentation.
     */
    @Schema(description = "ImageId of the quiz")
    private UUID imageId;
}
