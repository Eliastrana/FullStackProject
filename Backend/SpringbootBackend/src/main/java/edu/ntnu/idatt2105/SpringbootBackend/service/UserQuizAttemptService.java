package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.dto.UserQuizAttemptDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.UserQuizAttemptNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.model.UserQuizAttempt;
import edu.ntnu.idatt2105.SpringbootBackend.repository.QuizRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.UserQuizAttemptRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import edu.ntnu.idatt2105.SpringbootBackend.mapper.UserQuizAttemptMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The {@code UserQuizAttemptService} class manages user quiz attempts,
 * providing functionality to create, retrieve, update, and delete attempts.
 * It leverages the {@link UserQuizAttemptRepository} for persistence,
 * {@link UserQuizAttemptMapper} for entity to DTO conversions, and repositories
 * for {@link User} and {@link Quiz} to validate foreign keys.
 *
 * @author Vegard Johnsen, sander rom skofsrud
 * @version 1.0
 * @since 1.0
 * @see UserQuizAttemptRepository
 * @see UserQuizAttemptMapper
 * @see User
 * @see Quiz
 */

@Service
public class UserQuizAttemptService {

    private final UserQuizAttemptRepository userQuizAttemptRepository;
    private final UserQuizAttemptMapper userQuizAttemptMapper;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;

    /**
     * Constructs a {@code UserQuizAttemptService} with necessary dependencies.
     *
     * @param userQuizAttemptRepository Repository for user quiz attempt entities.
     * @param userQuizAttemptMapper Mapper for converting between entity and DTO.
     * @param userRepository Repository for user entities.
     * @param quizRepository Repository for quiz entities.
     */
    @Autowired
    public UserQuizAttemptService(UserQuizAttemptRepository userQuizAttemptRepository, UserQuizAttemptMapper userQuizAttemptMapper, UserRepository userRepository, QuizRepository quizRepository) {
        this.userQuizAttemptRepository = userQuizAttemptRepository;
        this.userQuizAttemptMapper = userQuizAttemptMapper;
        this.userRepository = userRepository;
        this.quizRepository = quizRepository;
    }

    /**
     * Retrieves all quiz attempts made by a specific user.
     *
     * @param userId The unique identifier of the user.
     * @return A list of {@link UserQuizAttemptDTO} representing the user's attempts.
     */
    public List<UserQuizAttemptDTO> getUserAttempts(UUID userId) {
        return userQuizAttemptRepository.findByUserId(userId).stream()
                .map(userQuizAttemptMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new user quiz attempt with the details provided in {@link UserQuizAttemptDTO}.
     *
     * @param userQuizAttemptDTO Data Transfer Object containing attempt details.
     * @return The created {@link UserQuizAttemptDTO}.
     * @throws RuntimeException if the user or quiz referenced does not exist.
     */
    @Transactional
    public UserQuizAttemptDTO createUserQuizAttempt(UserQuizAttemptDTO userQuizAttemptDTO) {
        User user = userRepository.findById(userQuizAttemptDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userQuizAttemptDTO.getUserId()));
        Quiz quiz = quizRepository.findById(userQuizAttemptDTO.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + userQuizAttemptDTO.getQuizId()));

        UserQuizAttempt userQuizAttempt = userQuizAttemptMapper.toEntity(userQuizAttemptDTO);
        userQuizAttempt.setUser(user);
        userQuizAttempt.setQuiz(quiz);
        userQuizAttempt.setTime(LocalDateTime.now());

        userQuizAttempt = userQuizAttemptRepository.save(userQuizAttempt);
        return userQuizAttemptMapper.toDto(userQuizAttempt);
    }

    /**
     * Retrieves a specific user quiz attempt by its unique identifier.
     *
     * @param id The unique identifier of the attempt.
     * @return The {@link UserQuizAttemptDTO} representing the attempt.
     * @throws UserQuizAttemptNotFoundException if the attempt is not found.
     */
    public UserQuizAttemptDTO getUserQuizAttemptById(UUID id) throws UserQuizAttemptNotFoundException {
        UserQuizAttempt userQuizAttempt = userQuizAttemptRepository.findById(id)
                .orElseThrow(() -> new UserQuizAttemptNotFoundException("UserQuizAttempt not found with id: " + id));
        return userQuizAttemptMapper.toDto(userQuizAttempt);
    }

    /**
     * Updates an existing user quiz attempt with new details provided in {@link UserQuizAttemptDTO}.
     *
     * @param id The unique identifier of the attempt to update.
     * @param userQuizAttemptDTO Data Transfer Object containing new attempt details.
     * @return The updated {@link UserQuizAttemptDTO}.
     * @throws UserQuizAttemptNotFoundException if the attempt is not found.
     */
    @Transactional
    public UserQuizAttemptDTO updateUserQuizAttempt(UUID id, UserQuizAttemptDTO userQuizAttemptDTO) throws UserQuizAttemptNotFoundException {
        UserQuizAttempt userQuizAttempt = userQuizAttemptRepository.findById(id)
                .orElseThrow(() -> new UserQuizAttemptNotFoundException("UserQuizAttempt not found with id: " + id));
        
        // Update fields from DTO
        userQuizAttempt.setScore(userQuizAttemptDTO.getScore());
        // Further updates as necessary

        userQuizAttempt = userQuizAttemptRepository.save(userQuizAttempt);
        return userQuizAttemptMapper.toDto(userQuizAttempt);
    }

    /**
     * Deletes a user quiz attempt by its unique identifier.
     *
     * @param id The unique identifier of the attempt to delete.
     */
    @Transactional
    public void deleteUserQuizAttempt(UUID id) {
        userQuizAttemptRepository.deleteById(id);
    }
}
