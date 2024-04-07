package edu.ntnu.idatt2105.SpringbootBackend.repository;

import edu.ntnu.idatt2105.SpringbootBackend.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testSaveAndRetrieveCategory() {
        // Setup
        String categoryName = "Science";
        Category category = Category.builder()
                .categoryName(categoryName)
                .description("A category for science quizzes")
                .build();

        // Act
        category = categoryRepository.save(category);
        Optional<Category> retrievedCategory = categoryRepository.findById(category.getId());

        // Assert
        assertTrue(retrievedCategory.isPresent());
        assertEquals(categoryName, retrievedCategory.get().getCategoryName());
        assertEquals("A category for science quizzes", retrievedCategory.get().getDescription());
    }

    @Test
    void testCategoryNameUniqueConstraint() {
        // Setup
        Category category1 = new Category(null, "History", "Description for history category");
        Category category2 = new Category(null, "History", "Another description");

        // Act & Assert
        categoryRepository.save(category1);
        assertThrows(Exception.class, () -> categoryRepository.saveAndFlush(category2),
                "Should throw exception due to unique constraint on categoryName");
    }
}
