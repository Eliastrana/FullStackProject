package edu.ntnu.idatt2105.SpringbootBackend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnswerTest {

    private Answer answer;

    @BeforeEach
    public void setup() {
        answer = new Answer();
    }

    @Test
    public void testId() {
        UUID id = UUID.randomUUID();
        answer.setId(id);
        assertEquals(id, answer.getId());
    }

    @Test
    public void testText() {
        String text = "This is a test answer.";
        answer.setText(text);
        assertEquals(text, answer.getText());
    }

    @Test
    public void testIsCorrect() {
        answer.setCorrect(true);
        assertTrue(answer.isCorrect());

        answer.setCorrect(false);
        assertFalse(answer.isCorrect());
    }

    @Test
    public void testQuestion() {
        Question question = new Question();
        answer.setQuestion(question);
        assertEquals(question, answer.getQuestion());
    }
}