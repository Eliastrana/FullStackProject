package edu.ntnu.idatt2105.SpringbootBackend.repository;

import edu.ntnu.idatt2105.SpringbootBackend.model.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @Test
    public void testFindByName() {
        Tag tag = new Tag();
        tag.setName("Test Tag");
        tag = tagRepository.save(tag);

        Optional<Tag> foundTag = tagRepository.findByName("Test Tag");

        assertTrue(foundTag.isPresent());
        assertEquals(tag.getName(), foundTag.get().getName());
    }
}
