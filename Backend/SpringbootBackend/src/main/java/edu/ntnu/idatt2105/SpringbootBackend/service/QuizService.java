package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.dto.QuestionCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuizCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuizDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.CategoryNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.CreatorNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.QuizNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.mapper.QuestionMapper;
import edu.ntnu.idatt2105.SpringbootBackend.mapper.QuizMapper;
import edu.ntnu.idatt2105.SpringbootBackend.model.Category;
import edu.ntnu.idatt2105.SpringbootBackend.model.Image;
import edu.ntnu.idatt2105.SpringbootBackend.model.Question;
import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.repository.CategoryRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.QuestionRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.QuizRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The {@code QuizService} class offers a suite of services for managing quizzes within the system.
 * It facilitates the creation, retrieval, updating, and deletion of quizzes. It leverages
 * {@link QuizRepository}, {@link UserRepository}, {@link CategoryRepository}, along with {@link QuizMapper}
 * and {@link QuestionMapper} for operations involving quiz entities.
 *
 * @author Vegard Johnsen, sander rom skofsrud
 * @version 1.0
 * @since 1.0
 * @see Quiz
 * @see Category
 * @see Question
 */
@Service
public class QuizService {

    private final QuestionMapper questionMapper;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final QuizMapper quizMapper;

    /**
     * Constructs a {@code QuizService} with necessary dependencies.
     *
     * @param questionRepository Repository for question entities.
     * @param questionMapper Mapper for converting between question entities and DTOs.
     * @param quizRepository Repository for quiz entities.
     * @param userRepository Repository for user entities.
     * @param categoryRepository Repository for category entities.
     * @param quizMapper Mapper for converting between quiz entities and DTOs.
     */
    @Autowired
    public QuizService(
    QuestionRepository questionRepository, 
    QuestionMapper questionMapper, 
    QuizRepository quizRepository, 
    UserRepository userRepository,
    CategoryRepository categoryRepository, 
    QuizMapper quizMapper) {
        this.questionMapper = questionMapper;
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.quizMapper = quizMapper;
    }

    /**
     * Creates a new quiz based on the provided {@link QuizCreateDTO}.
     *
     * @param quizCreateDTO Data Transfer Object containing the details for the new quiz.
     * @return The created {@link QuizDTO} containing the details of the newly created quiz.
     * @throws CreatorNotFoundException if the specified creator does not exist.
     * @throws CategoryNotFoundException if the specified category does not exist.
     */
    @Transactional
    public QuizDTO createQuiz(QuizCreateDTO quizCreateDTO) {
        User creator = userRepository.findById(quizCreateDTO.getCreatorId())
                .orElseThrow(() -> new CreatorNotFoundException(quizCreateDTO.getCreatorId().toString()));
        Category category = null;
        if (quizCreateDTO.getCategoryId() != null) {
            category = categoryRepository.findById(quizCreateDTO.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException( quizCreateDTO.getCategoryId()));
        }

        Quiz quiz = quizMapper.toQuiz(quizCreateDTO, creator);
        quiz.setCategory(category); // This will be null if the category isn't set

        if (quizCreateDTO.getQuestions() != null) {
            for (QuestionCreateDTO questionCreateDTO : quizCreateDTO.getQuestions()) {
                Question question = questionMapper.toQuestion(questionCreateDTO, quiz);
                quiz.getQuestions().add(question);
            }
        }

        quiz = quizRepository.save(quiz);

        return quizMapper.toQuizDTO(quiz);
    }

    /**
     * Retrieves all quizzes available in the system.
     *
     * @return A list of {@link QuizDTO} representing all quizzes.
     */
    @Transactional(readOnly = true)
    public List<QuizDTO> getAllQuizzes() {
        return quizRepository.findAll().stream()
                .map(quizMapper::toQuizDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific quiz by its ID.
     *
     * @param id The unique identifier of the quiz to retrieve.
     * @return The {@link QuizDTO} of the requested quiz.
     * @throws QuizNotFoundException if no quiz with the specified ID exists.
     */
    @Transactional(readOnly = true)
    public QuizDTO getQuizById(UUID id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found with id: " + id));
        return quizMapper.toQuizDTO(quiz);
    }

    /**
     * Updates an existing quiz identified by its ID with new details provided in the {@link QuizDTO}.
     *
     * @param id The ID of the quiz to update.
     * @param quizUpdateDTO The data transfer object containing the updated quiz details.
     * @return The updated {@link QuizDTO} with updated quiz details.
     * @throws QuizNotFoundException if no quiz with the specified ID exists.
     */
    @Transactional
public QuizDTO updateQuiz(UUID id, QuizDTO quizUpdateDTO) {
    Quiz quiz = quizRepository.findById(id)
            .orElseThrow(() -> new QuizNotFoundException("Quiz not found with id: " + id));

    // Assuming QuizMapper's updateQuizFromDTO now handles difficulty
    quizMapper.updateQuizFromDTO(quizUpdateDTO, quiz);

    quiz = quizRepository.save(quiz);
    return quizMapper.toQuizDTO(quiz);
}


    /**
     * Deletes a quiz identified by its ID.
     *
     * @param id The UUID of the quiz to delete.
     * @throws QuizNotFoundException if no quiz with the specified ID exists.
     */
    public void deleteQuiz(UUID id) {
        if (!quizRepository.existsById(id)) {
            throw new QuizNotFoundException("Quiz not found with id: " + id);
        }
        quizRepository.deleteById(id);
    }

    public QuizDTO setImageForQuiz(UUID quizId, Image image) {
    Quiz quiz = quizRepository.findById(quizId)
            .orElseThrow(() -> new QuizNotFoundException("Quiz not found with id: " + quizId));
    quiz.setImage(image);
    quiz = quizRepository.save(quiz);
    return quizMapper.toQuizDTO(quiz);
    }

    public List<QuizDTO> getPublicQuizzes() {
    return quizRepository.findAllByIsPublicTrue().stream()
            .map(quizMapper::toQuizDTO)
            .collect(Collectors.toList());
    }
}