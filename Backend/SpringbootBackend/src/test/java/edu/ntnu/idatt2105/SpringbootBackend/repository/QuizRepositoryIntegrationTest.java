package edu.ntnu.idatt2105.SpringbootBackend.repository;

import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;
import edu.ntnu.idatt2105.SpringbootBackend.repository.QuizRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
public class QuizRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private QuizRepository quizRepository;

    @Test
    public void whenFindById_thenReturnQuiz() {
        // given
        Quiz quiz = new Quiz();
        quiz.setTitle("Sample Quiz");
        quiz.setDescription("This is a sample quiz for testing.");
        quiz.setPublic(true); // Assuming isPublic method is correctly named according to the JavaBean convention
        quiz = entityManager.persistFlushFind(quiz);

        // when
        Optional<Quiz> foundQuiz = quizRepository.findById(quiz.getId());

        // then
        assertTrue(foundQuiz.isPresent());
        assertEquals(quiz.getTitle(), foundQuiz.get().getTitle());
    }
}

