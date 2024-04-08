package edu.ntnu.idatt2105.SpringbootBackend.dto;

import edu.ntnu.idatt2105.SpringbootBackend.model.QuestionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

/**
 * This DTO is designed for the creation of new questions within the system. It encompasses all essential
 * attributes needed to construct a question, including the question's text, the type of question, optional multimedia content (such as images or videos), tags for categorization,
 * a set of possible answers, and an optional image for visual representation. This structured format ensures
 * that all necessary information for question creation is encapsulated and can be easily transmitted between
 * the client and server, facilitating the dynamic construction of quizzes and educational content.
 *
 * @author Vegard Johnsen
 * @version 0.1
 * @since 0.1
 * @see AnswerCreateDTO
 * @see QuestionType
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for creating a new question. This includes all necessary information to define a question such as the text, type, multimedia links, tags, and associated answers.")
public class QuestionCreateDTO {

    /**
     * The primary content of the question, articulated through a textual description or query that
     * the respondent is expected to address or answer.
     */
    @NotBlank(message = "Question text is required")
    @Schema(description = "The text content of the question.", example = "What is the capital of France?")
    private String text;

    /**
     * A designation of the question's format, determining how responses should be structured and how
     * the question will be interacted with by users. This categorization facilitates diverse question
     * types.
     */
    @NotBlank(message = "Question type is required")
    @Schema(description = "The type of the question, defining how it should be presented or answered.", example = "MULTIPLE_CHOICE")
    private QuestionType questionType;

    /**
     * An optional attribute providing a URL to external multimedia resources that are related to the
     * question. This can include, but is not limited to, illustrative images, diagrams, or explanatory
     * video clips.
     */
    @NotBlank(message = "Multimedia link is required")
    @Schema(description = "An optional link to multimedia content related to the question, such as images or videos.", example = "https://example.com/image.png")
    private String multimediaLink;

    /**
     * A collection of tags represented by UUIDs, associated with the question for purposes of classification
     * or categorization. These tags help organize questions into coherent topics or themes.
     */
    @Schema(description = "A set of UUIDs representing the tags associated with the question. These tags help categorize the question by topic.", example = "[\"d290f1ee-6c54-4b01-90e6-d701748f0851\", \"3f6c6af7-9b81-4c60-b4e0-5fbb7c478e88\"]")
    private Set<UUID> tags;

    /**
     * A detailed enumeration of possible answers for the question. This field is particularly critical
     * for question types that provide multiple potential answers, each marked as correct or incorrect,
     * thereby forming the basis for the question's challenge and assessment capability.
     */
    @Schema(description = "A set of answers for the question. This is particularly relevant for types like MULTIPLE_CHOICE where multiple answer options are provided.", example = "[{\"text\":\"Paris\",\"isCorrect\":true}, {\"text\":\"London\",\"isCorrect\":false}]")
    private Set<AnswerCreateDTO> answers;

    /**
     * An optional UUID referencing an image associated with the question, allowing for the inclusion of
     * visual elements that may complement or elucidate the question text.
     */
    @Schema(description = "The UUID of an image associated with the question. This is optional and can be used to add a visual component to the question.", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    private UUID imageId;
}
