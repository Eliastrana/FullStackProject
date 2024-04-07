package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.dto.QuestionCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuestionDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.ImageNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.QuestionNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.QuizNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.TagNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.mapper.QuestionMapper;
import edu.ntnu.idatt2105.SpringbootBackend.model.Image;
import edu.ntnu.idatt2105.SpringbootBackend.model.Question;
import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;
import edu.ntnu.idatt2105.SpringbootBackend.model.Tag;
import edu.ntnu.idatt2105.SpringbootBackend.repository.QuestionRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.QuizRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.TagRepository;
import edu.ntnu.idatt2105.SpringbootBackend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The {@code QuestionService} provides functionalities for managing questions
 * within quizzes, including creation, retrieval, updating, and deletion of questions.
 * It works closely with {@link QuizRepository}, {@link QuestionRepository}, and {@link TagRepository}
 * to ensure that questions are properly managed and associated with their quizzes and tags.
 *
 * @author Vegard Johnsen
 * @version 0.1
 * @since 0.1
 * @see Question
 * @see Quiz
 * @see Tag
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ImageService imageService;

    /**
     * Creates a new question and associates it with the specified quiz.
     * If an image ID is provided, it associates the image with the question.
     * Tags associated with the question are also handled in this method.
     *
     * @param quizId The ID of the quiz to which the question will be added.
     * @param questionCreateDTO Data Transfer Object containing question details.
     * @return The created {@link QuestionDTO} with ID and question details.
     * @throws QuizNotFoundException if the quiz with the specified ID does not exist.
     */
    @Transactional
    public QuestionDTO createQuestion(UUID quizId, QuestionCreateDTO questionCreateDTO) throws QuizNotFoundException {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found with id: " + quizId));
        
        Question question = questionMapper.toQuestion(questionCreateDTO, quiz);
        UUID imageId = questionCreateDTO.getImageId();
        if (imageId != null) {
            Image image = imageService.getImage(imageId)
                    .orElseThrow(() -> new ImageNotFoundException("Image not found with id: " + imageId)); 
            question.setImage(image);
        }

        if (questionCreateDTO.getTags() != null && !questionCreateDTO.getTags().isEmpty()) {
            Set<Tag> tags = new HashSet<>(tagRepository.findAllById(questionCreateDTO.getTags()));
            question.setTags(tags);
        }

        question = questionRepository.save(question);
        
        return questionMapper.toQuestionDTO(question);
    }

    /**
     * Retrieves a question by its ID.
     *
     * @param questionId The ID of the question to retrieve.
     * @return The {@link QuestionDTO} containing question details.
     * @throws QuestionNotFoundException if no question with the specified ID exists.
     */
    public QuestionDTO getQuestionById(UUID questionId) throws QuestionNotFoundException {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(questionId));
        
        return questionMapper.toQuestionDTO(question);
    }

    /**
     * Updates an existing question identified by its ID with new details provided in the {@link QuestionDTO}.
     *
     * @param questionId The ID of the question to update.
     * @param questionDTO Data Transfer Object containing new question details.
     * @return The updated {@link QuestionDTO} with updated question details.
     * @throws QuestionNotFoundException if no question with the specified ID exists.
     */
    @Transactional
    public QuestionDTO updateQuestion(UUID questionId, QuestionDTO questionDTO) throws QuestionNotFoundException {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(questionId));

        // Fetch and set tags
        if (questionDTO.getTags() != null && !questionDTO.getTags().isEmpty()) {
            Set<Tag> tags = questionDTO.getTags().stream()
                    .map(tagId -> tagRepository.findById(tagId)
                            .orElseThrow(() -> new TagNotFoundException(tagId))) 
                    .collect(Collectors.toSet());
            question.setTags(tags);
        }


        // Assuming you have a method in QuestionMapper to update other fields of a Question entity from QuestionDTO
        // Exclude tag updating logic from this method if it was previously there
        question = questionMapper.updateQuestionFromDTO(questionDTO, question);

        question = questionRepository.save(question);

        return questionMapper.toQuestionDTO(question);
    }


    /**
     * Deletes a question identified by its ID.
     *
     * @param questionId The ID of the question to delete.
     * @throws QuestionNotFoundException if no question with the specified ID exists.
     */
    @Transactional
    public void deleteQuestion(UUID questionId) throws QuestionNotFoundException {
        if (!questionRepository.existsById(questionId)) {
            throw new QuestionNotFoundException(questionId);
        }
        
        questionRepository.deleteById(questionId);
    }

    /**
     * Retrieves all questions associated with a specific quiz ID.
     *
     * @param quizId The ID of the quiz for which to retrieve questions.
     * @return A list of {@link QuestionDTO} containing details of each question associated with the quiz.
     */
    public List<QuestionDTO> getQuestionsByQuizId(UUID quizId) {
        List<Question> questions = questionRepository.findAllByQuizId(quizId);
        return questions.stream().map(questionMapper::toQuestionDTO).collect(Collectors.toList());
    }

    /**
     * Associates an image with a question identified by its ID.
     *
     * @param questionId The ID of the question to associate with the image.
     * @param image The {@link Image} to associate with the question.
     * @throws QuestionNotFoundException if no question with the specified ID exists.
     */
    @Transactional
    public void setImageForQuestion(UUID questionId, Image image) throws QuestionNotFoundException {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(questionId));
        question.setImage(image);
        questionRepository.save(question);
    }
}
