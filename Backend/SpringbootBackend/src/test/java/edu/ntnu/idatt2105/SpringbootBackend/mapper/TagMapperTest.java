package edu.ntnu.idatt2105.SpringbootBackend.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idatt2105.SpringbootBackend.dto.TagDTO;
import edu.ntnu.idatt2105.SpringbootBackend.model.Question;
import edu.ntnu.idatt2105.SpringbootBackend.model.Tag;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.UUID;

class TagMapperTest {

    private TagMapper tagMapper;

    @BeforeEach
    void setUp() {
        tagMapper = new TagMapper(); // Assuming TagMapper has a no-args constructor
    }

    @Test
    void toTagDTOTest() {
        // Setup
        UUID questionId1 = UUID.randomUUID();
        UUID questionId2 = UUID.randomUUID();

        Question question1 = new Question();
        question1.setId(questionId1);
        Question question2 = new Question();
        question2.setId(questionId2);

        Tag tag = new Tag();
        tag.setId(UUID.randomUUID());
        tag.setName("History");
        tag.setQuestions(Set.of(question1, question2));

        // Act
        TagDTO result = tagMapper.toTagDTO(tag);

        // Assert
        assertEquals(tag.getId(), result.getId());
        assertEquals(tag.getName(), result.getName());
        assertTrue(result.getQuestionIds().contains(questionId1));
        assertTrue(result.getQuestionIds().contains(questionId2));
    }

    @Test
    void toEntityTest() {
    // Setup
    UUID tagId = UUID.randomUUID();
    Set<UUID> questionIds = Set.of(UUID.randomUUID(), UUID.randomUUID());
    
    TagDTO tagDTO = new TagDTO();
    tagDTO.setId(tagId);
    tagDTO.setName("Math");
    tagDTO.setQuestionIds(questionIds);
    
    // Act
    Tag tag = tagMapper.toEntity(tagDTO);
    
    // Assert
    assertEquals(tagId, tag.getId());
    assertEquals("Math", tag.getName());
    assertNotNull(tag.getQuestions());
    assertEquals(questionIds.size(), tag.getQuestions().size());
    tag.getQuestions().forEach(question -> assertTrue(questionIds.contains(question.getId())));
    }

    @Test
    void toDtoSetTest() {
    // Setup
    Tag tag1 = createTestTag(UUID.randomUUID(), "Science");
    Tag tag2 = createTestTag(UUID.randomUUID(), "Literature");
    Set<Tag> tags = Set.of(tag1, tag2);
    
    // Act
    Set<TagDTO> result = tagMapper.toDtoSet(tags);
    
    // Assert
    assertEquals(tags.size(), result.size());
    result.forEach(dto -> {
        Tag originalTag = tags.stream().filter(tag -> tag.getId().equals(dto.getId())).findFirst().orElse(null);
        assertNotNull(originalTag);
        assertEquals(originalTag.getName(), dto.getName());
        assertEquals(originalTag.getQuestions().size(), dto.getQuestionIds().size());
    });
}
private Tag createTestTag(UUID id, String name) {
    Tag tag = new Tag();
    tag.setId(id);
    tag.setName(name);
    Question question1 = new Question();
    question1.setId(UUID.randomUUID());
    Question question2 = new Question();
    question2.setId(UUID.randomUUID());
    tag.setQuestions(Set.of(question1, question2));
    return tag;
}
@Test
void toEntitySetTest() {
    // Setup
    TagDTO dto1 = new TagDTO();
    dto1.setId(UUID.randomUUID());
    dto1.setName("History");
    dto1.setQuestionIds(Set.of(UUID.randomUUID(), UUID.randomUUID()));


    TagDTO dto2 = new TagDTO();
    dto2.setId(UUID.randomUUID());
    dto2.setName("Math");
    dto2.setQuestionIds(Set.of(UUID.randomUUID(), UUID.randomUUID()));
    Set<TagDTO> dtos = Set.of(dto1, dto2);
    
    // Act
    Set<Tag> result = tagMapper.toEntitySet(dtos);
    
    // Assert
    assertEquals(dtos.size(), result.size());
    result.forEach(tag -> {
        TagDTO originalDto = dtos.stream().filter(dto -> dto.getId().equals(tag.getId())).findFirst().orElse(null);
        assertNotNull(originalDto);
        assertEquals(originalDto.getName(), tag.getName());
        assertEquals(originalDto.getQuestionIds().size(), tag.getQuestions().size());
    });
}

}

