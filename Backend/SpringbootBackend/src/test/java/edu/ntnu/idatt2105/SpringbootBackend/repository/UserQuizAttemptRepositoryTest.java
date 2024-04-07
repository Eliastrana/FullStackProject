package edu.ntnu.idatt2105.SpringbootBackend.repository;

import edu.ntnu.idatt2105.SpringbootBackend.model.UserQuizAttempt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class UserQuizAttemptRepositoryTest {

    @Autowired
    private UserQuizAttemptRepository userQuizAttemptRepository;

    @Test
    public void testFindByUserId() {
        UserQuizAttempt userQuizAttempt = new UserQuizAttempt();
        userQuizAttempt.setUser(new User());
        userQuizAttempt = userQuizAttemptRepository.save(userQuizAttempt);

        List<UserQuizAttempt> foundUserQuizAttempts = userQuizAttemptRepository.findByUserId(userQuizAttempt.getUser().getId());

        assertEquals(1, foundUserQuizAttempts.size());
        assertEquals(userQuizAttempt.getUser().getId(), foundUserQuizAttempts.get(0).getUser().getId());
    }

    @Test
    public void testFindByQuizId() {
        UserQuizAttempt userQuizAttempt = new UserQuizAttempt();
        userQuizAttempt.setQuiz(new Quiz());
        userQuizAttempt = userQuizAttemptRepository.save(userQuizAttempt);

        List<UserQuizAttempt> foundUserQuizAttempts = userQuizAttemptRepository.findByQuizId(userQuizAttempt.getQuiz().getId());

        assertEquals(1, foundUserQuizAttempts.size());
        assertEquals(userQuizAttempt.getQuiz().getId(), foundUserQuizAttempts.get(0).getQuiz().getId());
    }

}
