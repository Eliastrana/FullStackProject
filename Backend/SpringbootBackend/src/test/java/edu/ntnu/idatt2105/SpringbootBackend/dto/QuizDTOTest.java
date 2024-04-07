package edu.ntnu.idatt2105.SpringbootBackend.dto;

import edu.ntnu.idatt2105.SpringbootBackend.model.Difficulty;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class QuizDTOTest {

    @Test
    void testBuilderPattern() {
        UUID id = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        UUID creatorId = UUID.randomUUID();
        UUID imageId = UUID.randomUUID();

        QuizDTO quizDTO = QuizDTO.builder()
                .id(id)
                .title("General Knowledge")
                .description("A quiz covering a wide range of topics.")
                .difficulty(Difficulty.EASY)
                .isPublic(true)
                .categoryId(categoryId)
                .creatorId(creatorId)
                .imageId(imageId)
                .build();

        assertEquals(id, quizDTO.getId());
        assertEquals("General Knowledge", quizDTO.getTitle());
        assertEquals("A quiz covering a wide range of topics.", quizDTO.getDescription());
        assertEquals(Difficulty.EASY, quizDTO.getDifficulty());
        assertTrue(quizDTO.isPublic());
        assertEquals(categoryId, quizDTO.getCategoryId());
        assertEquals(creatorId, quizDTO.getCreatorId());
        assertEquals(imageId, quizDTO.getImageId());
    }

    @Test
    void testOptionalField() {
        QuizDTO quizDTO = QuizDTO.builder()
                .id(UUID.randomUUID())
                .title("Science Quiz")
                .description("Quiz about basic science topics")
                .difficulty(Difficulty.MEDIUM)
                .isPublic(false)
                .creatorId(UUID.randomUUID())
                .build();

        assertNotNull(quizDTO);
        assertNull(quizDTO.getImageId()); // Optional field not set should be null
    }
    @Test
    void testAllArgsConstructor() {
        UUID id = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        UUID creatorId = UUID.randomUUID();
        UUID imageId = UUID.randomUUID();

        QuizDTO quizDTO = new QuizDTO(id, "General Knowledge", "A quiz covering a wide range of topics.", Difficulty.EASY, true, categoryId, creatorId, imageId);

        assertEquals(id, quizDTO.getId());
        assertEquals("General Knowledge", quizDTO.getTitle());
        assertEquals("A quiz covering a wide range of topics.", quizDTO.getDescription());
        assertEquals(Difficulty.EASY, quizDTO.getDifficulty());
        assertTrue(quizDTO.isPublic());
        assertEquals(categoryId, quizDTO.getCategoryId());
        assertEquals(creatorId, quizDTO.getCreatorId());
        assertEquals(imageId, quizDTO.getImageId());
    }
    @Test
    void testHandlingNullValues() {
        QuizDTO quizDTO = QuizDTO.builder()
                .title(null)
                .description(null)
                .build();

        assertNull(quizDTO.getTitle());
        assertNull(quizDTO.getDescription());
        // Verify that non-set UUIDs and other nullable fields are indeed null
        assertNull(quizDTO.getCreatorId());
        assertNull(quizDTO.getCategoryId());
        assertNull(quizDTO.getImageId());
    }

    @Test
    void testEdgeCasesForStringFields() {
        QuizDTO quizDTO = QuizDTO.builder()
                .title(" ")
                .description("    ")
                .build();

        assertEquals(" ", quizDTO.getTitle());
        assertEquals("    ", quizDTO.getDescription());
        // This test verifies that the DTO does not inherently trim strings or validate content
    }

    @Test
    void testBooleanFieldBehavior() {
        QuizDTO quizDTO = QuizDTO.builder()
                .isPublic(true)
                .build();

        assertTrue(quizDTO.isPublic());

        quizDTO = QuizDTO.builder()
                .isPublic(false)
                .build();

        assertFalse(quizDTO.isPublic());
    }
}
