package edu.ntnu.idatt2105.SpringbootBackend.dto;

import edu.ntnu.idatt2105.SpringbootBackend.model.QuestionType;
import edu.ntnu.idatt2105.SpringbootBackend.model.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a Data Transfer Object (DTO) for a question, encapsulating all relevant details about the question.
 * This includes the question's content, associated quiz, type, tags, and any linked multimedia content.
 * The QuestionDTO structure is used for operations involving the creation, retrieval, and updating of question information.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for question details. This includes all relevant information about a question such as its content, associated quiz, type, tags, and any linked multimedia content.")
public class QuestionDTO {

    /**
     * Serves as the unique identifier for the question, facilitating precise referencing and
     * manipulation within the system's database and operations.
     */
    @Schema(description = "The unique identifier of the question.", example = "a3b8d425-2b60-4ad7-becc-bedf2ef860bd")
    private UUID id;

    /**
     * Links the question to its corresponding quiz by means of the quiz's unique identifier, thereby
     * establishing a clear hierarchical relationship within the quiz structure.
     */
    @Schema(description = "The unique identifier of the quiz to which this question belongs.", example = "e8f5e7f3-7ed3-4faa-9889-7161d2b55633")
    private UUID quizId;

    /**
     * Constitutes the core query or statement posed by the question, formulated in text. This is the
     * primary content to which respondents must react or answer.
     */
    @Schema(description = "The text content of the question.", example = "Who wrote 'To Kill a Mockingbird'?")
    private String text;

    /**
     * Specifies the format or method through which the question is to be answered, such as multiple
     * choice or open-ended, thus defining the interaction modality for the question.
     */
    @Schema(description = "The type of the question, indicating how it should be answered.", example = "MULTIPLE_CHOICE")
    private QuestionType questionType;

    /**
     * Provides an optional URL to external multimedia resources that augment or clarify the question,
     * such as illustrative images or explanatory videos, enriching the question's context.
     */
    @Schema(description = "An optional link to multimedia content related to the question, such as an image or video.", example = "https://example.com/video.mp4")
    private String multimediaLink;

    /**
     * Records the moment of the question's creation within the system, offering a temporal
     * reference that can be used for organizational, historical, or auditing purposes.
     */
    @Schema(description = "The date and time when the question was created.", example = "2023-04-05T12:00:00")
    private LocalDateTime creationDate;

    /**
     * Identifies the thematic or topical categories with which the question is associated, through
     * a collection of tag UUIDs. This facilitates categorization and searchability within the system.
     */
    @Schema(description = "A set of UUIDs representing the tags associated with the question. Tags are used to categorize questions into topics.", example = "[\"e97aaa14-9a84-4e28-957c-76e8fcb4c321\", \"5fbdd9e3-eded-4980-9a4b-1d8df815b851\"]")
    private Set<UUID> tags;

    /**
     * If applicable, the UUID of an image linked to the question, adding a visual dimension to the
     * question content and potentially enhancing engagement and comprehension.
     */
    @Schema(description = "The UUID of an image associated with the question, if any. This can be used to add visual context to the question.", example = "f5d8a8ff-9b4d-4c67-8e48-5e9d5e7485df")
    private UUID imageId; 
}
