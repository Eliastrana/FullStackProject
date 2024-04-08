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

@Service
public class QuizService {

    private final QuestionMapper questionMapper;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final QuizMapper quizMapper;

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

    @Transactional(readOnly = true)
    public List<QuizDTO> getAllQuizzes() {
        return quizRepository.findAll().stream()
                .map(quizMapper::toQuizDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public QuizDTO getQuizById(UUID id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found with id: " + id));
        return quizMapper.toQuizDTO(quiz);
    }

    @Transactional
public QuizDTO updateQuiz(UUID id, QuizDTO quizUpdateDTO) {
    Quiz quiz = quizRepository.findById(id)
            .orElseThrow(() -> new QuizNotFoundException("Quiz not found with id: " + id));

    // Assuming QuizMapper's updateQuizFromDTO now handles difficulty
    quizMapper.updateQuizFromDTO(quizUpdateDTO, quiz);

    quiz = quizRepository.save(quiz);
    return quizMapper.toQuizDTO(quiz);
}


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