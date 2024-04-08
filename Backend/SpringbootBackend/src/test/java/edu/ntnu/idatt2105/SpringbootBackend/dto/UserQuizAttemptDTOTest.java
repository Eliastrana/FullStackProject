package edu.ntnu.idatt2105.SpringbootBackend.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserQuizAttemptDTOTest {

    @Test
    void testNoArgsConstructor() {
        UserQuizAttemptDTO attempt = new UserQuizAttemptDTO();
        assertNotNull(attempt);
    }

    @Test
    void testAllArgsConstructor() {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID quizId = UUID.randomUUID();
        int score = 85;
        String status = "completed";

        UserQuizAttemptDTO attempt = new UserQuizAttemptDTO(id, userId, quizId, status, score, status);

        assertEquals(id, attempt.getId());
        assertEquals(userId, attempt.getUserId());
        assertEquals(quizId, attempt.getQuizId());
        assertEquals(score, attempt.getScore());
        assertEquals(status, attempt.getStatus());
    }

    @Test
    void testFieldAssignments() {
        UserQuizAttemptDTO attempt = new UserQuizAttemptDTO();
        
        UUID id = UUID.randomUUID();
        attempt.setId(id);

        UUID userId = UUID.randomUUID();
        attempt.setUserId(userId);

        UUID quizId = UUID.randomUUID();
        attempt.setQuizId(quizId);

        int score = 95;
        attempt.setScore(score);

        String status = "in progress";
        attempt.setStatus(status);
        
        assertEquals(id, attempt.getId());
        assertEquals(userId, attempt.getUserId());
        assertEquals(quizId, attempt.getQuizId());
        assertEquals(score, attempt.getScore());
        assertEquals(status, attempt.getStatus());
    }
}
