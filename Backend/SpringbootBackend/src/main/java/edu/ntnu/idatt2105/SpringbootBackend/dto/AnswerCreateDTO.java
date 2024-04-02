package edu.ntnu.idatt2105.SpringbootBackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerCreateDTO {
    private String text;
    private boolean isCorrect;
}
