package edu.ntnu.idatt2105.SpringbootBackend.dto;

import edu.ntnu.idatt2105.SpringbootBackend.model.QuestionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for creating a new question")
public class QuestionCreateDTO {
    @Schema(description = "Text of the question", required = true)
    private String text;

    @Schema(description = "Type of the question", required = true)
    private QuestionType questionType;

    @Schema(description = "Optional multimedia link associated with the question")
    private String multimediaLink;
}