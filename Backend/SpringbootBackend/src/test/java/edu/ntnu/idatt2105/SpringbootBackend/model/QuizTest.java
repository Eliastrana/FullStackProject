package edu.ntnu.idatt2105.SpringbootBackend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class QuizTest {

    private Quiz quiz;

    @BeforeEach
    public void setup() {
        quiz = new Quiz();
    }

    @Test
    public void testId() {
        UUID id = UUID.randomUUID();
        quiz.setId(id);
        assertEquals(id, quiz.getId());
    }

    @Test
    public void testTitle() {
        String title = "Test Quiz";
        quiz.setTitle(title);
        assertEquals(title, quiz.getTitle());
    }

    @Test
    public void testDescription() {
        String description = "This is a test quiz.";
        quiz.setDescription(description);
        assertEquals(description, quiz.getDescription());

        quiz.setDescription(null);
        assertNull(quiz.getDescription());
    }

    @Test
    public void testCategory() {
        Category category = new Category();
        quiz.setCategory(category);
        assertEquals(category, quiz.getCategory());
    }
    @Test
    public void testQuestions() {
        Set<Question> questions = new HashSet<>();
        questions.add(new Question());
        quiz.setQuestions(questions);
        assertNotNull(quiz.getQuestions());
        assertEquals(questions.size(), quiz.getQuestions().size());
    }
    @Test
    public void testImage() {
        Image image = new Image();
        quiz.setImage(image);
        assertEquals(image, quiz.getImage());
    }
}
