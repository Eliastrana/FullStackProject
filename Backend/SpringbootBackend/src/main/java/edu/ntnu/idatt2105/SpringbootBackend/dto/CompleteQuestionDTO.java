package edu.ntnu.idatt2105.SpringbootBackend.dto;

import edu.ntnu.idatt2105.SpringbootBackend.model.QuestionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for a complete question, including all details such as text, type, multimedia link, tags, answers, and associated image.")
public class CompleteQuestionDTO {
    @Schema(example = "What is the penalty for a handball in soccer?", description = "The text content of the question.", required = true)
    private String text;
    
    @Schema(example = "MULTIPLE_CHOICE", description = "The type of question, indicating how it should be presented and answered. For example, MULTIPLE_CHOICE for questions with multiple options.", required = true)
    private QuestionType questionType;
    
    @Schema(example = "https://example.com/video/rules-of-handball-in-soccer", description = "An optional multimedia link related to the question, such as a video or image URL.")
    private String multimediaLink;
    
    @Schema(example = "[\"Soccer\", \"Rules\"]", description = "A set of tags associated with the question, categorizing or defining its topics.")
    private Set<String> tags;
    
    @Schema(description = "A collection of possible answers for the question. Each answer is detailed with its content and correctness.")
    private Set<AnswerCreateDTO> answers; 
    
    @Schema(example = "soccer_rules.png", description = "The name of the image file associated with the question, if any.")
    private String imageName;
    
    @Schema(example = "png", description = "The file type of the associated image, such as 'png', 'jpg', etc.")
    private String imageType;
    
    @Schema(example = "iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==", description = "Base64-encoded data of the image associated with the question, if directly uploaded.")
    private String imageData;
}
