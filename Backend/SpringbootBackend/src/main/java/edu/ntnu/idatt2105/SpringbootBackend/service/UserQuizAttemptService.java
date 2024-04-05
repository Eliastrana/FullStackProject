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

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserQuizAttemptService {

    private final UserQuizAttemptRepository userQuizAttemptRepository;
    private final UserQuizAttemptMapper userQuizAttemptMapper;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;

    @Autowired
    public UserQuizAttemptService(UserQuizAttemptRepository userQuizAttemptRepository, UserQuizAttemptMapper userQuizAttemptMapper, UserRepository userRepository, QuizRepository quizRepository) {
        this.userQuizAttemptRepository = userQuizAttemptRepository;
        this.userQuizAttemptMapper = userQuizAttemptMapper;
        this.userRepository = userRepository;
        this.quizRepository = quizRepository;
    }

    public List<UserQuizAttemptDTO> getUserAttempts(UUID userId) {
        return userQuizAttemptRepository.findByUserId(userId).stream()
                .map(userQuizAttemptMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserQuizAttemptDTO createUserQuizAttempt(UserQuizAttemptDTO userQuizAttemptDTO) {
        User user = userRepository.findById(userQuizAttemptDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userQuizAttemptDTO.getUserId()));
        Quiz quiz = quizRepository.findById(userQuizAttemptDTO.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + userQuizAttemptDTO.getQuizId()));

        UserQuizAttempt userQuizAttempt = userQuizAttemptMapper.toEntity(userQuizAttemptDTO);
        userQuizAttempt.setUser(user);
        userQuizAttempt.setQuiz(quiz);

        userQuizAttempt = userQuizAttemptRepository.save(userQuizAttempt);
        return userQuizAttemptMapper.toDto(userQuizAttempt);
    }

    public UserQuizAttemptDTO getUserQuizAttemptById(UUID id) throws UserQuizAttemptNotFoundException {
        UserQuizAttempt userQuizAttempt = userQuizAttemptRepository.findById(id)
                .orElseThrow(() -> new UserQuizAttemptNotFoundException("UserQuizAttempt not found with id: " + id));
        return userQuizAttemptMapper.toDto(userQuizAttempt);
    }

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

    @Transactional
    public void deleteUserQuizAttempt(UUID id) {
        userQuizAttemptRepository.deleteById(id);
    }
}
