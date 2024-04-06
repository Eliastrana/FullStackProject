package edu.ntnu.idatt2105.SpringbootBackend.dto;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TagDTOTest {

    @Test
    void testNoArgsConstructorAndFieldAssignments() {
        TagDTO tag = new TagDTO();
        
        UUID id = UUID.randomUUID();
        tag.setId(id);
        tag.setName("History");
        
        Set<UUID> questionIds = new HashSet<>();
        UUID questionId1 = UUID.randomUUID();
        UUID questionId2 = UUID.randomUUID();
        questionIds.add(questionId1);
        questionIds.add(questionId2);
        tag.setQuestionIds(questionIds);

        assertEquals(id, tag.getId());
        assertEquals("History", tag.getName());
        assertTrue(tag.getQuestionIds().contains(questionId1));
        assertTrue(tag.getQuestionIds().contains(questionId2));
        assertEquals(2, tag.getQuestionIds().size());
    }

    @Test
    void testCollectionBehavior() {
        TagDTO tag = new TagDTO();
        
        Set<UUID> questionIds = new HashSet<>();
        UUID questionId = UUID.randomUUID();
        questionIds.add(questionId);
        tag.setQuestionIds(questionIds);

        // Test addition
        UUID newQuestionId = UUID.randomUUID();
        tag.getQuestionIds().add(newQuestionId);
        assertEquals(2, tag.getQuestionIds().size());
        assertTrue(tag.getQuestionIds().contains(newQuestionId));

        // Test removal
        tag.getQuestionIds().remove(newQuestionId);
        assertEquals(1, tag.getQuestionIds().size());
        assertFalse(tag.getQuestionIds().contains(newQuestionId));
    }
}
