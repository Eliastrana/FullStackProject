package edu.ntnu.idatt2105.SpringbootBackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Schema(description = "Data Transfer Object for Category")
@Data
@Builder
public class CategoryDTO {

  @Schema(description = "Unique identifier of the category", example = "123e4567-e89b-12d3-a456-426614174000")
  private UUID id;

  @Schema(description = "Name of the category", example = "Science")
  private String categoryName;

  @Schema(description = "Description of the category", example = "Questions related to scientific facts", nullable = true)
  private String description;

  public CategoryDTO(UUID id, String categoryName, String description) {
    this.id = id;
    this.categoryName = categoryName;
    this.description = description;
  }
}
