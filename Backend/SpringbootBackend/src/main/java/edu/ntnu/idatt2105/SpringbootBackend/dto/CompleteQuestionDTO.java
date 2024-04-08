package edu.ntnu.idatt2105.SpringbootBackend.dto;

import edu.ntnu.idatt2105.SpringbootBackend.model.QuestionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

/**
 * Represents a Data Transfer Object (DTO) for encapsulating all the details of a question in a quiz.
 * This includes the question's content, type, any multimedia links, tags for categorization,
 * a set of possible answers, and any associated images. This comprehensive structure is designed
 * to provide a full overview of a question's properties in a single object, facilitating easier data
 * transfer and processing between the client and server.
 *
 * @author Vegard Johnsen, Sander R. Skofsrud
 * @version 0.1
 * @since 0.1
 * @see AnswerCreateDTO
 * @see QuestionType
 * @see CompleteQuestionDTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for a complete question, including all details such as text, type, multimedia link, tags, answers, and associated image.")
public class CompleteQuestionDTO {
    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "The unique identifier of the question.", required = true)
    private UUID id;

    /**
     * The text content of the question. This is the primary information presented to the user,
     * describing what is being asked.
     */
    @Schema(example = "What is the penalty for a handball in soccer?", description = "The text content of the question.", required = true)
    private String text;

    /**
     * The type of the question, indicating how it should be presented and answered. Enumerated values
     * such as MULTIPLE_CHOICE define the interaction model for the question.
     */
    @Schema(example = "MULTIPLE_CHOICE", description = "The type of question, indicating how it should be presented and answered. For example, MULTIPLE_CHOICE for questions with multiple options.", required = true)
    private QuestionType questionType;

    /**
     * An optional multimedia link related to the question. This can be a URL pointing to an external
     * resource like a video, image, or article that provides additional context or information for the question.
     */
    @Schema(example = "https://example.com/video/rules-of-handball-in-soccer", description = "An optional multimedia link related to the question, such as a video or image URL.")
    private String multimediaLink;

    /**
     * A set of tags associated with the question. Tags help in categorizing or defining the topics
     * covered by the question, enhancing discoverability and organization.
     */
    @Schema(example = "[\"Soccer\", \"Rules\"]", description = "A set of tags associated with the question, categorizing or defining its topics.")
    private Set<String> tags;

    /**
     * A collection of possible answers for the question. Each entry in this set represents a potential
     * answer, detailing its content and whether it is correct or not.
     */
    @Schema(description = "A collection of possible answers for the question. Each answer is detailed with its content and correctness.")
    private Set<AnswerCreateDTO> answers;

    /**
     * The name of the image file associated with the question, if any. This is used to reference
     * an image stored either locally or externally, providing a visual element to the question.
     */
    @Schema(example = "soccer_rules.png", description = "The name of the image file associated with the question, if any.")
    private String imageName;

    /**
     * The file type of the associated image. This detail, such as 'png', 'jpg', etc., helps in
     * determining how to properly handle and display the image.
     */
    @Schema(example = "png", description = "The file type of the associated image, such as 'png', 'jpg', etc.")
    private String imageType;

    /**
     * Base64-encoded data of the image associated with the question. This field is used if the image
     * is directly uploaded and stored as part of the question data, providing an embedded image representation.
     */
    @Schema(example = "iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==", description = "Base64-encoded data of the image associated with the question, if directly uploaded.")
    private String imageData;
}
