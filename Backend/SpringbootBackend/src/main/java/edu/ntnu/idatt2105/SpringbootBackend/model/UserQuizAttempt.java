package edu.ntnu.idatt2105.SpringbootBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
import java.time.LocalDateTime;

/**
 * Represents an attempt by a user to complete a quiz, capturing the user's score and
 * the timestamp of the attempt. This entity forms a crucial part of the quiz application
 * by linking users to quizzes and tracking their performance.
 * The {@code UserQuizAttempt} class is associated with both {@link User} and {@link Quiz},
 * establishing a many-to-one relationship with each. This means each attempt is linked to
 * one specific user and one specific quiz, while a user or a quiz can have multiple attempts
 * associated with them.
 * This model captures essential information about a quiz attempt, including the unique
 * identifier of the attempt, the associated user, the quiz being attempted, the score achieved
 * by the user, and the time of the attempt. It is a critical component for tracking user
 * progress and performance across different quizzes.
 *
 * @author Vegard Johnsen
 * @version 0.1
 * @since 0.1
 * @see User for the user making the quiz attempt
 * @see Quiz for the quiz being attempted
 */
@Entity
@Table(name = "user_quiz_attempt")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserQuizAttempt {

    /**
     * The unique identifier for the quiz attempt. This is automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The user who made the quiz attempt. This is established through a many-to-one
     * relationship, indicating that each attempt is made by one specific user.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * The quiz that was attempted. This is established through a many-to-one relationship,
     * indicating that each attempt is associated with one specific quiz.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    /**
     * The score achieved by the user in this quiz attempt. It represents the user's performance.
     */
    @Column(nullable = false)
    private int score;

    /**
     * The time at which the quiz attempt was made. This timestamp helps track when the attempt
     * occurred and can be used for analytics or historical records.
     */
    @Column(nullable = false)
    private LocalDateTime time;
}
