package edu.ntnu.idatt2105.SpringbootBackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "Data Transfer Object for a Category. Represents the classification or grouping of questions into a specific area or topic.")
public class CategoryDTO {

  @Schema(description = "Unique identifier for the category. This is typically a UUID.", 
          example = "123e4567-e89b-12d3-a456-426614174000")
  private UUID id;

  @Schema(description = "The name of the category, which succinctly represents the theme or topic covered by questions within this category.", 
          example = "Science")
  private String categoryName;

  @Schema(description = "A brief description providing more context about what kinds of questions or topics the category encompasses. This field is optional.", 
          example = "Questions related to scientific facts, including physics, chemistry, and biology.", 
          nullable = true)
  private String description;
}
