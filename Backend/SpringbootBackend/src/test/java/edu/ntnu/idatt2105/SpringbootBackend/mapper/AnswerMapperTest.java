package edu.ntnu.idatt2105.SpringbootBackend.mapper;

import edu.ntnu.idatt2105.SpringbootBackend.dto.AnswerCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.AnswerDTO;
import edu.ntnu.idatt2105.SpringbootBackend.model.Answer;
import edu.ntnu.idatt2105.SpringbootBackend.model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class AnswerMapperTest {

    private AnswerMapper answerMapper;
    private Question mockQuestion;

    @BeforeEach
    void setUp() {
        answerMapper = new AnswerMapper();
        mockQuestion = new Question();
        mockQuestion.setId(UUID.randomUUID());
    }

    @Test
    void testToDTOWithNull() {
        assertNull(answerMapper.toDTO(null));
    }

    @Test
    void testToDTO() {
        Answer answer = new Answer();
        answer.setId(UUID.randomUUID());
        answer.setText("Yes");
        answer.setCorrect(true);
        answer.setQuestion(mockQuestion);

        AnswerDTO dto = answerMapper.toDTO(answer);

        assertNotNull(dto);
        assertEquals(answer.getId(), dto.getId());
        assertEquals(answer.getText(), dto.getText());
        assertEquals(answer.isCorrect(), dto.isCorrect());
    }

    @Test
    void testToEntityWithNull() {
        assertNull(answerMapper.toEntity(null, null));
    }

    @Test
    void testToEntity() {
        AnswerCreateDTO dto = new AnswerCreateDTO("Yes", true);

        Answer answer = answerMapper.toEntity(dto, mockQuestion);

        assertNotNull(answer);
        assertEquals(dto.getText(), answer.getText());
        assertEquals(dto.isCorrect(), answer.isCorrect());
        assertEquals(mockQuestion, answer.getQuestion());
    }

    @Test
    void testUpdateEntityFromDTO() {
        Answer existingAnswer = new Answer();
        existingAnswer.setText("No");
        existingAnswer.setCorrect(false);

        AnswerDTO dto = new AnswerDTO();
        dto.setText("Yes");
        dto.setCorrect(true);

        Answer updatedAnswer = answerMapper.updateEntityFromDTO(dto, existingAnswer);

        assertNotNull(updatedAnswer);
        assertEquals(dto.getText(), updatedAnswer.getText());
        assertEquals(dto.isCorrect(), updatedAnswer.isCorrect());
    }
}
