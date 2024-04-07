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

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  // Constructor injection
  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

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

  public List<CategoryDTO> getAllCategories() {
    return categoryRepository.findAll().stream()
            .map(category -> new CategoryDTO(category.getId(), category.getCategoryName(), category.getDescription()))
            .collect(Collectors.toList());
  }

  public CategoryDTO getCategoryById(UUID id) {
    Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new CategoryNotFoundException(id));
    return new CategoryDTO(category.getId(), category.getCategoryName(), category.getDescription());
  }

  @Transactional
  public CategoryDTO updateCategory(UUID id, CategoryDTO categoryDTO) {
    Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    category.setCategoryName(categoryDTO.getCategoryName());
    category.setDescription(categoryDTO.getDescription());
    category = categoryRepository.save(category);
    return new CategoryDTO(category.getId(), category.getCategoryName(), category.getDescription());
  }

  @Transactional
  public void deleteCategory(UUID id) {
    categoryRepository.deleteById(id);
  }
}
