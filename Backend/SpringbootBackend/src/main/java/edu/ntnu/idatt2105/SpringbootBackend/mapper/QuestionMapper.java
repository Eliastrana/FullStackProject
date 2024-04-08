package edu.ntnu.idatt2105.SpringbootBackend.mapper;

import edu.ntnu.idatt2105.SpringbootBackend.dto.AnswerCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.CompleteQuestionDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuestionCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuestionDTO;
import edu.ntnu.idatt2105.SpringbootBackend.model.Image;
import edu.ntnu.idatt2105.SpringbootBackend.model.Question;
import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;
import edu.ntnu.idatt2105.SpringbootBackend.model.Tag;
import edu.ntnu.idatt2105.SpringbootBackend.repository.TagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * The {@code QuestionMapper} class is responsible for mapping between {@link Question} entities
 * and their corresponding DTOs ({@link QuestionDTO}, {@link CompleteQuestionDTO}, and {@link QuestionCreateDTO}).
 * It provides methods to convert entities to DTOs for data transfer, and vice versa, to assist
 * with data manipulation and presentation logic.
 *
 * @author Vegard Johnsen
 * @version 1.0
 * @since 1.0
 * @see Question
 * @see QuestionDTO
 * @see CompleteQuestionDTO
 * @see QuestionCreateDTO
 * @see AnswerMapper
 * @see TagRepository
 */
@Component
public class QuestionMapper {

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private TagRepository tagRepository;

    /**
     * Converts a {@link Question} entity to a {@link QuestionDTO}.
     * This method maps all relevant fields from the {@link Question} entity to the {@link QuestionDTO}.
     * If the {@code question} is null, it returns null.
     *
     * @param question the {@link Question} entity to convert
     * @return the converted {@link QuestionDTO}, or null if the input is null
     */
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
        dto.setImageId(question.getImage() != null ? question.getImage().getId() : null);

        
        return dto;
    }

    /**
     * Converts a {@link QuestionCreateDTO} to a {@link Question} entity and associates it with a given {@link Quiz}.
     * This method is used when creating a new question from client data. It sets the provided details
     * onto a new {@link Question} entity. The question's creation date is set to the current date and time.
     *
     * @param dto the {@link QuestionCreateDTO} containing the new question's details
     * @param quiz the {@link Quiz} to associate the new question with
     * @return the created {@link Question} entity, or null if the input DTO is null
     */
    public Question toQuestion(QuestionCreateDTO dto, Quiz quiz) {
        if (dto == null) {
            return null;
        }
        
        Question question = new Question();
        question.setQuiz(quiz);
        question.setText(dto.getText());
        question.setQuestionType(dto.getQuestionType());
        question.setMultimediaLink(dto.getMultimediaLink());
        question.setCreationDate(LocalDateTime.now());
        
        return question;
    }

    /**
     * Updates an existing {@link Question} entity with data from a {@link QuestionDTO}.
     * This method applies changes from the DTO to the existing entity, such as text and multimedia link.
     * It is used to modify an existing question based on updated data provided by the client.
     *
     * @param dto the {@link QuestionDTO} containing the updated details
     * @param question the existing {@link Question} entity to update
     * @return the updated {@link Question} entity, or null if any input is null
     */
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

    /**
     * Converts a {@link CompleteQuestionDTO} to a {@link Question} entity and associates it with a given {@link Quiz}.
     * This method sets detailed information onto a new {@link Question} entity, including associated tags
     * and answers, making it suitable for creating questions with a complete set of details at once.
     *
     * @param dto the {@link CompleteQuestionDTO} containing the complete question details
     * @param quiz the {@link Quiz} to associate the new question with
     * @return the created {@link Question} entity, or null if the input DTO is null
     */
    public Question toEntity(CompleteQuestionDTO dto, Quiz quiz) {
        if (dto == null) {
            return null;
        }
        
        Question question = new Question();
        question.setId(dto.getId());
        question.setQuiz(quiz);
        question.setText(dto.getText());
        question.setQuestionType(dto.getQuestionType());
        question.setMultimediaLink(dto.getMultimediaLink());
        question.setCreationDate(LocalDateTime.now());

        if (dto.getImageData() != null && !dto.getImageData().isEmpty()) {
            byte[] decodedImg = Base64.getDecoder().decode(dto.getImageData());
            Image image = Image.builder()
                    .filename(dto.getImageName())
                    .fileType(dto.getImageType())
                    .size(decodedImg.length)
                    .data(decodedImg)
                    .build();
            question.setImage(image);
        }

        if (dto.getTags() != null) {
            Set<Tag> tags = dto.getTags().stream()
                    .map(tagName -> tagRepository.findByName(tagName)
                            .orElseGet(() -> {
                                Tag newTag = new Tag();
                                newTag.setName(tagName);
                                return tagRepository.save(newTag); // Create and save a new Tag if one with the name doesn't exist
                            }))
                    .collect(Collectors.toSet());
            question.setTags(tags);
        }

        if (dto.getAnswers() != null) {
            question.setAnswers(dto.getAnswers().stream()
                    .map(answerCreateDTO -> answerMapper.toEntity(answerCreateDTO, question)) // Adjust this method in AnswerMapper
                    .collect(Collectors.toSet()));
        }

        
        return question;
    }

    /**
     * Converts a {@link Question} entity to a {@link CompleteQuestionDTO}.
     * This method maps detailed information from the {@link Question} entity, including tags and answers,
     * to a {@link CompleteQuestionDTO}, suitable for transferring a complete question's data to the client.
     *
     * @param question the {@link Question} entity to convert
     * @return the converted {@link CompleteQuestionDTO}, or null if the input is null
     */
    public CompleteQuestionDTO toCompleteQuestionDTO(Question question) {
        if (question == null) {
            return null;
        }
        CompleteQuestionDTO dto = new CompleteQuestionDTO();
        dto.setId(question.getId());
        dto.setText(question.getText());
        dto.setQuestionType(question.getQuestionType());
        dto.setMultimediaLink(question.getMultimediaLink());

        // Include image information if available
        if (question.getImage() != null) {
            dto.setImageName(question.getImage().getFilename());
            dto.setImageType(question.getImage().getFileType());
            // Normally, imageData is not included here to avoid large payloads
        }
        dto.setTags(question.getTags().stream()
            .map(Tag::getName)
            .collect(Collectors.toSet()));



        if (question.getAnswers() != null && !question.getAnswers().isEmpty()) {
            dto.setAnswers(question.getAnswers().stream()
                    .map(answer -> new AnswerCreateDTO(answer.getText(), answer.isCorrect()))
                    .collect(Collectors.toSet()));
        }
        return dto;
    }
    
}
