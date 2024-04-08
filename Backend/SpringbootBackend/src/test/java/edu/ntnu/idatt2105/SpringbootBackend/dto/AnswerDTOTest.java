package edu.ntnu.idatt2105.SpringbootBackend.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AnswerDTOTest {

    @Test
    void testNoArgsConstructor() {
        AnswerDTO dto = new AnswerDTO();
        assertNotNull(dto);
    }

    @Test
    void testAllArgsConstructor() {
        UUID id = UUID.randomUUID();
        AnswerDTO dto = new AnswerDTO(id, "Norway", true);
        
        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals("Norway", dto.getText());
        assertTrue(dto.isCorrect());
    }

    @Test
    void testSettersAndGetters() {
        AnswerDTO dto = new AnswerDTO();
        UUID id = UUID.randomUUID();
        dto.setId(id);
        dto.setText("Sweden");
        dto.setCorrect(false);
        
        assertEquals(id, dto.getId());
        assertEquals("Sweden", dto.getText());
        assertFalse(dto.isCorrect());
    }
}
