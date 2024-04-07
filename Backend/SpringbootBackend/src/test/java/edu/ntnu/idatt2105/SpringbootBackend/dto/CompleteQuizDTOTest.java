package edu.ntnu.idatt2105.SpringbootBackend.dto;

import edu.ntnu.idatt2105.SpringbootBackend.model.Difficulty;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CompleteQuizDTOTest {

    @Test
    void testNoArgsConstructor() {
        CompleteQuizDTO quiz = new CompleteQuizDTO();
        assertNotNull(quiz);
    }

    @Test
    void testAllArgsConstructor() {
        UUID creatorId = UUID.randomUUID();
        Set<CompleteQuestionDTO> questions = new HashSet<>();
        questions.add(new CompleteQuestionDTO()); // Add a mock question

        CompleteQuizDTO quiz = new CompleteQuizDTO();
        quiz.setTitle("Sport Quiz");
        quiz.setCreatorId(creatorId);
        quiz.setDifficulty(Difficulty.EASY);
        quiz.setPublic(true);
        quiz.setQuestions(questions);

        assertNotNull(quiz);
        assertEquals("Sport Quiz", quiz.getTitle());
        assertEquals(creatorId, quiz.getCreatorId());
        assertEquals(Difficulty.EASY, quiz.getDifficulty());
        assertTrue(quiz.isPublic());
        assertEquals(1, quiz.getQuestions().size());
    }

    @Test
    void testCollectionManipulation() {
        CompleteQuizDTO quiz = new CompleteQuizDTO();
        quiz.setQuestions(new HashSet<>());

        CompleteQuestionDTO question = new CompleteQuestionDTO();
        quiz.getQuestions().add(question);

        assertEquals(1, quiz.getQuestions().size());

        quiz.getQuestions().remove(question);
        assertTrue(quiz.getQuestions().isEmpty());
    }

    @Test
    void testOptionalFieldsHandling() {
        CompleteQuizDTO quiz = new CompleteQuizDTO();
        quiz.setImageName("quiz_image.png");
        quiz.setImageType("png");
        quiz.setImageData("base64String");

        assertNotNull(quiz.getImageName());
        assertNotNull(quiz.getImageType());
        assertNotNull(quiz.getImageData());

        // Testing optional fields by setting them to null
        quiz.setImageName(null);
        quiz.setImageType(null);
        quiz.setImageData(null);

        assertNull(quiz.getImageName());
        assertNull(quiz.getImageType());
        assertNull(quiz.getImageData());
    }
}
