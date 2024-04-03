package edu.ntnu.idatt2105.SpringbootBackend.dto;

import edu.ntnu.idatt2105.SpringbootBackend.model.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompleteQuestionDTO {
    private String text;
    private QuestionType questionType;
    private String multimediaLink;
    private Set<String> tags;
    private List<AnswerCreateDTO> answers; 
    
    // Image fields
    private String imageName;
    private String imageType;
    private String imageData;
}
