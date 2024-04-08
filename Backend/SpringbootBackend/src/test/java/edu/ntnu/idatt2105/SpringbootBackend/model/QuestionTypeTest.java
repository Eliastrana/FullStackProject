package edu.ntnu.idatt2105.SpringbootBackend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class QuestionTypeTest {

    @Test
    public void testQuestionTypeValues() {
        QuestionType[] actualValues = QuestionType.values();
        QuestionType[] expectedValues = { QuestionType.MULTIPLE_CHOICE, QuestionType.STUDY, QuestionType.FILL_IN_BLANK };
        assertArrayEquals(expectedValues, actualValues);
    }
}