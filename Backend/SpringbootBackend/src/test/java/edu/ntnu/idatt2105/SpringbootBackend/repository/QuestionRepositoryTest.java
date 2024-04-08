package edu.ntnu.idatt2105.SpringbootBackend.repository;
import edu.ntnu.idatt2105.SpringbootBackend.model.Category;
import edu.ntnu.idatt2105.SpringbootBackend.model.Difficulty;
import edu.ntnu.idatt2105.SpringbootBackend.model.Question;
import edu.ntnu.idatt2105.SpringbootBackend.model.QuestionType;
import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testFindAllByQuizId() {
        Category category = new Category(UUID.randomUUID(),"Test Category", "Test Category Description");
        category = categoryRepository.save(category);
        Quiz quiz = new Quiz();
        quiz.setTitle("Test Quiz");
        quiz.setDescription("This is a test quiz.");
        quiz.setCategory(new Category());
        quiz.setDifficulty(Difficulty.EASY);
        quiz.setCategory(category);
        quiz = quizRepository.save(quiz);
        Question question = new Question();
        question.setQuiz(quiz);
        question.setCreationDate(LocalDateTime.now());
        question.setImage(null);
        question.setQuestionType(QuestionType.MULTIPLE_CHOICE);
        question.setText("Is this a test question?");

        question.setTags(null);
        question = questionRepository.save(question);

        List<Question> foundQuestions = questionRepository.findAllByQuizId(question.getQuiz().getId());

        assertEquals(1, foundQuestions.size());
        assertEquals(question.getQuiz().getId(), foundQuestions.get(0).getQuiz().getId());
    }

    @Test
public void testFindAllByQuizId_NoQuestions() {
    Category category = new Category(UUID.randomUUID(),"Test Category", "Test Category Description");
    category = categoryRepository.save(category);
    Quiz quiz = new Quiz();
    quiz.setTitle("Test Quiz");
    quiz.setDescription("This is a test quiz.");
    quiz.setCategory(category);
    quiz.setDifficulty(Difficulty.EASY);
    quiz = quizRepository.save(quiz);

    List<Question> foundQuestions = questionRepository.findAllByQuizId(quiz.getId());

    assertEquals(0, foundQuestions.size());
}


}