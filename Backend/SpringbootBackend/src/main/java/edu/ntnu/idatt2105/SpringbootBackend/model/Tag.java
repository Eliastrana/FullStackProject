package edu.ntnu.idatt2105.SpringbootBackend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

/**
 * Represents a tag that can be assigned to questions to categorize them into specific topics
 * or themes. Tags facilitate easier searching and organization of questions within the quiz
 * application. Each tag can be associated with multiple questions, enabling a many-to-many
 * relationship.
 *
 * The {@code Tag} class is annotated with {@code @JsonIdentityInfo} to handle potential
 * recursion issues during JSON serialization and deserialization processes, especially
 * when dealing with bidirectional relationships.
 *
 * This model defines the fundamental properties of a tag, including its unique identifier,
 * name, and the list of questions associated with it. The relationship with {@link Question}
 * is defined to allow the association of a tag with multiple questions, reflecting the
 * many-to-many relationship between tags and questions.
 *
 * @author Sander R. Skofsrud
 * @version 0.1
 * @since 0.1
 * @see Question for the association with this tag
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tag")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Tag {

  /**
   * The unique identifier for the tag, automatically generated.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  /**
   * The name of the tag, providing a descriptive label that categorizes questions.
   * The name is intended to reflect the content or theme that the tag represents.
   */
  @Column(nullable = false, length = 255)
  private String name;

  /**
   * A set of questions that are associated with this tag. The {@code @ManyToMany}
   * annotation specifies the bidirectional many-to-many relationship between tags
   * and questions, facilitated through a join table.
   */
  @ManyToMany(mappedBy = "tags")
  private Set<Question> questions;
}
