package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.dto.QuizCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuizDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.QuizNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.mapper.QuizMapper;
import edu.ntnu.idatt2105.SpringbootBackend.model.Category;
import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.repository.CategoryRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.QuizRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final QuizMapper quizMapper;

    @Autowired
    public QuizService(QuizRepository quizRepository, UserRepository userRepository,
                       CategoryRepository categoryRepository, QuizMapper quizMapper) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.quizMapper = quizMapper;
    }

    @Transactional
    public QuizDTO createQuiz(QuizCreateDTO quizCreateDTO) {
        User creator = userRepository.findById(quizCreateDTO.getCreatorId())
                .orElseThrow(() -> new RuntimeException("Creator not found with id: " + quizCreateDTO.getCreatorId()));
        Category category = null;
        if (quizCreateDTO.getCategoryId() != null) {
            category = categoryRepository.findById(quizCreateDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + quizCreateDTO.getCategoryId()));
        }

        Quiz quiz = quizMapper.toQuiz(quizCreateDTO, creator);
        quiz.setCategory(category); // This will be null if the category isn't set

        quiz = quizRepository.save(quiz);

        return quizMapper.toQuizDTO(quiz);
    }

    public List<QuizDTO> getAllQuizzes() {
        return quizRepository.findAll().stream()
                .map(quizMapper::toQuizDTO)
                .collect(Collectors.toList());
    }

    public QuizDTO getQuizById(UUID id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found with id: " + id));
        return quizMapper.toQuizDTO(quiz);
    }

    @Transactional
    public QuizDTO updateQuiz(UUID id, QuizDTO quizUpdateDTO) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found with id: " + id));
        // If category update is allowed, fetch and set the category as done in createQuiz

        // Apply updates from quizUpdateDTO to quiz
        quizMapper.updateQuizFromDTO(quizUpdateDTO, quiz);

        // Save the updated quiz
        quiz = quizRepository.save(quiz);

        // Convert updated Quiz entity to QuizDTO and return
        return quizMapper.toQuizDTO(quiz);
    }

    public void deleteQuiz(UUID id) {
        if (!quizRepository.existsById(id)) {
            throw new QuizNotFoundException("Quiz not found with id: " + id);
        }
        quizRepository.deleteById(id);
    }
}
