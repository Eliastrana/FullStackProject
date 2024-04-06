package edu.ntnu.idatt2105.SpringbootBackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * Represents a Data Transfer Object (DTO) for a Category, which is used to categorize or classify questions into specific areas or topics.
 * Categories facilitate the organization and retrieval of questions by theme, making it easier for users to navigate and interact with quiz content.
 * This DTO structure is utilized for operations involving the creation, retrieval, and updating of category information.
 */
@Data
@Builder
@AllArgsConstructor
@Schema(description = "Data Transfer Object for a Category. Represents the classification or grouping of questions into a specific area or topic.")
public class CategoryDTO {

  /**
   * The unique identifier for the category, typically represented as a UUID. This ID ensures
   * the uniqueness of each category across the system, serving as a key for category-related operations.
   */
  @Schema(description = "Unique identifier for the category. This is typically a UUID.", 
          example = "123e4567-e89b-12d3-a456-426614174000")
  private UUID id;

  /**
   * The name of the category, which succinctly identifies the theme or topic covered by questions within this category.
   * The category name is used in user interfaces and API responses to represent the category in a human-readable form.
   */
  @Schema(description = "The name of the category, which succinctly represents the theme or topic covered by questions within this category.", 
          example = "Science")
  private String categoryName;

  /**
   * An optional brief description providing additional context about what kinds of questions or topics the category encompasses.
   * This field can offer insights into the scope and focus of the category, helping users understand the nature of its content.
   */
  @Schema(description = "A brief description providing more context about what kinds of questions or topics the category encompasses. This field is optional.", 
          example = "Questions related to scientific facts, including physics, chemistry, and biology.", 
          nullable = true)
  private String description;
}
