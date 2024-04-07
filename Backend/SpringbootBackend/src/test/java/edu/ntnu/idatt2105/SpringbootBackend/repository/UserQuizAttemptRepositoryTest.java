package edu.ntnu.idatt2105.SpringbootBackend.repository;

import edu.ntnu.idatt2105.SpringbootBackend.model.UserQuizAttempt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import edu.ntnu.idatt2105.SpringbootBackend.model.Difficulty;
import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.model.Category;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class UserQuizAttemptRepositoryTest {

    @Autowired
    private UserQuizAttemptRepository userQuizAttemptRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testFindByUserId() {
        Quiz quiz = new Quiz();
        Category category = new Category(UUID.randomUUID(), "Test Category", "Test Category Description");
        category = categoryRepository.save(category);
        quiz.setCategory(category);
        quiz.setTitle("Test Quiz");
        quiz.setDescription("This is a test quiz.");
        quiz.setDifficulty(Difficulty.EASY);

        quiz = quizRepository.save(quiz);

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("Test User");
        user.setPassword("Test Password");
        user.setEmail("test@mail.com");
        user = userRepository.save(user);

        UserQuizAttempt userQuizAttempt = new UserQuizAttempt();
        userQuizAttempt.setUser(user);
        userQuizAttempt.setQuiz(quiz);
        userQuizAttempt.setScore(0);
        userQuizAttempt.setTime(java.time.LocalDateTime.now());

        userQuizAttempt = userQuizAttemptRepository.save(userQuizAttempt);

        List<UserQuizAttempt> foundUserQuizAttempts = userQuizAttemptRepository.findByUserId(user.getId());

        assertEquals(1, foundUserQuizAttempts.size());
        assertEquals(userQuizAttempt.getUser().getId(), foundUserQuizAttempts.get(0).getUser().getId());
    }

    @Test
    public void testFindByQuizId() {
        Quiz quiz = new Quiz();
        Category category = new Category(UUID.randomUUID(), "Test Category", "Test Category Description");
        category = categoryRepository.save(category);
        quiz.setCategory(category);
        quiz.setTitle("Test Quiz");
        quiz.setDescription("This is a test quiz.");
        quiz.setDifficulty(Difficulty.EASY);

        quiz = quizRepository.save(quiz);

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("Test User");
        user.setPassword("Test Password");
        user.setEmail("test@mail.com");
        user = userRepository.save(user);

        UserQuizAttempt userQuizAttempt = new UserQuizAttempt();
        userQuizAttempt.setUser(user);
        userQuizAttempt.setQuiz(quiz);
        userQuizAttempt.setScore(0);
        userQuizAttempt.setTime(java.time.LocalDateTime.now());

        userQuizAttempt = userQuizAttemptRepository.save(userQuizAttempt);

        List<UserQuizAttempt> foundUserQuizAttempts = userQuizAttemptRepository.findByQuizId(quiz.getId());

        assertEquals(1, foundUserQuizAttempts.size());
        assertEquals(userQuizAttempt.getUser().getId(), foundUserQuizAttempts.get(0).getUser().getId());
    }

}
