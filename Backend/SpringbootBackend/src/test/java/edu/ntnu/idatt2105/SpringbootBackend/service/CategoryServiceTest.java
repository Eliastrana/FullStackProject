package edu.ntnu.idatt2105.SpringbootBackend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import edu.ntnu.idatt2105.SpringbootBackend.dto.CategoryDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.CategoryAlreadyExistsException;
import edu.ntnu.idatt2105.SpringbootBackend.model.Category;
import edu.ntnu.idatt2105.SpringbootBackend.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void createCategory_BasicSuccess() {
        // Arrange
        CategoryDTO newCategoryDTO = new CategoryDTO(null, "Basic", "Description");
        Category savedCategory = new Category();
        savedCategory.setId(UUID.randomUUID());
        savedCategory.setCategoryName("Basic");
        savedCategory.setDescription("Description");

        when(categoryRepository.existsByCategoryName(anyString())).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> {
            Category category = invocation.getArgument(0);
            if (category.getId() == null) {
                category.setId(UUID.randomUUID());
            }
            return category;
        });

        // Act
        CategoryDTO result = categoryService.createCategory(newCategoryDTO);

        // Assert
        assertNotNull(result.getId());
        assertEquals("Basic", result.getCategoryName());
        assertEquals("Description", result.getDescription());

        // Verify
        verify(categoryRepository).existsByCategoryName("Basic");
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
void createCategory_WhenCategoryAlreadyExists_ThrowsException() {
    // Arrange
    CategoryDTO newCategoryDTO = new CategoryDTO(null, "Existing", "This category already exists.");
    when(categoryRepository.existsByCategoryName(newCategoryDTO.getCategoryName())).thenReturn(true);

    // Act & Assert
    assertThrows(CategoryAlreadyExistsException.class, () -> categoryService.createCategory(newCategoryDTO));

    // Verify
    verify(categoryRepository, never()).save(any(Category.class));
}

@Test
void getAllCategories_ReturnsAllCategories() {
    // Arrange
    List<Category> categories = List.of(
            new Category(UUID.randomUUID(), "Programming", "Related to programming."),
            new Category(UUID.randomUUID(), "Science", "Related to science.")
    );
    when(categoryRepository.findAll()).thenReturn(categories);

    // Act
    List<CategoryDTO> result = categoryService.getAllCategories();

    // Assert
    assertEquals(2, result.size());
    assertEquals("Programming", result.get(0).getCategoryName());
    assertEquals("Science", result.get(1).getCategoryName());

    // Verify
    verify(categoryRepository).findAll();
}

@Test
void getCategoryById_ReturnsCorrectCategory() {
    // Arrange
    UUID categoryId = UUID.randomUUID();
    Category category = new Category(categoryId, "Math", "Mathematics related.");
    when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

    // Act
    CategoryDTO result = categoryService.getCategoryById(categoryId);

    // Assert
    assertEquals("Math", result.getCategoryName());
    assertEquals("Mathematics related.", result.getDescription());

    // Verify
    verify(categoryRepository).findById(categoryId);
}

@Test
void updateCategory_SuccessfullyUpdatesCategory() {
    // Arrange
    UUID categoryId = UUID.randomUUID();
    Category existingCategory = new Category(categoryId, "OldName", "Old description");
    CategoryDTO categoryUpdateDTO = new CategoryDTO(categoryId, "NewName", "New description");

    when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
    when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

    // Act
    CategoryDTO updatedCategory = categoryService.updateCategory(categoryId, categoryUpdateDTO);

    // Assert
    assertEquals("NewName", updatedCategory.getCategoryName());
    assertEquals("New description", updatedCategory.getDescription());

    // Verify
    verify(categoryRepository).findById(categoryId);
    verify(categoryRepository).save(any(Category.class));
}

@Test
void deleteCategory_DeletesCategorySuccessfully() {
    // Arrange
    UUID categoryId = UUID.randomUUID();

    // Act
    categoryService.deleteCategory(categoryId);

    // Assert (verification here since deletion returns void)
    verify(categoryRepository).deleteById(categoryId);
}
}
