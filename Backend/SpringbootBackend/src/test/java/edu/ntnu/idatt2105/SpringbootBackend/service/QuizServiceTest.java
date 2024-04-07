package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.dto.QuizCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuizDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.CreatorNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.QuizNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.mapper.QuizMapper;
import edu.ntnu.idatt2105.SpringbootBackend.model.Category;
import edu.ntnu.idatt2105.SpringbootBackend.model.Image;
import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.repository.CategoryRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.QuizRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private QuizMapper quizMapper;

    @InjectMocks
    private QuizService quizService;

    private User creator;
    private Category category;
    private Quiz quiz;
    private QuizCreateDTO quizCreateDTO;
    private QuizDTO quizDTO;

    @BeforeEach
    void setUp() {
        creator = new User();
        creator.setId(UUID.randomUUID());
        category = new Category();
        category.setId(UUID.randomUUID());
        quiz = new Quiz();
        quiz.setId(UUID.randomUUID());
        quiz.setCreator(creator);
        quiz.setCategory(category);

        quizCreateDTO = new QuizCreateDTO();
        quizCreateDTO.setCreatorId(creator.getId());
        quizCreateDTO.setCategoryId(category.getId());

        quizDTO = QuizDTO.builder()
                .id(quiz.getId())
                .creatorId(creator.getId())
                .categoryId(category.getId())
                .build();
    }

    @Test
    void createQuiz_Success() {
        when(userRepository.findById(quizCreateDTO.getCreatorId())).thenReturn(Optional.of(creator));
        when(categoryRepository.findById(quizCreateDTO.getCategoryId())).thenReturn(Optional.of(category));
        when(quizMapper.toQuiz(any(QuizCreateDTO.class), any(User.class))).thenReturn(quiz);
        when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);
        when(quizMapper.toQuizDTO(any(Quiz.class))).thenReturn(quizDTO);

        QuizDTO result = quizService.createQuiz(quizCreateDTO);

        assertNotNull(result);
        assertEquals(quizDTO.getId(), result.getId());
    }

    @Test
    void createQuiz_CreatorNotFound_ShouldThrowException() {
        when(userRepository.findById(quizCreateDTO.getCreatorId())).thenReturn(Optional.empty());

        assertThrows(CreatorNotFoundException.class, () -> quizService.createQuiz(quizCreateDTO));
    }

    @Test
    void updateQuiz_Success() {
        when(quizRepository.findById(quiz.getId())).thenReturn(Optional.of(quiz));
        when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);
        when(quizMapper.toQuizDTO(any(Quiz.class))).thenReturn(quizDTO);

        QuizDTO updatedQuizDTO = quizService.updateQuiz(quiz.getId(), quizDTO);

        assertNotNull(updatedQuizDTO);
        assertEquals(quizDTO.getId(), updatedQuizDTO.getId());
    }

    @Test
    void deleteQuiz_NotFound_ShouldThrowException() {
        UUID randomId = UUID.randomUUID();
        when(quizRepository.existsById(randomId)).thenReturn(false);

        assertThrows(QuizNotFoundException.class, () -> quizService.deleteQuiz(randomId));
    }

    @Test
    void setImageForQuiz_Success() {
        Image image = new Image();
        image.setId(UUID.randomUUID());

        when(quizRepository.findById(quiz.getId())).thenReturn(Optional.of(quiz));
        when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);
        when(quizMapper.toQuizDTO(any(Quiz.class))).thenReturn(quizDTO);

        QuizDTO resultDTO = quizService.setImageForQuiz(quiz.getId(), image);

        assertNotNull(resultDTO);
        assertEquals(quizDTO.getId(), resultDTO.getId());
    }
}
