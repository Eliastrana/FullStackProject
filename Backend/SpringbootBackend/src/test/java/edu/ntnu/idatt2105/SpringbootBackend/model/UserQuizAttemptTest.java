package edu.ntnu.idatt2105.SpringbootBackend.model;

import edu.ntnu.idatt2105.SpringbootBackend.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserQuizAttemptTest {

    private UserQuizAttempt userQuizAttempt;

    @BeforeEach
    public void setup() {
        userQuizAttempt = new UserQuizAttempt();
    }

    @Test
    public void testId() {
        UUID id = UUID.randomUUID();
        userQuizAttempt.setId(id);
        assertEquals(id, userQuizAttempt.getId());
    }

    @Test
    public void testUser() {
        User user = new User();
        userQuizAttempt.setUser(user);
        assertEquals(user, userQuizAttempt.getUser());
    }

    @Test
    public void testQuiz() {
        Quiz quiz = new Quiz();
        userQuizAttempt.setQuiz(quiz);
        assertEquals(quiz, userQuizAttempt.getQuiz());
    }

    @Test
    public void testScore() {
        int score = 85;
        userQuizAttempt.setScore(score);
        assertEquals(score, userQuizAttempt.getScore());
    }

    @Test
    public void testTime() {
        LocalDateTime time = LocalDateTime.now();
        userQuizAttempt.setTime(time);
        assertEquals(time, userQuizAttempt.getTime());
    }
}
