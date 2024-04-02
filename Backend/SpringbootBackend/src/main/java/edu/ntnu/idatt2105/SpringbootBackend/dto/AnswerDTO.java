package edu.ntnu.idatt2105.SpringbootBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Answer Data Transfer Object (DTO) for transferring answer data between client and server.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO {

    private UUID id;
    private String text;
    private boolean isCorrect;
}
