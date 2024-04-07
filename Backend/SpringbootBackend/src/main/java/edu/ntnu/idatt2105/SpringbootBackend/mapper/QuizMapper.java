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
import java.util.Set;
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
            .difficulty(quiz.getDifficulty())
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
                .difficulty(quizCreateDTO.getDifficulty())
                .isPublic(quizCreateDTO.isPublic())
                .build();
    }

    public Quiz updateQuizFromDTO(QuizDTO quizDTO, Quiz existingQuiz) {
        // assuming that the category cannot be changed via the QuizDTO
        existingQuiz.setTitle(quizDTO.getTitle());
        existingQuiz.setDescription(quizDTO.getDescription());
        existingQuiz.setCategory(Category.builder().id(quizDTO.getCategoryId()).build());
        existingQuiz.setCreator(User.builder().id(quizDTO.getCreatorId()).build());
        existingQuiz.setImage(Image.builder().id(quizDTO.getImageId()).build());
        existingQuiz.setDifficulty(quizDTO.getDifficulty());
        existingQuiz.setPublic(quizDTO.isPublic());
        return existingQuiz;
    }

    public Quiz toEntity(CompleteQuizDTO dto, User creator, Category category) {
        Quiz quiz = new Quiz();
        quiz.setTitle(dto.getTitle());
        quiz.setDescription(dto.getDescription());
        quiz.setCreator(creator);
        quiz.setCategory(category);
        quiz.setDifficulty(dto.getDifficulty());
        quiz.setPublic(dto.getIsPublic());

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
                .collect(Collectors.toSet())); // Collect to a Set instead of a List
        }

        return quiz;
    }


    public CompleteQuizDTO toCompleteQuizDTO(Quiz quiz) {
        Set<CompleteQuestionDTO> completeQuestionDTOs = quiz.getQuestions().stream()
                .map(question -> questionMapper.toCompleteQuestionDTO(question))
                .collect(Collectors.toSet());

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
                quiz.getCategory().getId(),
                quiz.getDifficulty(),
                quiz.isPublic(),
                completeQuestionDTOs,
                imageName,
                imageType,
                imageData
                );
        
    }
}
