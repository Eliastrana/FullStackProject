package edu.ntnu.idatt2105.SpringbootBackend.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnswerCreateDTOTest {

    @Test
    void testNoArgsConstructor() {
        AnswerCreateDTO dto = new AnswerCreateDTO();
        assertNotNull(dto);
    }

    @Test
    void testAllArgsConstructor() {
        AnswerCreateDTO dto = new AnswerCreateDTO("Norway", true);
        assertNotNull(dto);
        assertEquals("Norway", dto.getText());
        assertTrue(dto.isCorrect());
    }

    @Test
    void testSettersAndGetters() {
        AnswerCreateDTO dto = new AnswerCreateDTO();
        dto.setText("Sweden");
        dto.setCorrect(false);
        
        assertEquals("Sweden", dto.getText());
        assertFalse(dto.isCorrect());
    }
}
