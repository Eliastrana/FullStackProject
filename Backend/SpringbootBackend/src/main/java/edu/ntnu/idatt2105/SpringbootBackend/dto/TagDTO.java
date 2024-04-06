package edu.ntnu.idatt2105.SpringbootBackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

/**
 * TagDTO is a Data Transfer Object that encapsulates the essential information about a tag used within the application.
 * Tags play a crucial role in categorizing and organizing questions by topics or themes, making it easier for users
 * to discover and engage with content relevant to their interests. Each tag is uniquely identified and can be associated
 * with multiple questions, thereby facilitating thematic grouping and enhanced navigability of quiz content.
 */
@Data
@Schema(description = "Data Transfer Object representing a tag. Tags are used to categorize questions and can be associated with multiple questions.")
public class TagDTO {

    @Schema(description = "The unique identifier of the tag.", example = "d7b8df5e-4567-4f1e-8c2f-3de0b6b6f1a4")
    private UUID id;

    @Schema(description = "The name of the tag, which typically represents a topic or category related to the questions it's associated with.", example = "History")
    private String name;

    @Schema(description = "A set of unique identifiers for questions associated with this tag. This allows questions to be grouped by shared topics or themes.", example = "[\"9fa1aef0-4b3b-4eda-9c1a-2a1d9eda1e2d\", \"5f8d3a7d-6e3e-4c9b-9775-35e9a2ecde2b\"]")
    private Set<UUID> questionIds;
}
