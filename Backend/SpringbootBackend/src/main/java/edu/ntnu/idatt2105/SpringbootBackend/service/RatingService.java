package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.dto.RatingDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.QuizNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.UserNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;
import edu.ntnu.idatt2105.SpringbootBackend.model.Rating;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.repository.QuizRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.RatingRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
/**
 * Provides services related to {@link Rating} entities, including saving or updating ratings,
 * fetching average rating for quizzes, and listing ratings by user.
 * This service utilizes {@link RatingRepository}, {@link UserRepository}, and {@link QuizRepository}
 * to perform its operations, ensuring that data integrity and business rules are upheld.
 *
 * @author Sander Skofsrud
 * @version 1.0
 * @since 1.0
 */
@Service
@AllArgsConstructor
public class RatingService {

  private final RatingRepository ratingRepository;
  private final UserRepository userRepository;
  private final QuizRepository quizRepository;

    /**
   * Saves or updates a rating for a specific quiz by a user. If a rating by the user for the quiz already exists,
   * it is updated; otherwise, a new rating is created.
   *
   * @param userId The unique identifier of the user.
   * @param quizId The unique identifier of the quiz.
   * @param ratingValue The value of the rating.
   * @return A {@link RatingDTO} representing the saved or updated rating.
   * @throws UserNotFoundException if the user does not exist.
   * @throws QuizNotFoundException if the quiz does not exist.
   */

  @Transactional
  public RatingDTO saveOrUpdateRating(UUID userId, UUID quizId, int ratingValue) {
    User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
    Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new QuizNotFoundException("Quiz not found"));

    Rating rating = ratingRepository.findByUserIdAndQuizId(userId, quizId)
            .orElse(new Rating());

    rating.setUser(user);
    rating.setQuiz(quiz);
    rating.setRating(ratingValue);

    Rating savedRating = ratingRepository.save(rating);
    return new RatingDTO(savedRating.getUser().getId(), savedRating.getQuiz().getId(), savedRating.getRating());
  }
  /**
   * Retrieves the average rating for a given quiz.
   *
   * @param quizId The unique identifier of the quiz.
   * @return The average rating as a double.
   */

  public double getAverageRatingForQuiz(UUID quizId) {
    return ratingRepository.findAverageRatingByQuizId(quizId)
            .orElse(0.0);
  }
  /**
   * Lists all ratings given by a specific user across all quizzes.
   *
   * @param userId The unique identifier of the user.
   * @return A list of {@link RatingDTO} representing the ratings made by the user.
   */
  public List<RatingDTO> getRatingsByUserId(UUID userId) {
    List<Rating> ratings = ratingRepository.findAllByUserId(userId);
    return ratings.stream()
            .map(r -> new RatingDTO(r.getUser().getId(), r.getQuiz().getId(), r.getRating()))
            .collect(Collectors.toList());
  }

  /**
   * Retrieves a specific rating given by a user for a quiz.
   *
   * @param userId The unique identifier of the user.
   * @param quizId The unique identifier of the quiz.
   * @return A {@link RatingDTO} representing the rating, or throws an exception if not found.
   */

  @Transactional(readOnly = true)
  public RatingDTO getRatingByUserAndQuizId(UUID userId, UUID quizId) {
    return ratingRepository.findByUserIdAndQuizId(userId, quizId)
            .map(rating -> new RatingDTO(rating.getUser().getId(), rating.getQuiz().getId(), rating.getRating()))
            .orElseThrow(() -> new RuntimeException("Rating not found"));
  }

  /**
   * Finds a rating by user ID and quiz ID, returning null if not found.
   * This method is explicitly read-only and transactional.
   *
   * @param userId The unique identifier of the user.
   * @param quizId The unique identifier of the quiz.
   * @return A {@link RatingDTO} if the rating exists, or null otherwise.
   */
  @Transactional(readOnly = true)
  public RatingDTO findRatingByUserIdAndQuizId(UUID userId, UUID quizId) {
    return ratingRepository.findByUserIdAndQuizId(userId, quizId)
            .map(rating -> new RatingDTO(rating.getUser().getId(), rating.getQuiz().getId(), rating.getRating()))
            .orElse(null);
  }

}
