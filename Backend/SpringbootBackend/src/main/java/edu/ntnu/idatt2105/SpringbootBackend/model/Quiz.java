package edu.ntnu.idatt2105.SpringbootBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;
import java.util.UUID;


/**
 * Represents a quiz entity within the system, encapsulating details such as quiz title,
 * description, public visibility, difficulty level, and associated categories, creators,
 * questions, and images. It supports organizing quizzes into categories, associating them
 * with creators, and managing quiz content through questions and images.
 * The {@code Quiz} class establishes relationships with other entities in the model, such
 * as {@link Category} for classification, {@link User} for creator information, {@link Question}
 * for quiz content, and {@link Image} for visual representation. This comprehensive model allows
 * for a detailed representation and management of quizzes within the system.
 *
 * @author Vegard Johnsen, Sander R. Skofsrud
 * @version 0.1
 * @since 0.1
 * @see Category
 * @see User
 * @see Question
 * @see Image
 */
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Entity
@Table(name = "quiz")
public class Quiz {

    /**
     * Unique identifier for the quiz, automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Title of the quiz, providing a brief overview of its content or theme.
     */
    @Column(nullable = false)
    private String title;

    /**
     * Detailed description of the quiz, offering insights into what participants can expect.
     */
    @Column(length = 1000)
    private String description;

    /**
     * Visibility flag indicating whether the quiz is publicly accessible or private.
     */
    @Column
    private boolean isPublic;

    /**
     * Difficulty level of the quiz, aiding users in selecting quizzes that match their skill level.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    /**
     * Category to which the quiz belongs, facilitating organized classification.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * User who created the quiz, establishing ownership and authorship.
     */
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    /**
     * Questions included in the quiz, forming the quiz content.
     */
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Question> questions;

    /**
     * Optional image associated with the quiz for visual enhancement.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;
}
