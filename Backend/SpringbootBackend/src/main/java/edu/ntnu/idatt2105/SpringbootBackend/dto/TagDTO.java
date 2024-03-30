package edu.ntnu.idatt2105.SpringbootBackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class TagDTO {
  @Schema(description = "Unique identifier of the tag", required = true)
  private UUID id;

  @Schema(description = "Name of the tag", required = true)
  private String name;

  @Schema(description = "List of question IDs associated with the tag")
  private Set<UUID> questionIds;
}