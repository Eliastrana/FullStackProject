package edu.ntnu.idatt2105.SpringbootBackend.repository;

import edu.ntnu.idatt2105.SpringbootBackend.model.UserQuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * The {@code UserQuizAttemptRepository} interface extends {@link JpaRepository} to facilitate
 * CRUD operations and queries related to {@link UserQuizAttempt} entities. This repository
 * supports the tracking and management of users' attempts at quizzes, allowing for the
 * retrieval of attempt records based on user or quiz identifiers.
 *
 * It provides methods to find all quiz attempts made by a specific user or to retrieve all
 * attempts for a specific quiz. These capabilities are essential for generating analytics
 * and feedback related to quiz engagement and performance, both from the perspective of
 * individual users and across the quiz system as a whole.
 *
 * Through the {@code UserQuizAttemptRepository}, the application can monitor user progress,
 * analyze quiz difficulty, and tailor the quiz experience to better suit user needs and
 * preferences, thereby enhancing the educational value and user satisfaction of the quiz platform.
 *
 * @author Vegard Johnsen
 * @version 0.1
 * @since 0.1
 * @see UserQuizAttempt for the entity managed by this repository.
 */
@Repository
public interface UserQuizAttemptRepository extends JpaRepository<UserQuizAttempt, UUID> {

    /**
     * Finds all quiz attempts made by a specific user.
     * This method enables the retrieval of a user's entire quiz attempt history, supporting
     * functionalities such as progress tracking, performance analysis, and personalized feedback.
     *
     * @param userId The unique identifier of the user whose quiz attempts are to be retrieved.
     * @return A list of {@link UserQuizAttempt} entities representing the user's quiz attempts.
     */
    List<UserQuizAttempt> findByUserId(UUID userId);

    /**
     * Finds all attempts for a specific quiz.
     * This method facilitates the analysis of a quiz's performance metrics, including average
     * scores, attempt frequency, and user engagement. It supports quiz authors and administrators
     * in evaluating the effectiveness of quiz content and making informed decisions on future
     * quiz development and refinement.
     *
     * @param quizId The unique identifier of the quiz for which attempts are to be retrieved.
     * @return A list of {@link UserQuizAttempt} entities representing all attempts made for the quiz.
     */
    List<UserQuizAttempt> findByQuizId(UUID quizId);
}
