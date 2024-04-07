package edu.ntnu.idatt2105.SpringbootBackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import edu.ntnu.idatt2105.SpringbootBackend.dto.AnswerDTO;
import edu.ntnu.idatt2105.SpringbootBackend.model.Answer;
import edu.ntnu.idatt2105.SpringbootBackend.model.Question;
import edu.ntnu.idatt2105.SpringbootBackend.repository.AnswerRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class AnswerServiceTest {

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private AnswerService answerService;

    @Autowired
    private Question question;

    @Autowired
    private Answer answer;

    @Autowired
    private AnswerDTO answerDTO;

    @BeforeEach
    void setUp() {
        // Initialize your test data here
        UUID questionId = UUID.randomUUID();
        question = new Question();
        question.setId(questionId);

        answer = new Answer();
        answer.setId(UUID.randomUUID());
        answer.setQuestion(question);
        answer.setText("Test Answer");
        answer.setCorrect(true);

        answerDTO = new AnswerDTO(answer.getId(), answer.getText(), answer.isCorrect());
        
        when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));
        when(answerRepository.save(any(Answer.class))).thenReturn(answer);
    }

@Test
void createAnswer_Success() throws Exception {
    // Given
    UUID questionId = question.getId();
    AnswerDTO newAnswerDTO = new AnswerDTO(null, "Test Answer", true);

    // When
    AnswerDTO createdAnswerDTO = answerService.createAnswer(questionId, newAnswerDTO);

    // Then
    assertNotNull(createdAnswerDTO.getId());
    assertEquals(newAnswerDTO.getText(), createdAnswerDTO.getText());
    assertEquals(newAnswerDTO.isCorrect(), createdAnswerDTO.isCorrect());
}

@Test
void updateAnswer_Success() throws Exception {
    // Given
    UUID answerId = answer.getId();
    AnswerDTO updatedAnswerDTO = new AnswerDTO(answerId, "Updated Answer Text", false);
    when(answerRepository.findById(answerId)).thenReturn(Optional.of(answer));

    // When
    AnswerDTO resultDTO = answerService.updateAnswer(answerId, updatedAnswerDTO);

    // Then
    assertNotNull(resultDTO);
    assertEquals(updatedAnswerDTO.getText(), resultDTO.getText());
    assertEquals(updatedAnswerDTO.isCorrect(), resultDTO.isCorrect());
}
@Test
void deleteAnswer_Success() throws Exception {
    // Given
    UUID answerId = answer.getId();
    when(answerRepository.existsById(answerId)).thenReturn(true);

    // When
    answerService.deleteAnswer(answerId);

    // Then
    verify(answerRepository, times(1)).deleteById(answerId);
    }
}
