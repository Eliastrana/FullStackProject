package edu.ntnu.idatt2105.SpringbootBackend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TagTest {

    private Tag tag;

    @BeforeEach
    public void setup() {
        tag = new Tag();
    }

    @Test
    public void testId() {
        UUID id = UUID.randomUUID();
        tag.setId(id);
        assertEquals(id, tag.getId());
    }

    @Test
    public void testName() {
        String name = "Test Tag";
        tag.setName(name);
        assertEquals(name, tag.getName());
    }

    @Test
    public void testQuestions() {
        Set<Question> questions = new HashSet<>();
        questions.add(new Question());
        tag.setQuestions(questions);
        assertNotNull(tag.getQuestions());
        assertEquals(questions.size(), tag.getQuestions().size());
    }

}
