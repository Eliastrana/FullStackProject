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

/**
 * The {@code QuizMapper} class is responsible for mapping between {@link Quiz} entities
 * and their corresponding Data Transfer Objects (DTOs), such as {@link QuizDTO} and {@link CompleteQuizDTO}.
 * It provides methods to convert entities to DTOs and vice versa, facilitating the manipulation
 * and presentation of quiz data.
 *
 * @author vegard johnsen, sander rom skofsrud
 * @version 1.0
 * @since 1.0
 * @see Quiz
 * @see QuizDTO
 * @see CompleteQuizDTO
 * @see CategoryRepository
 * @see ImageRepository
 */
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


    /**
     * Converts a {@link Quiz} entity to a {@link QuizDTO}.
     * This method maps all relevant fields from the {@link Quiz} entity to the {@link QuizDTO},
     * including the quiz's category and image if they exist.
     *
     * @param quiz the {@link Quiz} entity to convert
     * @return the converted {@link QuizDTO}, or null if the input is null
     */
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
            .isPublic(quiz.isPublic())
            .build();
}

    /**
     * Converts a {@link QuizCreateDTO} to a {@link Quiz} entity and sets the creator.
     * This method is used when creating a new quiz from client data, associating it with the user who created it.
     *
     * @param quizCreateDTO the {@link QuizCreateDTO} containing the new quiz's details
     * @param creator the {@link User} who is creating the quiz
     * @return the created {@link Quiz} entity, or null if the input DTO is null
     */
    public Quiz toQuiz(QuizCreateDTO quizCreateDTO, User creator) {
        return Quiz.builder()
                .title(quizCreateDTO.getTitle())
                .description(quizCreateDTO.getDescription())
                .creator(creator)
                .difficulty(quizCreateDTO.getDifficulty())
                .isPublic(quizCreateDTO.isPublic())
                .build();
    }

    /**
     * Updates an existing {@link Quiz} entity with data from a {@link QuizDTO}.
     * This method applies changes from the DTO to the existing entity, such as title and description.
     *
     * @param quizDTO the {@link QuizDTO} containing the updated details
     * @param existingQuiz the existing {@link Quiz} entity to update
     * @return the updated {@link Quiz} entity, or null if any input is null
     */
    public Quiz updateQuizFromDTO(QuizDTO quizDTO, Quiz existingQuiz) {
        // assuming that the category cannot be changed via the QuizDTO
        existingQuiz.setTitle(quizDTO.getTitle());
        existingQuiz.setDescription(quizDTO.getDescription());
        existingQuiz.setCategory(Category.builder().id(quizDTO.getCategoryId()).build());
        existingQuiz.setCreator(User.builder().id(quizDTO.getCreatorId()).build());
        existingQuiz.setImage(Image.builder().id(quizDTO.getImageId()).build());
        existingQuiz.setDifficulty(quizDTO.getDifficulty());
        existingQuiz.setPublic(quizDTO.getIsPublic());
        return existingQuiz;
    }

    /**
     * Converts a {@link CompleteQuizDTO} to a {@link Quiz} entity, including all questions and images.
     * This method sets detailed information onto a new {@link Quiz} entity, making it suitable
     * for creating quizzes with a complete set of details at once.
     *
     * @param dto the {@link CompleteQuizDTO} containing the complete quiz details
     * @param creator the {@link User} who is creating the quiz
     * @param category the {@link Category} under which the quiz falls
     * @return the created {@link Quiz} entity, or null if the input DTO is null
     */

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


    /**
     * Converts a {@link Quiz} entity to a {@link CompleteQuizDTO}.
     * This method maps detailed information from the {@link Quiz} entity to a {@link CompleteQuizDTO},
     * suitable for transferring a complete quiz's data to the client, including questions and images.
     *
     * @param quiz the {@link Quiz} entity to convert
     * @return the converted {@link CompleteQuizDTO}, or null if the input is null
     */
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
