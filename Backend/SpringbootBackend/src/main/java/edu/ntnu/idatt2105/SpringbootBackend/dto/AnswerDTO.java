package edu.ntnu.idatt2105.SpringbootBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Answer Data Transfer Object (DTO) for transferring answer data between client and server.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for an answer")
public class AnswerDTO {
    @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "Unique identifier of the answer", required = true)
    private UUID id;
    @Schema(example = "Norway", description = "Text of the answer", required = true)
    private String text;
    @Schema(example = "true", description = "Boolean value indicating if the answer is correct", required = true)
    private boolean isCorrect;
}
