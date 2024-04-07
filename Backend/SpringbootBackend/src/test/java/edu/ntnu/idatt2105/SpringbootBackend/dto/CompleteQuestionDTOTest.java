package edu.ntnu.idatt2105.SpringbootBackend.dto;

import edu.ntnu.idatt2105.SpringbootBackend.model.QuestionType;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CompleteQuestionDTOTest {

    @Test
    void testNoArgsConstructor() {
        CompleteQuestionDTO dto = new CompleteQuestionDTO();
        assertNotNull(dto);
    }

    @Test
    void testAllArgsConstructor() {
        Set<String> tags = new HashSet<>();
        tags.add("Soccer");
        tags.add("Rules");

        Set<AnswerCreateDTO> answers = new HashSet<>();
        answers.add(new AnswerCreateDTO("Yes", true));
        answers.add(new AnswerCreateDTO("No", false));

        UUID id = UUID.randomUUID();
        CompleteQuestionDTO dto = new CompleteQuestionDTO(
                id, "What is the penalty for a handball in soccer?",
                QuestionType.MULTIPLE_CHOICE,
                "https://example.com/video/rules-of-handball-in-soccer",
                tags,
                answers,
                "soccer_rules.png",
                "png",
                "base64ImageData"
        );

        assertNotNull(dto);
        assertEquals("What is the penalty for a handball in soccer?", dto.getText());
        assertEquals(QuestionType.MULTIPLE_CHOICE, dto.getQuestionType());
        assertEquals("https://example.com/video/rules-of-handball-in-soccer", dto.getMultimediaLink());
        assertEquals(2, dto.getTags().size());
        assertTrue(dto.getTags().contains("Soccer"));
        assertEquals(2, dto.getAnswers().size());
        assertEquals("soccer_rules.png", dto.getImageName());
        assertEquals("png", dto.getImageType());
        assertNotNull(dto.getImageData());
    }

    @Test
    void testOptionalFields() {
        CompleteQuestionDTO dto = new CompleteQuestionDTO();
        dto.setText("Is soccer a popular sport worldwide?");
        dto.setQuestionType(QuestionType.MULTIPLE_CHOICE);
        dto.setTags(new HashSet<>()); // Tags are optional, so initializing to empty
        dto.setAnswers(new HashSet<>()); // Same for answers

        assertNotNull(dto);
        assertNull(dto.getMultimediaLink()); // Multimedia link is not set, so it should be null
        assertTrue(dto.getTags().isEmpty());
        assertTrue(dto.getAnswers().isEmpty());
    }

    @Test
    void testCollectionManipulation() {
        CompleteQuestionDTO dto = new CompleteQuestionDTO();
        dto.setTags(new HashSet<>());
        dto.setAnswers(new HashSet<>());

        dto.getTags().add("Football");
        dto.getAnswers().add(new AnswerCreateDTO("Outside the box", false));

        assertEquals(1, dto.getTags().size());
        assertEquals(1, dto.getAnswers().size());
    }

    @Test
    void testOptionalFieldsAfterInitialization() {
        CompleteQuestionDTO dto = new CompleteQuestionDTO();
        dto.setMultimediaLink("https://example.com/initial_link");
        
        assertNotNull(dto.getMultimediaLink());

        // Now explicitly set to null to test handling
        dto.setMultimediaLink(null);
        assertNull(dto.getMultimediaLink());
    }

    @Test
    void testEdgeCasesInFields() {
        String longText = "This is a very long question text".repeat(10); // Create a long string
        String imageDataWithSpecialChars = "iVBORw0KGgo@@@$$$###"; // Include special characters

        CompleteQuestionDTO dto = new CompleteQuestionDTO();
                dto.setText(longText);
                dto.setQuestionType(QuestionType.MULTIPLE_CHOICE);
                dto.setImageData(imageDataWithSpecialChars);

        assertEquals(longText, dto.getText());
        assertEquals(imageDataWithSpecialChars, dto.getImageData());
    }

}
