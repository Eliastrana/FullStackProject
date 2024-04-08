package edu.ntnu.idatt2105.SpringbootBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents a Data Transfer Object for an answer. This DTO is utilized for transmitting answer
 * details between the client and server, encapsulating information such as the answer's unique identifier,
 * the textual content of the answer, and a boolean flag indicating its correctness. This structure is essential
 * for operations involving answer creation, retrieval, and manipulation in quiz-related functionalities.
 *
 * @author Vegard Johnsen
 * @version 0.1
 * @since 0.1
 * @see AnswerCreateDTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for an answer")
public class AnswerDTO {
    /**
     * The unique identifier for the answer, represented as a UUID. This ID is used to uniquely
     * identify an answer across the system, facilitating operations like retrieval and updating of answer details.
     */
    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "Unique identifier of the answer", required = true)
    private UUID id;
    /**
     * The text of the answer. This field contains the actual content of the answer as presented
     * to the user or quiz taker, forming the substance of the quiz's questioning.
     */
    @Schema(example = "Norway", description = "Text of the answer", required = true)
    private String text;
    /**
     * A flag indicating whether the answer is correct. This boolean value is used to determine
     * the correctness of the answer in the context of the quiz, playing a critical role in quiz scoring and feedback.
     */
    @Schema(example = "true", description = "Boolean value indicating if the answer is correct", required = true)
    private boolean isCorrect;
}
