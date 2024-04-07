package edu.ntnu.idatt2105.SpringbootBackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Represents an answer within the quiz system. Each answer is linked to a specific
 * {@link Question} and indicates whether the answer is correct. This class is annotated
 * for persistence in a relational database and JSON serialization/deserialization handling.
 *
 * <p>Answers are directly associated with questions, forming a many-to-one relationship
 * where each question can have multiple answers but each answer is associated with only one
 * question. The relationship is managed through the {@code question_id} foreign key.</p>
 *
 * <p>The {@link JsonBackReference} annotation is used to prevent recursion issues during
 * JSON serialization by omitting the serialization of the question back reference.</p>
 *
 * @author vegard johnsen
 * @version 0.1
 * @since
 * @see Question for the entity to which this answer belongs.
 */
@Entity
@Table(name = "answer")
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Answer {
    /**
     * Unique identifier for the answer. Generated automatically.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The text content of the answer.
     */
    @Column(nullable = false, length = 1024)
    private String text;

    /**
     * Indicates whether this answer is the correct one for its question.
     */

    @Column(nullable = false)
    private boolean isCorrect;

    /**
     * The question to which this answer belongs. Managed through a many-to-one relationship.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    @JsonBackReference
    private Question question;

}
