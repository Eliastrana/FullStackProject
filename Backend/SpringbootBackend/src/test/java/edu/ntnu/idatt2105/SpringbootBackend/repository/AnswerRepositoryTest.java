package edu.ntnu.idatt2105.SpringbootBackend.repository;

import edu.ntnu.idatt2105.SpringbootBackend.model.Answer;
import edu.ntnu.idatt2105.SpringbootBackend.model.Difficulty;
import edu.ntnu.idatt2105.SpringbootBackend.model.Question;
import edu.ntnu.idatt2105.SpringbootBackend.model.QuestionType;
import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;
    
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Test
    public void testSaveAndRetrieveAnswer() {
        // Setup
        Quiz quiz = new Quiz();
        quiz.setCategory(null);
        quiz.setTitle("Test Quiz");
        quiz.setDescription("This is a test quiz.");
        quiz.setDifficulty(Difficulty.EASY);

        quiz = quizRepository.save(quiz);

        Question question = new Question();
        question.setCreationDate(LocalDateTime.now());
        question.setImage(null);
        question.setQuestionType(QuestionType.MULTIPLE_CHOICE);
        question.setText("Is this a test question?");

        question.setQuiz(quiz);
        question = questionRepository.save(question);

        Answer answer = new Answer();
        answer.setText("Yes");
        answer.setCorrect(true);
        answer.setQuestion(question);

        HashSet<Answer> answerSet = new HashSet<>();
        answerSet.add(answer);

        question.setAnswers(answerSet);


        answer = answerRepository.save(answer);
        Optional<Answer> retrievedAnswers = answerRepository.findByQuestionAndText(question, answer.getText());
        assertTrue(retrievedAnswers.isPresent());
        assertEquals(answer.getText(), retrievedAnswers.get().getText());
        assertEquals(answer.isCorrect(), retrievedAnswers.get().isCorrect());
        assertEquals(answer.getQuestion().getId(), retrievedAnswers.get().getQuestion().getId());
    }

}
