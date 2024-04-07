package edu.ntnu.idatt2105.SpringbootBackend.repository;

import edu.ntnu.idatt2105.SpringbootBackend.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RatingRepository extends JpaRepository<Rating, UUID> {

  Optional<Rating> findByUserIdAndQuizId(UUID userId, UUID quizId);

  @Query("SELECT r FROM Rating r WHERE r.user.id = :userId")
  List<Rating> findAllByUserId(UUID userId);

  @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.quiz.id = :quizId")
  Optional<Double> findAverageRatingByQuizId(UUID quizId);
}
