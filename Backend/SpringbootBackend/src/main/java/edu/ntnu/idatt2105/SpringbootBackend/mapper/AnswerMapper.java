package edu.ntnu.idatt2105.SpringbootBackend.mapper;

import edu.ntnu.idatt2105.SpringbootBackend.dto.AnswerCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.AnswerDTO;
import edu.ntnu.idatt2105.SpringbootBackend.model.Answer;
import edu.ntnu.idatt2105.SpringbootBackend.model.Question;
import org.springframework.stereotype.Component;

@Component
public class AnswerMapper {

    public AnswerDTO toDTO(Answer answer) {
        if (answer == null) {
            return null;
        }

        AnswerDTO dto = new AnswerDTO();
        dto.setId(answer.getId());
        dto.setText(answer.getText());
        dto.setCorrect(answer.isCorrect());
        // Assuming AnswerDTO has a field for questionId if needed
        // dto.setQuestionId(answer.getQuestion().getId());

        return dto;
    }

    public Answer toEntity(AnswerCreateDTO dto, Question question) {
        if (dto == null) {
            return null;
        }

        Answer answer = new Answer();
        answer.setText(dto.getText());
        answer.setCorrect(dto.isCorrect());
        answer.setQuestion(question); // Set the question this answer belongs to

        return answer;
    }

    // Optionally, implement an update method if needed
    public Answer updateEntityFromDTO(AnswerDTO dto, Answer existingAnswer) {
        if (dto == null || existingAnswer == null) {
            return null;
        }

        existingAnswer.setText(dto.getText());
        existingAnswer.setCorrect(dto.isCorrect());
        // Note: The question associated with the answer typically doesn't change in an update scenario

        return existingAnswer;
    }

    public AnswerDTO toAnswerDTO(Answer answer) {
        if (answer == null) {
            return null;
        }

        AnswerDTO dto = new AnswerDTO();
        dto.setId(answer.getId());
        dto.setText(answer.getText());
        dto.setCorrect(answer.isCorrect());
        return dto;
    }
}
