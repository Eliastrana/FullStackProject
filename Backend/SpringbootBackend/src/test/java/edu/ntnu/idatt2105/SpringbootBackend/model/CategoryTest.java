package edu.ntnu.idatt2105.SpringbootBackend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CategoryTest {

    private Category category;

    @BeforeEach
    public void setup() {
        category = new Category();
    }

    @Test
    public void testId() {
        UUID id = UUID.randomUUID();
        category.setId(id);
        assertEquals(id, category.getId());
    }

    @Test
    public void testCategoryName() {
        String categoryName = "Test Category";
        category.setCategoryName(categoryName);
        assertEquals(categoryName, category.getCategoryName());
    }

    @Test
    public void testDescription() {
        String description = "This is a test category.";
        category.setDescription(description);
        assertEquals(description, category.getDescription());

        category.setDescription(null);
        assertNull(category.getDescription());
    }

}