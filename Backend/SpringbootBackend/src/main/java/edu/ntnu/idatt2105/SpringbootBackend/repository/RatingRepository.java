package edu.ntnu.idatt2105.SpringbootBackend.repository;

import edu.ntnu.idatt2105.SpringbootBackend.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The {@code RatingRepository} interface extends the Spring Data {@link JpaRepository} for managing
 * {@link Rating} entities within the quiz application. This interface provides the necessary abstraction
 * layer to conduct CRUD operations on quiz ratings, alongside custom query capabilities to support specific
 * application requirements concerning ratings.
 * <p>
 * With this repository, the application can effortlessly perform operations such as saving, updating,
 * and retrieving quiz ratings. It enables efficient access to the database for manipulating rating
 * data, thus facilitating a smooth data persistence workflow.
 * <p>
 * Essential for the functionality of rating management, the `RatingRepository` supports key features such
 * as finding all ratings by a user, computing average ratings for quizzes, and finding specific ratings
 * by user and quiz identifiers. These capabilities ensure that ratings are effectively managed and utilized
 * within the application to enhance user interaction and content quality assessment.
 * 
 * @author Vegard Johnsen
 * @version 0.1
 * @since 0.1
 * @see Rating for the entity managed by this repository.
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, UUID> {

    Optional<Rating> findByUserIdAndQuizId(UUID userId, UUID quizId);

    @Query("SELECT r FROM Rating r WHERE r.user.id = :userId")
    List<Rating> findAllByUserId(UUID userId);

    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.quiz.id = :quizId")
    Optional<Double> findAverageRatingByQuizId(UUID quizId);
}
