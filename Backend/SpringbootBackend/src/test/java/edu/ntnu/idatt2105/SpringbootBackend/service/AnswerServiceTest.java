package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.dto.AnswerDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.AnswerNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.QuestionNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.model.Answer;
import edu.ntnu.idatt2105.SpringbootBackend.model.Question;
import edu.ntnu.idatt2105.SpringbootBackend.repository.AnswerRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AnswerServiceTest {

    @InjectMocks
    private AnswerService answerService;

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private QuestionRepository questionRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAnswer() throws QuestionNotFoundException {
        UUID questionId = UUID.randomUUID();
        Question question = new Question();
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));

        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setText("Test Answer");
        answerDTO.setCorrect(true);

        Answer answer = new Answer();
        answer.setText(answerDTO.getText());
        answer.setCorrect(answerDTO.isCorrect());
        answer.setQuestion(question);

        when(answerRepository.save(any(Answer.class))).thenReturn(answer);

        AnswerDTO createdAnswerDTO = answerService.createAnswer(questionId, answerDTO);

        assertEquals(answerDTO.getText(), createdAnswerDTO.getText());
        assertEquals(answerDTO.isCorrect(), createdAnswerDTO.isCorrect());
    }

    @Test
    public void testCreateAnswer_QuestionNotFound() {
        UUID questionId = UUID.randomUUID();
        when(questionRepository.findById(questionId)).thenReturn(Optional.empty());

        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setText("Test Answer");
        answerDTO.setCorrect(true);

        assertThrows(QuestionNotFoundException.class, () -> answerService.createAnswer(questionId, answerDTO));
    }

    @Test
public void testGetAnswerById() throws AnswerNotFoundException {
    UUID answerId = UUID.randomUUID();
    Answer answer = new Answer();
    answer.setText("Test Answer");
    answer.setCorrect(true);
    when(answerRepository.findById(answerId)).thenReturn(Optional.of(answer));

    AnswerDTO answerDTO = answerService.getAnswerById(answerId);

    assertEquals(answer.getText(), answerDTO.getText());
    assertEquals(answer.isCorrect(), answerDTO.isCorrect());
}

@Test
public void testGetAnswerById_NotFound() {
    UUID answerId = UUID.randomUUID();
    when(answerRepository.findById(answerId)).thenReturn(Optional.empty());

    assertThrows(AnswerNotFoundException.class, () -> answerService.getAnswerById(answerId));
}

@Test
public void testUpdateAnswer() throws AnswerNotFoundException {
    UUID answerId = UUID.randomUUID();
    Answer answer = new Answer();
    answer.setText("Test Answer");
    answer.setCorrect(true);
    when(answerRepository.findById(answerId)).thenReturn(Optional.of(answer));

    AnswerDTO newAnswerDTO = new AnswerDTO();
    newAnswerDTO.setText("Updated Answer");
    newAnswerDTO.setCorrect(false);

    when(answerRepository.save(any(Answer.class))).thenAnswer(invocation -> invocation.getArgument(0));

    AnswerDTO updatedAnswerDTO = answerService.updateAnswer(answerId, newAnswerDTO);

    assertEquals(newAnswerDTO.getText(), updatedAnswerDTO.getText());
    assertEquals(newAnswerDTO.isCorrect(), updatedAnswerDTO.isCorrect());
}

@Test
public void testUpdateAnswer_NotFound() {
    UUID answerId = UUID.randomUUID();
    when(answerRepository.findById(answerId)).thenReturn(Optional.empty());

    AnswerDTO newAnswerDTO = new AnswerDTO();
    newAnswerDTO.setText("Updated Answer");
    newAnswerDTO.setCorrect(false);

    assertThrows(AnswerNotFoundException.class, () -> answerService.updateAnswer(answerId, newAnswerDTO));
}

@Test
public void testDeleteAnswer() throws AnswerNotFoundException {
    UUID answerId = UUID.randomUUID();
    when(answerRepository.existsById(answerId)).thenReturn(true);

    answerService.deleteAnswer(answerId);

    verify(answerRepository, times(1)).deleteById(answerId);
}

@Test
public void testDeleteAnswer_NotFound() {
    UUID answerId = UUID.randomUUID();
    when(answerRepository.existsById(answerId)).thenReturn(false);

    assertThrows(AnswerNotFoundException.class, () -> answerService.deleteAnswer(answerId));
}


}