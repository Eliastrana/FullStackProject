package edu.ntnu.idatt2105.SpringbootBackend.mapper;

import edu.ntnu.idatt2105.SpringbootBackend.dto.QuizCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuizDTO;
import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import org.springframework.stereotype.Component;

@Component
public class QuizMapper {

    public QuizDTO toQuizDTO(Quiz quiz) {
        return new QuizDTO(
                quiz.getId(),
                quiz.getTitle(),
                quiz.getDescription(),
                quiz.getCreator() != null ? quiz.getCreator().getId() : null
        );
    }

    public Quiz toQuiz(QuizCreateDTO quizCreateDTO, User creator) {
        Quiz quiz = new Quiz();
        quiz.setTitle(quizCreateDTO.getTitle());
        quiz.setDescription(quizCreateDTO.getDescription());
        quiz.setCreator(creator); 
        return quiz;
    }

    public Quiz updateQuizFromDTO(QuizDTO quizDTO, Quiz existingQuiz) {
        existingQuiz.setTitle(quizDTO.getTitle());
        existingQuiz.setDescription(quizDTO.getDescription());
        return existingQuiz;
    }
}
