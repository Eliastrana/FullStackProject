package edu.ntnu.idatt2105.SpringbootBackend.dto;

import edu.ntnu.idatt2105.SpringbootBackend.model.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompleteQuestionDTO {
    private UUID id;
    private String text;
    private QuestionType questionType;
    private String multimediaLink;
    private Set<UUID> tags;
    private List<AnswerDTO> answers; 

}
