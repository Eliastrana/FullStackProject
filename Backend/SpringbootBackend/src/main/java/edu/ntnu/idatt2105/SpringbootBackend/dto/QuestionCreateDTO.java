package edu.ntnu.idatt2105.SpringbootBackend.dto;

import edu.ntnu.idatt2105.SpringbootBackend.model.QuestionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for creating a new question. This includes all necessary information to define a question such as the text, type, multimedia links, tags, and associated answers.")
public class QuestionCreateDTO {
    
    @Schema(description = "The text content of the question.", example = "What is the capital of France?")
    private String text;

    @Schema(description = "The type of the question, defining how it should be presented or answered.", example = "MULTIPLE_CHOICE")
    private QuestionType questionType;

    @Schema(description = "An optional link to multimedia content related to the question, such as images or videos.", example = "https://example.com/image.png")
    private String multimediaLink;

    @Schema(description = "A set of UUIDs representing the tags associated with the question. These tags help categorize the question by topic.", example = "[\"d290f1ee-6c54-4b01-90e6-d701748f0851\", \"3f6c6af7-9b81-4c60-b4e0-5fbb7c478e88\"]")
    private Set<UUID> tags;

    @Schema(description = "A set of answers for the question. This is particularly relevant for types like MULTIPLE_CHOICE where multiple answer options are provided.", example = "[{\"text\":\"Paris\",\"isCorrect\":true}, {\"text\":\"London\",\"isCorrect\":false}]")
    private Set<AnswerCreateDTO> answers;

    @Schema(description = "The UUID of an image associated with the question. This is optional and can be used to add a visual component to the question.", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    private UUID imageId;
}
