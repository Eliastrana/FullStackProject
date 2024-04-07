package edu.ntnu.idatt2105.SpringbootBackend.repository;

import edu.ntnu.idatt2105.SpringbootBackend.model.Category;
import edu.ntnu.idatt2105.SpringbootBackend.model.Difficulty;
import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
public class QuizRepositoryTest {

    @Autowired
    private QuizRepository quizRepository;

    @Test
    public void testSaveAndFindById() {
        Quiz quiz = new Quiz();
        quiz.setId(UUID.randomUUID());
        quiz.setCategory(new Category());
        quiz.setTitle("Test Quiz");
        quiz.setDescription("This is a test quiz.");
        quiz.setDifficulty(Difficulty.EASY);
        quiz = quizRepository.save(quiz);

        Optional<Quiz> foundQuiz = quizRepository.findById(quiz.getId());

        assertTrue(foundQuiz.isPresent());
        assertEquals(quiz.getId(), foundQuiz.get().getId());
    }
}