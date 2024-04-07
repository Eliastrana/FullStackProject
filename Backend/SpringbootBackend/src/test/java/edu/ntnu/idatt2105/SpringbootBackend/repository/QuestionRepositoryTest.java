package edu.ntnu.idatt2105.SpringbootBackend.repository;
import edu.ntnu.idatt2105.SpringbootBackend.model.Question;
import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    public void testFindAllByQuizId() {
        Quiz quiz = new Quiz();
        UUID id = UUID.randomUUID();
        quiz.setId(id);
        Question question = new Question();
        question.setQuiz(new Quiz());
        question = questionRepository.save(question);

        List<Question> foundQuestions = questionRepository.findAllByQuizId(question.getQuiz().getId());

        assertEquals(1, foundQuestions.size());
        assertEquals(question.getQuiz().getId(), foundQuestions.get(0).getQuiz().getId());
    }
}