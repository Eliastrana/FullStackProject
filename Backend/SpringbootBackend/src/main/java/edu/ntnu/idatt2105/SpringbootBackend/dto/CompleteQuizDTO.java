package edu.ntnu.idatt2105.SpringbootBackend.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class CompleteQuizDTO {
    private UUID id;
    private String title;
    private String description;
    private UUID creatorId;
    private UUID categoryId;
    private List<CompleteQuestionDTO> questions;
}