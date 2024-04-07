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

@Service
@AllArgsConstructor
public class RatingService {

  private final RatingRepository ratingRepository;
  private final UserRepository userRepository;
  private final QuizRepository quizRepository;

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

  public double getAverageRatingForQuiz(UUID quizId) {
    return ratingRepository.findAverageRatingByQuizId(quizId)
            .orElse(0.0);
  }

  public List<RatingDTO> getRatingsByUserId(UUID userId) {
    List<Rating> ratings = ratingRepository.findAllByUserId(userId);
    return ratings.stream()
            .map(r -> new RatingDTO(r.getUser().getId(), r.getQuiz().getId(), r.getRating()))
            .collect(Collectors.toList());
  }

  public RatingDTO getRatingByUserAndQuizId(UUID userId, UUID quizId) {
    return ratingRepository.findByUserIdAndQuizId(userId, quizId)
            .map(rating -> new RatingDTO(rating.getUser().getId(), rating.getQuiz().getId(), rating.getRating()))
            .orElseThrow(() -> new RuntimeException("Rating not found"));
  }

  @Transactional(readOnly = true)
  public RatingDTO findRatingByUserIdAndQuizId(UUID userId, UUID quizId) {
    return ratingRepository.findByUserIdAndQuizId(userId, quizId)
            .map(rating -> new RatingDTO(rating.getUser().getId(), rating.getQuiz().getId(), rating.getRating()))
            .orElse(null);
  }

}
