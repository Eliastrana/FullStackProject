package edu.ntnu.idatt2105.SpringbootBackend.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDTOTest {

    @Test
    void testAllArgsConstructor() {
        UUID id = UUID.randomUUID();
        CategoryDTO category = new CategoryDTO(id, "Science", "Questions related to scientific facts.");
        
        assertNotNull(category);
        assertEquals(id, category.getId());
        assertEquals("Science", category.getCategoryName());
        assertEquals("Questions related to scientific facts.", category.getDescription());
    }

    @Test
    void testBuilder() {
        UUID id = UUID.randomUUID();
        CategoryDTO category = CategoryDTO.builder()
                                           .id(id)
                                           .categoryName("Mathematics")
                                           .description("Questions related to mathematical concepts.")
                                           .build();
        
        assertNotNull(category);
        assertEquals(id, category.getId());
        assertEquals("Mathematics", category.getCategoryName());
        assertEquals("Questions related to mathematical concepts.", category.getDescription());
    }

    @Test
    void testNullableField() {
        CategoryDTO category = CategoryDTO.builder()
                                           .id(UUID.randomUUID())
                                           .categoryName("Art")
                                           .build(); // description is not set, testing its nullability
        
        assertNotNull(category);
        assertNull(category.getDescription()); // The description should be null if not set
    }
}
