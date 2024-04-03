package edu.ntnu.idatt2105.SpringbootBackend.dto;

import java.util.Set;
import java.util.UUID;

import edu.ntnu.idatt2105.SpringbootBackend.model.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class CompleteQuizDTO {
    private String title;
    private String description;
    private UUID creatorId;
    private String categoryName;
    private Difficulty difficulty;
    private Set<CompleteQuestionDTO> questions;

    // Image fields
    private String imageName;
    private String imageType;
    private String imageData;
}