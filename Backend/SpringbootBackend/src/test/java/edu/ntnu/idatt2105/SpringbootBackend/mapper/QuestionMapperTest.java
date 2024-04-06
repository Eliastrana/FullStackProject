package edu.ntnu.idatt2105.SpringbootBackend.mapper;

import edu.ntnu.idatt2105.SpringbootBackend.dto.*;
import edu.ntnu.idatt2105.SpringbootBackend.model.*;
import edu.ntnu.idatt2105.SpringbootBackend.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class QuestionMapperTest {

    @InjectMocks
    private QuestionMapper questionMapper;

    @Mock
    private AnswerMapper answerMapper;

    @Mock
    private TagRepository tagRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToQuestionDTOWithNull() {
        assertNull(questionMapper.toQuestionDTO(null));
    }

    @Test
    void testToEntityWithCompleteQuestionDTO() {
        CompleteQuestionDTO dto = new CompleteQuestionDTO();
        dto.setText("What is the capital of Norway?");
        dto.setQuestionType(QuestionType.MULTIPLE_CHOICE);
        dto.setMultimediaLink("https://example.com/oslo.jpg");
        dto.setTags(new HashSet<>(Set.of("Geography", "Europe")));
        Quiz quiz = new Quiz();

        when(tagRepository.findByName(anyString())).thenAnswer(i -> {
            String name = i.getArgument(0);
            Tag tag = new Tag();
            tag.setName(name);
            return Optional.of(tag);
        });

        Question question = questionMapper.toEntity(dto, quiz);

        assertNotNull(question);
        assertEquals(dto.getText(), question.getText());
        assertEquals(dto.getQuestionType(), question.getQuestionType());
        assertEquals(dto.getMultimediaLink(), question.getMultimediaLink());
        assertNotNull(question.getTags());
        assertEquals(2, question.getTags().size());
    }

    @Test
    void testUpdateQuestionFromDTO() {
        QuestionDTO dto = new QuestionDTO();
        dto.setText("Updated Question?");
        dto.setQuestionType(QuestionType.MULTIPLE_CHOICE);
        dto.setMultimediaLink("https://example.com/updated.jpg");

        Question question = new Question();
        question.setText("Original Question");
        question.setQuestionType(QuestionType.STUDY);
        question.setMultimediaLink("https://example.com/original.jpg");

        Question updatedQuestion = questionMapper.updateQuestionFromDTO(dto, question);

        assertNotNull(updatedQuestion);
        assertEquals(dto.getText(), updatedQuestion.getText());
        assertEquals(dto.getQuestionType(), updatedQuestion.getQuestionType());
        assertEquals(dto.getMultimediaLink(), updatedQuestion.getMultimediaLink());
    }
    @Test
void testToQuestionDTOWithAllFields() {
    // Create a Question entity with all fields populated
    Question question = new Question();
    question.setId(UUID.randomUUID());
    question.setQuiz(new Quiz());
    question.getQuiz().setId(UUID.randomUUID());
    question.setText("Sample question text");
    question.setQuestionType(QuestionType.MULTIPLE_CHOICE);
    question.setMultimediaLink("https://example.com/image.jpg");
    question.setCreationDate(LocalDateTime.now());
    question.setImage(new Image(UUID.randomUUID(), "image.jpg", "image/jpeg", 1024, new byte[1024]));
    Set<Tag> tags = new HashSet<>();
    Tag tag1 = new Tag();
    tag1.setId(UUID.randomUUID());
    tag1.setName("Tag 1");
    tags.add(tag1);
    question.setTags(tags);

    // Map the Question entity to DTO
    QuestionDTO dto = questionMapper.toQuestionDTO(question);

    // Verify that all fields are correctly mapped
    assertEquals(question.getId(), dto.getId());
    assertEquals(question.getQuiz().getId(), dto.getQuizId());
    assertEquals(question.getText(), dto.getText());
    assertEquals(question.getQuestionType(), dto.getQuestionType());
    assertEquals(question.getMultimediaLink(), dto.getMultimediaLink());
    assertEquals(question.getCreationDate(), dto.getCreationDate());
    assertEquals(question.getImage().getId(), dto.getImageId());
}


}
