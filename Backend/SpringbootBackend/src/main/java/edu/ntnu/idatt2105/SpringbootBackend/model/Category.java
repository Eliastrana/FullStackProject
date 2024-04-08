package edu.ntnu.idatt2105.SpringbootBackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Represents a category within the quiz system. Categories are used to group quizzes into coherent themes
 * or subjects, making it easier for users to find quizzes that match their interests or study needs.
 * Each category is identified by a unique name and can optionally include a description to provide further
 * context about the kinds of quizzes it contains.
 *
 * This entity is part of the application's domain model and is persisted in the database using JPA annotations
 * to define its mapping to the {@code category} table. Each category can be associated with multiple quizzes,
 * establishing a one-to-many relationship managed by the {@link Quiz} entity.
 *
 * @author Sander R. Skofsrud
 * @version 0.1
 * @since 0.1
 * @see Quiz for the quizzes associated with this category.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "category")
public class Category {

  /**
   * Unique identifier for the category, automatically generated upon persistence.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  /**
   * The name of the category, serving as a unique and descriptive label for the type of quizzes it encompasses.
   * This field is marked as non-nullable and unique, ensuring that each category name is distinct.
   */
  @Column(nullable = false, unique = true)
  private String categoryName;

  /**
   * An optional description of the category, providing additional context about the quizzes it contains.
   * This can include information about the subject, difficulty level, or intended audience of the quizzes.
   */
  @Column(length = 1000)
  private String description; // Assuming you allow for a description that can be null
}
