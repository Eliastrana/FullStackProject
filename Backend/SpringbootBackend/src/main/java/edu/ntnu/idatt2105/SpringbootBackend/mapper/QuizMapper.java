package edu.ntnu.idatt2105.SpringbootBackend.mapper;

import edu.ntnu.idatt2105.SpringbootBackend.dto.CategoryDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.CompleteQuestionDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.CompleteQuizDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuizCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuizDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.CategoryNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.model.Category;
import edu.ntnu.idatt2105.SpringbootBackend.model.Image;
import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.repository.CategoryRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.ImageRepository;

import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuizMapper {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private ImageRepository imageRepository;

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
    if (quiz.getImage() != null) { 
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

    public Quiz toEntity(CompleteQuizDTO dto, User creator, Category category) {
        Quiz quiz = new Quiz();
        quiz.setTitle(dto.getTitle());
        quiz.setDescription(dto.getDescription());
        quiz.setCreator(creator);
        quiz.setCategory(category);

        // Include image information if available
        if (dto.getImageData() != null && !dto.getImageData().isEmpty()) {
            byte[] decodedImg = Base64.getDecoder().decode(dto.getImageData());
            Image image = Image.builder()
                    .filename(dto.getImageName()) // Assuming you have image name and type in DTO
                    .fileType(dto.getImageType())
                    .size(decodedImg.length)
                    .data(decodedImg)
                    .build();
            // Assuming you have a repository or service to save the image
            image = imageRepository.save(image); 
            quiz.setImage(image);
        }
        
        if (dto.getQuestions() != null) {
            quiz.setQuestions(dto.getQuestions().stream()
                .map(questionDTO -> questionMapper.toEntity(questionDTO, quiz))
                .collect(Collectors.toList()));
        }

        return quiz;
    }


    public CompleteQuizDTO toCompleteQuizDTO(Quiz quiz) {
        List<CompleteQuestionDTO> completeQuestionDTOs = quiz.getQuestions().stream()
                .map(question -> questionMapper.toCompleteQuestionDTO(question))
                .collect(Collectors.toList());

        String categoryName = quiz.getCategory() != null ? quiz.getCategory().getCategoryName() : null;

        String imageName = null;
        String imageType = null;
        String imageData = null;

        if (quiz.getImage() != null) {
            imageName = quiz.getImage().getFilename();
            imageType = quiz.getImage().getFileType();
            imageData = Base64.getEncoder().encodeToString(quiz.getImage().getData());
        }

        return new CompleteQuizDTO(
                quiz.getTitle(),
                quiz.getDescription(),
                quiz.getCreator().getId(),
                categoryName,
                completeQuestionDTOs,
                imageName,
                imageType,
                imageData
                );
        
    }
}
