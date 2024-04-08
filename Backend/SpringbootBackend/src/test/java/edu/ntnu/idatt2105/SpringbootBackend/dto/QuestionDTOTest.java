package edu.ntnu.idatt2105.SpringbootBackend.dto;

import edu.ntnu.idatt2105.SpringbootBackend.model.QuestionType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class QuestionDTOTest {

    @Test
    void testNoArgsConstructor() {
        QuestionDTO question = new QuestionDTO();
        assertNotNull(question);
    }

    @Test
    void testAllArgsConstructor() {
        UUID id = UUID.randomUUID();
        UUID quizId = UUID.randomUUID();
        String text = "Who wrote 'To Kill a Mockingbird'?";
        QuestionType questionType = QuestionType.MULTIPLE_CHOICE;
        String multimediaLink = "https://example.com/video.mp4";
        LocalDateTime creationDate = LocalDateTime.now();
        Set<UUID> tags = new HashSet<>();
        tags.add(UUID.randomUUID());
        UUID imageId = UUID.randomUUID();

        QuestionDTO question = new QuestionDTO(id, quizId, text, questionType, multimediaLink, creationDate, tags, imageId);

        assertEquals(id, question.getId());
        assertEquals(quizId, question.getQuizId());
        assertEquals(text, question.getText());
        assertEquals(questionType, question.getQuestionType());
        assertEquals(multimediaLink, question.getMultimediaLink());
        assertEquals(creationDate, question.getCreationDate());
        assertEquals(tags.size(), question.getTags().size());
        assertTrue(question.getTags().containsAll(tags));
        assertEquals(imageId, question.getImageId());
    }

    @Test
    void testOptionalFields() {
        QuestionDTO question = new QuestionDTO();
        question.setText("What is the capital of Norway?");
        question.setQuestionType(QuestionType.STUDY);
        question.setCreationDate(LocalDateTime.now());

        // MultimediaLink and imageId are optional
        assertNull(question.getMultimediaLink());
        assertNull(question.getImageId());

        // Test setting and then clearing an optional field
        question.setMultimediaLink("https://example.com/image.png");
        assertNotNull(question.getMultimediaLink());
        question.setMultimediaLink(null);
        assertNull(question.getMultimediaLink());
    }

    @Test
    void testCollectionBehavior() {
        QuestionDTO question = new QuestionDTO();
        Set<UUID> initialTags = new HashSet<>();
        initialTags.add(UUID.randomUUID());
        question.setTags(initialTags);

        assertEquals(1, question.getTags().size());

        // Test adding another tag
        UUID newTag = UUID.randomUUID();
        question.getTags().add(newTag);

        assertEquals(2, question.getTags().size());
        assertTrue(question.getTags().contains(newTag));

        // Test removal of a tag
        question.getTags().remove(newTag);
        assertEquals(1, question.getTags().size());
        assertFalse(question.getTags().contains(newTag));
    }
}
