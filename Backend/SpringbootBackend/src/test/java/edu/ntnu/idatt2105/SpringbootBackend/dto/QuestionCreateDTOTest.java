package edu.ntnu.idatt2105.SpringbootBackend.dto;

import edu.ntnu.idatt2105.SpringbootBackend.model.QuestionType;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class QuestionCreateDTOTest {

    @Test
    void testBuilderAndFieldAssignments() {
        Set<UUID> tags = new HashSet<>();
        tags.add(UUID.randomUUID());
        tags.add(UUID.randomUUID());

        Set<AnswerCreateDTO> answers = new HashSet<>();
        answers.add(new AnswerCreateDTO("Paris", true));
        answers.add(new AnswerCreateDTO("London", false));

        UUID imageId = UUID.randomUUID();

        QuestionCreateDTO question = QuestionCreateDTO.builder()
                .text("What is the capital of France?")
                .questionType(QuestionType.MULTIPLE_CHOICE)
                .multimediaLink("https://example.com/image.png")
                .tags(tags)
                .answers(answers)
                .imageId(imageId)
                .build();

        assertEquals("What is the capital of France?", question.getText());
        assertEquals(QuestionType.MULTIPLE_CHOICE, question.getQuestionType());
        assertEquals("https://example.com/image.png", question.getMultimediaLink());
        assertEquals(2, question.getTags().size());
        assertEquals(2, question.getAnswers().size());
        assertEquals(imageId, question.getImageId());
    }

    @Test
    void testOptionalFields() {
        QuestionCreateDTO question = QuestionCreateDTO.builder()
                .text("What is the highest mountain in the world?")
                .questionType(QuestionType.MULTIPLE_CHOICE)
                .build();

        assertNull(question.getMultimediaLink());
        assertNull(question.getImageId());
        assertTrue(question.getTags() == null || question.getTags().isEmpty());
        assertTrue(question.getAnswers() == null || question.getAnswers().isEmpty());
    }

    @Test
    void testHandlingEmptyCollections() {
        QuestionCreateDTO question = QuestionCreateDTO.builder()
                .text("Which planet is closest to the sun?")
                .questionType(QuestionType.MULTIPLE_CHOICE)
                .tags(new HashSet<>()) // Explicitly empty
                .answers(new HashSet<>()) // Explicitly empty
                .build();

        assertNotNull(question.getTags());
        assertTrue(question.getTags().isEmpty());
        assertNotNull(question.getAnswers());
        assertTrue(question.getAnswers().isEmpty());
    }
    @Test
    void testModificationOfCollectionFields() {
        QuestionCreateDTO question = QuestionCreateDTO.builder()
                .text("What is the chemical symbol for water?")
                .questionType(QuestionType.STUDY)
                .tags(new HashSet<>())
                .answers(new HashSet<>())
                .build();

        UUID tag1 = UUID.randomUUID();
        question.getTags().add(tag1);

        AnswerCreateDTO answer = new AnswerCreateDTO("H2O", true);
        question.getAnswers().add(answer);

        assertEquals(1, question.getTags().size());
        assertTrue(question.getTags().contains(tag1));
        assertEquals(1, question.getAnswers().size());
        assertTrue(question.getAnswers().contains(answer));
    }
}
