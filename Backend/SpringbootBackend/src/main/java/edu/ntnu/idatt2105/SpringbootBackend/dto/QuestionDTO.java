package edu.ntnu.idatt2105.SpringbootBackend.dto;

import edu.ntnu.idatt2105.SpringbootBackend.model.Question.QuestionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for question details")
public class QuestionDTO {
    @Schema(description = "Unique identifier of the question", required = true)
    private UUID id;

    @Schema(description = "Unique identifier of the quiz this question belongs to", required = true)
    private UUID quizId;

    @Schema(description = "Text of the question", required = true)
    private String text;

    @Schema(description = "Type of the question", required = true)
    private QuestionType questionType;

    @Schema(description = "Optional multimedia link associated with the question")
    private String multimediaLink;

    @Schema(description = "Creation date of the question", required = true)
    private LocalDateTime creationDate;
}
