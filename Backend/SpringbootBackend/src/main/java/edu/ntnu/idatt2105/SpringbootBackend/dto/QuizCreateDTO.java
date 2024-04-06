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

/**
 * A Data Transfer Object designed for the creation of new quizzes. It encompasses all necessary
 * information required to initialize a quiz, including its title, description, level of difficulty,
 * public/private visibility status, the creator's unique identifier, category association, a collection
 * of questions, and an optional image identifier for visual representation. This structured format ensures
 * that all critical aspects of quiz creation are covered.
 */
@Schema(description = "Quiz Create DTO for creating new quizzes")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizCreateDTO {

    /**
     * The title of the quiz. It's a concise representation of the quiz's theme or subject matter,
     * and it must be between 1 and 255 characters in length to ensure clarity and conciseness.
     */
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    @Schema(required = true, example = "General Knowledge", description = "Title of the new quiz")
    private String title;

    /**
     * A detailed description of the quiz, providing potential participants with insights into its
     * content, scope, and objectives. The description supports up to 1000 characters, allowing
     * for thorough explanations without overwhelming brevity.
     */
    @NotBlank(message = "Description is required")
    @Size(min = 1, max = 1000, message = "Description must be between 1 and 1000 characters")
    @Schema(required = true, example = "A quiz covering a wide range of topics.", description = "Description of the new quiz")
    private String description;

    /**
     * The difficulty level of the quiz, categorized to match participant expertise and challenge preferences.
     * This parameter aids in appropriately targeting and engaging diverse audiences.
     */
    @NotBlank(message = "Difficulty is required")
    @Schema(required = true, example = "EASY", description = "Difficulty of the new quiz")
    private Difficulty difficulty;

    /**
     * A flag indicating whether the quiz is to be made publicly available or restricted to private access.
     * This allows creators to control the visibility and reach of their content based on specific needs
     * or contexts.
     */
    @NotBlank(message = "isPublic is required")
    @Schema(required = true, example = "true", description = "Boolean value indicating whether the new quiz is public or private")
    private boolean isPublic;

    /**
     * The unique identifier of the quiz creator, ensuring that authorship and associated permissions
     * are correctly attributed and managed within the system.
     */
    @NotBlank(message = "CreatorId is required")
    @Schema(required = true, example = "123e4567-e89b-12d3-a456-426614174000", description = "Unique identifier of the quiz creator")
    private UUID creatorId;


    /**
     * Associates the quiz with a specific category, facilitating its classification and discoverability
     * within the platform.
     */
    @NotBlank(message = "CategoryId is required")
    @Schema(description = "CategoryId of the quiz", example = "23e4567-e89b-12d3-a456-426614174000")
    private UUID categoryId;


    /**
     * A collection of questions that will constitute the quiz. This list encapsulates various question
     * types, structures, and content, providing the foundational elements of the quiz's interactive components.
     */
    @Schema(description = "List of questions for the new quiz")
    private List<QuestionCreateDTO> questions;

    /**
     * An optional identifier for an associated image, offering a visual representation or thematic
     * complement to the quiz content.
     */
    @Schema(description = "ImageId of the quiz")
    private UUID imageId;
}
