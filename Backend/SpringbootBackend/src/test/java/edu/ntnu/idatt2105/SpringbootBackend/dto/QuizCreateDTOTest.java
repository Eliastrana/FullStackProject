package edu.ntnu.idatt2105.SpringbootBackend.dto;

import edu.ntnu.idatt2105.SpringbootBackend.model.Difficulty;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class QuizCreateDTOTest {

    @Test
    void testNoArgsConstructor() {
        QuizCreateDTO dto = new QuizCreateDTO();
        assertNotNull(dto);
    }

    @Test
    void testAllArgsConstructor() {
        UUID creatorId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        UUID imageId = UUID.randomUUID();
        List<QuestionCreateDTO> questions = new ArrayList<>();

        QuizCreateDTO dto = new QuizCreateDTO(
                "General Knowledge",
                "A quiz covering a wide range of topics.",
                Difficulty.EASY,
                true,
                creatorId,
                categoryId,
                questions,
                imageId
        );

        assertEquals("General Knowledge", dto.getTitle());
        assertEquals("A quiz covering a wide range of topics.", dto.getDescription());
        assertEquals(Difficulty.EASY, dto.getDifficulty());
        assertTrue(dto.isPublic());
        assertEquals(creatorId, dto.getCreatorId());
        assertEquals(categoryId, dto.getCategoryId());
        assertEquals(0, dto.getQuestions().size()); // No questions added yet
        assertEquals(imageId, dto.getImageId());
    }

    @Test
    void testBuilderPattern() {
        QuizCreateDTO dto = QuizCreateDTO.builder()
                .title("Science Quiz")
                .description("Quiz about basic science topics")
                .difficulty(Difficulty.MEDIUM)
                .isPublic(false)
                .creatorId(UUID.randomUUID())
                .categoryId(UUID.randomUUID())
                .build();

        assertEquals("Science Quiz", dto.getTitle());
        assertEquals("Quiz about basic science topics", dto.getDescription());
        assertEquals(Difficulty.MEDIUM, dto.getDifficulty());
        assertFalse(dto.isPublic());
        assertNotNull(dto.getCreatorId());
        assertNotNull(dto.getCategoryId());
        assertNull(dto.getQuestions()); // Optional field not set
        assertNull(dto.getImageId()); // Optional field not set
    }
}
