package edu.ntnu.idatt2105.SpringbootBackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Represents a single question within a quiz, encapsulating all necessary details such as the
 * question text, type, associated multimedia links, and answers. Each question is part of a quiz
 * and can have multiple answers, among which one or more may be correct.
 * 
 * The {@code Question} class includes relationships to other entities such as {@link Quiz},
 * {@link Answer}, {@link Image}, and {@link Tag}, establishing a comprehensive model for
 * managing quiz content within the system.
 * 
 * @Author  Sander R. Skofsrud
 * @version 0.1
 * @since 0.1
 * @see Quiz
 * @see Answer
 * @see User
 * @see UUID
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rating")
public class Rating {
  /**
   * Unique identifier for the rating. This is automatically generated.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  /**
   * The user who rated the quiz. Defined by a many-to-one relationship.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;
  /**
   * The quiz that was rated. Defined by a many-to-one relationship.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "quiz_id")
  private Quiz quiz;
  /**
   * The rating given by the user.
   */
  @Column(nullable = false)
  private int rating;
}
