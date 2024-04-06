package edu.ntnu.idatt2105.SpringbootBackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;

/**
 * Represents a Data Transfer Object for creating a new answer. It is used to carry data concerning
 * an answer, including its text and correctness, between processes or through network calls.
 * This DTO includes the text of the answer and a boolean indicating whether it is the correct answer.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for creating a new answer")
public class AnswerCreateDTO {

    /**
     * The textual content of the answer.
     */
    @Schema(example = "Norway",description = "Text of the answer", required = true)
    private String text;

    /**
     * Indicates whether the answer is correct.
     */
    @Schema(example = "true",description = "Boolean value indicating if the answer is correct", required = true)
    private boolean isCorrect;
}
