package edu.ntnu.idatt2105.SpringbootBackend.mapper;

import edu.ntnu.idatt2105.SpringbootBackend.dto.CompleteQuestionDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuestionCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuestionDTO;
import edu.ntnu.idatt2105.SpringbootBackend.model.Image;
import edu.ntnu.idatt2105.SpringbootBackend.model.Question;
import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;
import edu.ntnu.idatt2105.SpringbootBackend.model.Tag;
import edu.ntnu.idatt2105.SpringbootBackend.repository.TagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class QuestionMapper {

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private TagRepository tagRepository;

    public QuestionDTO toQuestionDTO(Question question) {
        if (question == null) {
            return null;
        }
        
        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setQuizId(question.getQuiz().getId());
        dto.setText(question.getText());
        dto.setQuestionType(question.getQuestionType());
        dto.setMultimediaLink(question.getMultimediaLink());
        dto.setCreationDate(question.getCreationDate());
        dto.setImageId(question.getImage() != null ? question.getImage().getId() : null);

        
        return dto;
    }

    public Question toQuestion(QuestionCreateDTO dto, Quiz quiz) {
        if (dto == null) {
            return null;
        }
        
        Question question = new Question();
        question.setQuiz(quiz);
        question.setText(dto.getText());
        question.setQuestionType(dto.getQuestionType());
        question.setMultimediaLink(dto.getMultimediaLink());
        question.setCreationDate(LocalDateTime.now());
        
        return question;
    }

    public Question updateQuestionFromDTO(QuestionDTO dto, Question question) {
        if (dto == null || question == null) {
            return null;
        }
        
        question.setText(dto.getText());
        question.setQuestionType(dto.getQuestionType());
        question.setMultimediaLink(dto.getMultimediaLink());
        // Note: CreationDate and QuizId should not be updated here
        
        return question;
    }

    public Question toEntity(CompleteQuestionDTO dto, Quiz quiz) {
        if (dto == null) {
            return null;
        }
        
        Question question = new Question();
        question.setQuiz(quiz);
        question.setText(dto.getText());
        question.setQuestionType(dto.getQuestionType());
        question.setMultimediaLink(dto.getMultimediaLink());
        question.setCreationDate(LocalDateTime.now());

        if (dto.getImageData() != null && !dto.getImageData().isEmpty()) {
            byte[] decodedImg = Base64.getDecoder().decode(dto.getImageData());
            Image image = Image.builder()
                    .filename(dto.getImageName())
                    .fileType(dto.getImageType())
                    .size(decodedImg.length)
                    .data(decodedImg)
                    .build();
            question.setImage(image);
        }

        if (dto.getTags() != null) {
            Set<Tag> tags = dto.getTags().stream()
                    .map(tagName -> tagRepository.findByName(tagName)
                            .orElseGet(() -> {
                                Tag newTag = new Tag();
                                newTag.setName(tagName);
                                return tagRepository.save(newTag); // Create and save a new Tag if one with the name doesn't exist
                            }))
                    .collect(Collectors.toSet());
            question.setTags(tags);
        }

        if (dto.getAnswers() != null) {
            question.setAnswers(dto.getAnswers().stream()
                    .map(answerCreateDTO -> answerMapper.toEntity(answerCreateDTO, question)) // Adjust this method in AnswerMapper
                    .collect(Collectors.toSet()));
        }

        
        return question;
    }

    public CompleteQuestionDTO toCompleteQuestionDTO(Question question) {
        if (question == null) {
            return null;
        }
        CompleteQuestionDTO dto = new CompleteQuestionDTO();
        dto.setText(question.getText());
        dto.setQuestionType(question.getQuestionType());
        dto.setMultimediaLink(question.getMultimediaLink());

        // Include image information if available
        if (question.getImage() != null) {
            dto.setImageName(question.getImage().getFilename());
            dto.setImageType(question.getImage().getFileType());
            // Normally, imageData is not included here to avoid large payloads
        }
        dto.setTags(question.getTags().stream()
            .map(Tag::getName) // Use getName() instead of getId()
            .collect(Collectors.toSet()));

            dto.setTags(question.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.toSet()));
                
        return dto;
    }
}
