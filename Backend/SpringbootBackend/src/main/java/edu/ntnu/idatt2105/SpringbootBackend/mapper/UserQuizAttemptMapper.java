package edu.ntnu.idatt2105.SpringbootBackend.mapper;

import edu.ntnu.idatt2105.SpringbootBackend.dto.UserQuizAttemptDTO;
import edu.ntnu.idatt2105.SpringbootBackend.model.UserQuizAttempt;
import org.springframework.stereotype.Component;

@Component
public class UserQuizAttemptMapper {

    public UserQuizAttemptDTO toDto(UserQuizAttempt userQuizAttempt) {
        if (userQuizAttempt == null) {
            return null;
        }

        UserQuizAttemptDTO dto = new UserQuizAttemptDTO();
        dto.setId(userQuizAttempt.getId());
        dto.setUserId(userQuizAttempt.getUser().getId());
        dto.setQuizId(userQuizAttempt.getQuiz().getId());
        dto.setScore(userQuizAttempt.getScore()); 
        return dto;
    }

    public UserQuizAttempt toEntity(UserQuizAttemptDTO dto) {
        if (dto == null) {
            return null;
        }

        UserQuizAttempt userQuizAttempt = new UserQuizAttempt();
        userQuizAttempt.setId(dto.getId());
        userQuizAttempt.setScore(dto.getScore());
        return userQuizAttempt;
    }
}
