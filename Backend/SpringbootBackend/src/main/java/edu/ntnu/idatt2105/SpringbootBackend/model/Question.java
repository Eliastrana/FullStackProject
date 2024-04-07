package edu.ntnu.idatt2105.SpringbootBackend.model;

import edu.ntnu.idatt2105.SpringbootBackend.model.Answer;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Set;

/**
 * Represents a single question within a quiz, encapsulating all necessary details such as the
 * question text, type, associated multimedia links, and answers. Each question is part of a quiz
 * and can have multiple answers, among which one or more may be correct.
 *
 * The {@code Question} class includes relationships to other entities such as {@link Quiz},
 * {@link Answer}, {@link Image}, and {@link Tag}, establishing a comprehensive model for
 * managing quiz content within the system.
 *
 * @author Vegard Johnsen, Sander R. Skofsrud
 * @version 0.1
 * @since 0.1
 * @see Quiz
 * @see Answer
 * @see Image
 * @see Tag
 */
@Entity
@Table(name = "question")
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Question {

    /**
     * Unique identifier for the question. This is automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The quiz to which this question belongs. Defined by a many-to-one relationship.
     */
    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    /**
     * The text content of the question.
     */
    @Column(nullable = false, length = 1024)
    private String text;

    /**
     * The type of the question, defining how it should be presented and answered.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType questionType;

    /**
     * An optional multimedia link related to the question, such as an image or video URL.
     */
    @Column
    private String multimediaLink;

    /**
     * An optional image associated with the question for visual representation.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    /**
     * The date and time when the question was created.
     */
    @Column(nullable = false)
    private LocalDateTime creationDate;

    /**
     * Tags associated with the question, facilitating categorization and searchability.
     */
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "question_tag",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    /**
     * A collection of answers associated with this question. Each question can have multiple answers.
     */
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Answer> answers;

}
