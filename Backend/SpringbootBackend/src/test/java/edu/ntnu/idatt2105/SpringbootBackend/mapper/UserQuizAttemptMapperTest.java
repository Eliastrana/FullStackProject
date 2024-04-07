package edu.ntnu.idatt2105.SpringbootBackend.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ntnu.idatt2105.SpringbootBackend.dto.UserQuizAttemptDTO;
import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.model.UserQuizAttempt;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

class UserQuizAttemptMapperTest {

    @Autowired
    private UserQuizAttemptMapper mapper = new UserQuizAttemptMapper();

    @Test
    void toDtoTest() {
        // Setup
        User user = new User();
        user.setId(UUID.randomUUID());

        Quiz quiz = new Quiz();
        quiz.setId(UUID.randomUUID());

        UserQuizAttempt attempt = new UserQuizAttempt();
        attempt.setId(UUID.randomUUID());
        attempt.setUser(user);
        attempt.setQuiz(quiz);
        attempt.setScore(85);

        // Act
        UserQuizAttemptDTO dto = mapper.toDto(attempt);

        // Assert
        assertNotNull(dto);
        assertEquals(attempt.getId(), dto.getId());
        assertEquals(attempt.getUser().getId(), dto.getUserId());
        assertEquals(attempt.getQuiz().getId(), dto.getQuizId());
        assertEquals(attempt.getScore(), dto.getScore());
    }

    @Test
    void toEntityTest() {
        // Setup
        UserQuizAttemptDTO dto = new UserQuizAttemptDTO();
        dto.setId(UUID.randomUUID());
        dto.setScore(90);

        // Act
        UserQuizAttempt attempt = mapper.toEntity(dto);

        // Assert
        assertNotNull(attempt);
        assertEquals(dto.getId(), attempt.getId());
        assertEquals(dto.getScore(), attempt.getScore());
        // Note: User and Quiz must be handled/set outside the mapper or through additional method parameters
    }

    @Test
    void handleNullInputTest() {
        assertNull(mapper.toDto(null));
        assertNull(mapper.toEntity(null));
    }
}

