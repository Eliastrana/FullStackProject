package edu.ntnu.idatt2105.SpringbootBackend.dto;

import edu.ntnu.idatt2105.SpringbootBackend.model.QuestionType;
import edu.ntnu.idatt2105.SpringbootBackend.model.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for question details. This includes all relevant information about a question such as its content, associated quiz, type, tags, and any linked multimedia content.")
public class QuestionDTO {
    
    @Schema(description = "The unique identifier of the question.", example = "a3b8d425-2b60-4ad7-becc-bedf2ef860bd")
    private UUID id;

    @Schema(description = "The unique identifier of the quiz to which this question belongs.", example = "e8f5e7f3-7ed3-4faa-9889-7161d2b55633")
    private UUID quizId;

    @Schema(description = "The text content of the question.", example = "Who wrote 'To Kill a Mockingbird'?")
    private String text;

    @Schema(description = "The type of the question, indicating how it should be answered.", example = "MULTIPLE_CHOICE")
    private QuestionType questionType;

    @Schema(description = "An optional link to multimedia content related to the question, such as an image or video.", example = "https://example.com/video.mp4")
    private String multimediaLink;

    @Schema(description = "The date and time when the question was created.", example = "2023-04-05T12:00:00")
    private LocalDateTime creationDate;

    @Schema(description = "A set of UUIDs representing the tags associated with the question. Tags are used to categorize questions into topics.", example = "[\"e97aaa14-9a84-4e28-957c-76e8fcb4c321\", \"5fbdd9e3-eded-4980-9a4b-1d8df815b851\"]")
    private Set<UUID> tags;

    @Schema(description = "The UUID of an image associated with the question, if any. This can be used to add visual context to the question.", example = "f5d8a8ff-9b4d-4c67-8e48-5e9d5e7485df")
    private UUID imageId; 
}
