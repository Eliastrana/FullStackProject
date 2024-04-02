package edu.ntnu.idatt2105.SpringbootBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserQuizAttemptDTO {

    private UUID id; 
    private UUID userId; 
    private UUID quizId; 
    private int score; 
    private String status; 
}
