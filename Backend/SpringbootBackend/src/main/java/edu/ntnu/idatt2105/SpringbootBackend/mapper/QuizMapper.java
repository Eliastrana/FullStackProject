package edu.ntnu.idatt2105.SpringbootBackend.mapper;

import edu.ntnu.idatt2105.SpringbootBackend.dto.CategoryDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuizCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuizDTO;
import edu.ntnu.idatt2105.SpringbootBackend.model.Category;
import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class QuizMapper {

    public QuizDTO toQuizDTO(Quiz quiz) {
    CategoryDTO categoryDTO = null;
    if (quiz.getCategory() != null) {
        categoryDTO = CategoryDTO.builder()
                .id(quiz.getCategory().getId())
                .categoryName(quiz.getCategory().getCategoryName())
                .description(quiz.getCategory().getDescription())
                .build();
    }

    UUID imageId = null;
    if (quiz.getImage() != null) { // Assuming getImage() retrieves the associated Image entity
        imageId = quiz.getImage().getId();
    }

    return QuizDTO.builder()
            .id(quiz.getId())
            .title(quiz.getTitle())
            .description(quiz.getDescription())
            .creatorId(quiz.getCreator().getId())
            .categoryId(categoryDTO != null ? categoryDTO.getId() : null)
            .imageId(imageId)
            .build();
}



    public Quiz toQuiz(QuizCreateDTO quizCreateDTO, User creator) {
        return Quiz.builder()
                .title(quizCreateDTO.getTitle())
                .description(quizCreateDTO.getDescription())
                .creator(creator)
                // Assume that category is set elsewhere since it's not in QuizCreateDTO
                .build();
    }

    public Quiz updateQuizFromDTO(QuizDTO quizDTO, Quiz existingQuiz) {
        // assuming that the category cannot be changed via the QuizDTO
        existingQuiz.setTitle(quizDTO.getTitle());
        existingQuiz.setDescription(quizDTO.getDescription());
        existingQuiz.setCategory(Category.builder().id(quizDTO.getCategoryId()).build());
        return existingQuiz;
    }
}
