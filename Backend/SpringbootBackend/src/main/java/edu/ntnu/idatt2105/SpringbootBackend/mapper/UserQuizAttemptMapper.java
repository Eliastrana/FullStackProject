package edu.ntnu.idatt2105.SpringbootBackend.mapper;

import edu.ntnu.idatt2105.SpringbootBackend.dto.UserQuizAttemptDTO;
import edu.ntnu.idatt2105.SpringbootBackend.model.UserQuizAttempt;
import org.springframework.stereotype.Component;

/**
 * The {@code UserQuizAttemptMapper} class is responsible for converting between
 * {@link UserQuizAttempt} entities and {@link UserQuizAttemptDTO} Data Transfer Objects (DTOs).
 * It provides methods to map from entity to DTO and vice versa, facilitating the transfer of data
 * between different layers of the application without exposing the entity model directly.
 *
 * @author vegard johnsen
 * @version 0.1
 * @since 0.1
 * @see UserQuizAttempt
 * @see UserQuizAttemptDTO
 */
@Component
public class UserQuizAttemptMapper {

    /**
     * Converts a {@link UserQuizAttempt} entity to a {@link UserQuizAttemptDTO}.
     * This method maps the essential information of a user's quiz attempt including
     * identifiers for the attempt, user, quiz, and the score achieved by the user.
     *
     * @param userQuizAttempt the {@link UserQuizAttempt} entity to be converted.
     * @return a {@link UserQuizAttemptDTO} containing the data from the user quiz attempt entity.
     */
    public UserQuizAttemptDTO toDto(UserQuizAttempt userQuizAttempt) {
        if (userQuizAttempt == null) {
            return null;
        }

        UserQuizAttemptDTO dto = new UserQuizAttemptDTO();
        dto.setId(userQuizAttempt.getId());
        dto.setUserId(userQuizAttempt.getUser().getId());
        dto.setQuizId(userQuizAttempt.getQuiz().getId());
        dto.setScore(userQuizAttempt.getScore()); 
        dto.setQuizTitle(userQuizAttempt.getQuiz().getTitle());
        return dto;
    }

    /**
     * Converts a {@link UserQuizAttemptDTO} to a {@link UserQuizAttempt} entity.
     * This method is used when creating or updating a user quiz attempt from DTO data.
     * It maps the score and identifiers back to the entity form. Note that relationships
     * with the User and Quiz entities are typically handled separately, outside this method.
     *
     * @param dto the {@link UserQuizAttemptDTO} containing the user quiz attempt data.
     * @return a {@link UserQuizAttempt} entity based on the DTO's data.
     */
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
