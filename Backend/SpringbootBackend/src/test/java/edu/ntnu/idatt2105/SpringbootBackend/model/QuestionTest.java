package edu.ntnu.idatt2105.SpringbootBackend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class QuestionTest {

    private Question question;

    @BeforeEach
    public void setup() {
        question = new Question();
    }

    @Test
    public void testId() {
        UUID id = UUID.randomUUID();
        question.setId(id);
        assertEquals(id, question.getId());
    }

    @Test
    public void testText() {
        String text = "This is a test question.";
        question.setText(text);
        assertEquals(text, question.getText());
    }

    @Test
    public void testQuestionType() {
        QuestionType questionType = QuestionType.MULTIPLE_CHOICE;
        question.setQuestionType(questionType);
        assertEquals(questionType, question.getQuestionType());
    }

    @Test
    public void testMultimediaLink() {
        String multimediaLink = "http://example.com/test.jpg";
        question.setMultimediaLink(multimediaLink);
        assertEquals(multimediaLink, question.getMultimediaLink());
    }

    @Test
    public void testCreationDate() {
        LocalDateTime creationDate = LocalDateTime.now();
        question.setCreationDate(creationDate);
        assertEquals(creationDate, question.getCreationDate());
    }

    @Test
    public void testTags() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag());
        question.setTags(tags);
        assertNotNull(question.getTags());
        assertEquals(tags.size(), question.getTags().size());
    }

    @Test
    public void testAnswers() {
        Set<Answer> answers = new HashSet<>();
        answers.add(new Answer());
        question.setAnswers(answers);
        assertNotNull(question.getAnswers());
        assertEquals(answers.size(), question.getAnswers().size());
    }
}
