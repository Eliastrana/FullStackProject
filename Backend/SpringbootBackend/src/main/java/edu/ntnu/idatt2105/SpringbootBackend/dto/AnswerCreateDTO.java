package edu.ntnu.idatt2105.SpringbootBackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for creating a new answer")
public class AnswerCreateDTO {
    @Schema(example = "Norway",description = "Text of the answer", required = true)
    private String text;
    @Schema(example = "true",description = "Boolean value indicating if the answer is correct", required = true)
    private boolean isCorrect;
}
