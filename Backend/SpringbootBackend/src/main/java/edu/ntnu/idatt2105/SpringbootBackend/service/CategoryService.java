package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.dto.CategoryDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.CategoryAlreadyExistsException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.CategoryNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.model.Category;
import edu.ntnu.idatt2105.SpringbootBackend.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The {@code CategoryService} class is responsible for managing categories within the system.
 * It provides functionality for creating, retrieving, updating, and deleting categories.
 * This service interacts with {@link CategoryRepository} to persist category data.
 *
 * @version 0.1
 * @since 0.1
 * @author Vegard Johnsen
 * @see CategoryDTO
 * @see Category
 * @see CategoryRepository
 */
@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  // Constructor injection
  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  /**
   * Creates a new category with the provided details encapsulated in {@link CategoryDTO}.
   * Throws {@link CategoryAlreadyExistsException} if a category with the same name already exists.
   *
   * @param categoryDTO The category data transfer object containing the new category's details.
   * @return A {@link CategoryDTO} representing the newly created category.
   * @throws CategoryAlreadyExistsException If the category name already exists.
   */
  @Transactional
  public CategoryDTO createCategory(CategoryDTO categoryDTO) {
    if (categoryRepository.existsByCategoryName(categoryDTO.getCategoryName())) {
      throw new CategoryAlreadyExistsException(categoryDTO.getCategoryName());
    }
    Category category = new Category();
    category.setCategoryName(categoryDTO.getCategoryName());
    category.setDescription(categoryDTO.getDescription());
    category = categoryRepository.save(category);
    return new CategoryDTO(category.getId(), category.getCategoryName(), category.getDescription());
  }

  /**
   * Retrieves all categories from the database and converts them to a list of {@link CategoryDTO}.
   *
   * @return A list of {@link CategoryDTO} representing all categories.
   */
  public List<CategoryDTO> getAllCategories() {
    return categoryRepository.findAll().stream()
            .map(category -> new CategoryDTO(category.getId(), category.getCategoryName(), category.getDescription()))
            .collect(Collectors.toList());
  }

  /**
   * Retrieves a category by its unique identifier.
   * Throws {@link CategoryNotFoundException} if the category is not found.
   *
   * @param id The unique identifier of the category.
   * @return A {@link CategoryDTO} representing the found category.
   * @throws CategoryNotFoundException If no category is found with the provided ID.
   */
  public CategoryDTO getCategoryById(UUID id) {
    Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new CategoryNotFoundException(id));
    return new CategoryDTO(category.getId(), category.getCategoryName(), category.getDescription());
  }

  /**
   * Updates an existing category identified by the provided ID with new details.
   * Throws {@link CategoryNotFoundException} if the category to update is not found.
   *
   * @param id The unique identifier of the category to update.
   * @param categoryDTO The new category details.
   * @return A {@link CategoryDTO} representing the updated category.
   * @throws CategoryNotFoundException If no category is found with the provided ID.
   */
  @Transactional
  public CategoryDTO updateCategory(UUID id, CategoryDTO categoryDTO) {
    Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    category.setCategoryName(categoryDTO.getCategoryName());
    category.setDescription(categoryDTO.getDescription());
    category = categoryRepository.save(category);
    return new CategoryDTO(category.getId(), category.getCategoryName(), category.getDescription());
  }

  /**
   * Deletes a category by its unique identifier.
   * If the category to be deleted is not found, the operation is ignored.
   *
   * @param id The unique identifier of the category to delete.
   */
  @Transactional
  public void deleteCategory(UUID id) {
    categoryRepository.deleteById(id);
  }
}
