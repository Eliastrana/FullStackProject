package edu.ntnu.idatt2105.SpringbootBackend.mapper;

import edu.ntnu.idatt2105.SpringbootBackend.dto.QuestionCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuestionDTO;
import edu.ntnu.idatt2105.SpringbootBackend.model.Tag;
import edu.ntnu.idatt2105.SpringbootBackend.model.Question;
import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class QuestionMapper {

    public QuestionDTO toQuestionDTO(Question question) {
        if (question == null) {
            return null;
        }
        
        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setQuizId(question.getQuiz().getId());
        dto.setText(question.getText());
        dto.setQuestionType(question.getQuestionType());
        dto.setMultimediaLink(question.getMultimediaLink());
        dto.setCreationDate(question.getCreationDate());
        dto.setTags(question.getTags().stream().map(Tag::getId).collect(Collectors.toSet()));
        return dto;
    }

    public Question toQuestion(QuestionCreateDTO dto, Quiz quiz) {
        if (dto == null) {
            return null;
        }
        
        Question question = new Question();
        question.setQuiz(quiz);
        question.setText(dto.getText());
        question.setQuestionType(dto.getQuestionType());
        question.setMultimediaLink(dto.getMultimediaLink());
        question.setCreationDate(LocalDateTime.now()); // Automatically set creation date
        
        return question;
    }

        public Question updateQuestionFromDTO(QuestionDTO dto, Question question) {
        if (dto == null || question == null) {
            return null;
        }
        
        question.setText(dto.getText());
        question.setQuestionType(dto.getQuestionType());
        question.setMultimediaLink(dto.getMultimediaLink());

        // Note: CreationDate and QuizId should not be updated here
        
        return question;
    }
}
