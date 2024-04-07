package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.dto.QuestionCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuestionDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.QuestionNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.QuizNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.mapper.QuestionMapper;
import edu.ntnu.idatt2105.SpringbootBackend.model.Image;
import edu.ntnu.idatt2105.SpringbootBackend.model.Question;
import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;
import edu.ntnu.idatt2105.SpringbootBackend.repository.QuestionRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.QuizRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuestionMapper questionMapper;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private QuestionService questionService;

    private Quiz quiz;
    private Question question;
    private Image image;
    private QuestionDTO questionDTO;
    private QuestionCreateDTO questionCreateDTO;

    @BeforeEach
    void setUp() {
        quiz = new Quiz();
        quiz.setId(UUID.randomUUID());

        question = new Question();
        question.setId(UUID.randomUUID());
        question.setQuiz(quiz);

        image = new Image();
        image.setId(UUID.randomUUID());

        questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());

        questionCreateDTO = new QuestionCreateDTO();
    }

    @Test
    void createQuestion_WhenQuizNotFound_ShouldThrowException() {
        UUID quizId = UUID.randomUUID();
        when(quizRepository.findById(quizId)).thenReturn(Optional.empty());

        assertThrows(QuizNotFoundException.class, () -> {
            questionService.createQuestion(quizId, questionCreateDTO);
        });
    }

    @Test
    void createQuestion_WhenSuccessful_ShouldReturnQuestionDTO() throws Exception {
        UUID quizId = quiz.getId();
        when(quizRepository.findById(quizId)).thenReturn(Optional.of(quiz));
        when(questionMapper.toQuestion(any(QuestionCreateDTO.class), any(Quiz.class))).thenReturn(question);
        when(questionRepository.save(any(Question.class))).thenReturn(question);
        when(questionMapper.toQuestionDTO(any(Question.class))).thenReturn(questionDTO);

        QuestionDTO resultDTO = questionService.createQuestion(quizId, questionCreateDTO);

        assertEquals(questionDTO, resultDTO);
        verify(questionRepository).save(any(Question.class));
    }

// Continuation from the previous `QuestionServiceTest` class

@Test
void updateQuestion_WhenQuestionNotFound_ShouldThrowException() {
    UUID questionId = UUID.randomUUID();
    when(questionRepository.findById(questionId)).thenReturn(Optional.empty());

    assertThrows(QuestionNotFoundException.class, () -> {
        questionService.updateQuestion(questionId, questionDTO);
    });
}

@Test
void updateQuestion_WhenSuccessful_ShouldReturnUpdatedQuestionDTO() throws Exception {
    when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));
    when(questionRepository.save(any(Question.class))).thenReturn(question);
    when(questionMapper.updateQuestionFromDTO(any(QuestionDTO.class), any(Question.class))).thenReturn(question);
    when(questionMapper.toQuestionDTO(any(Question.class))).thenReturn(questionDTO);

    QuestionDTO updatedQuestionDTO = questionService.updateQuestion(question.getId(), questionDTO);

    assertEquals(questionDTO.getId(), updatedQuestionDTO.getId());
    verify(questionRepository).save(question);
}

@Test
void deleteQuestion_WhenQuestionNotFound_ShouldThrowException() {
    UUID questionId = UUID.randomUUID();
    when(questionRepository.existsById(questionId)).thenReturn(false);

    assertThrows(QuestionNotFoundException.class, () -> {
        questionService.deleteQuestion(questionId);
    });
}

@Test
void deleteQuestion_WhenSuccessful_ShouldNotThrowException() {
    when(questionRepository.existsById(question.getId())).thenReturn(true);

    assertDoesNotThrow(() -> {
        questionService.deleteQuestion(question.getId());
    });

    verify(questionRepository).deleteById(question.getId());
}

@Test
void getQuestionsByQuizId_ShouldReturnListOfQuestionDTOs() {
    List<Question> questions = List.of(question);
    when(questionRepository.findAllByQuizId(quiz.getId())).thenReturn(questions);
    when(questionMapper.toQuestionDTO(any(Question.class))).thenReturn(questionDTO);

    List<QuestionDTO> questionDTOs = questionService.getQuestionsByQuizId(quiz.getId());

    assertNotNull(questionDTOs);
    assertEquals(1, questionDTOs.size());
    assertEquals(questionDTO.getId(), questionDTOs.get(0).getId());
}
}
