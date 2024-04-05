package edu.ntnu.idatt2105.SpringbootBackend.controller;

import edu.ntnu.idatt2105.SpringbootBackend.dto.CategoryDTO;
import edu.ntnu.idatt2105.SpringbootBackend.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Category Management")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @Operation(summary = "Create a new category", description = "Creates a new category with the provided details")
  @ApiResponse(responseCode = "201", description = "Category created successfully")
  @ApiResponse(responseCode = "400", description = "Bad request when the category details are invalid")
  @PostMapping("/")
  public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
    CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
    return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
  }

  @Operation(summary = "Get all categories", description = "Retrieves all the categories")
  @ApiResponse(responseCode = "200", description = "Categories fetched successfully")
  @GetMapping("/")
  public ResponseEntity<List<CategoryDTO>> getAllCategories() {
    List<CategoryDTO> categories = categoryService.getAllCategories();
    return new ResponseEntity<>(categories, HttpStatus.OK);
  }

  @Operation(summary = "Get a category by ID", description = "Retrieves a category by its unique identifier")
  @ApiResponse(responseCode = "200", description = "Category fetched successfully")
  @ApiResponse(responseCode = "404", description = "Category not found")
  @GetMapping("/{id}")
  public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable UUID id) {
    CategoryDTO categoryDTO = categoryService.getCategoryById(id);
    return ResponseEntity.ok(categoryDTO);
  }

  @Operation(summary = "Update a category", description = "Updates a category with the given ID and details")
  @ApiResponse(responseCode = "200", description = "Category updated successfully")
  @ApiResponse(responseCode = "404", description = "Category not found")
  @PutMapping("/{id}")
  public ResponseEntity<CategoryDTO> updateCategory(@PathVariable UUID id, @RequestBody CategoryDTO categoryDTO) {
    CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
    return ResponseEntity.ok(updatedCategory);
  }

  @Operation(summary = "Delete a category", description = "Deletes a category with the given ID")
  @ApiResponse(responseCode = "204", description = "Category deleted successfully")
  @ApiResponse(responseCode = "404", description = "Category not found")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
    categoryService.deleteCategory(id);
    return ResponseEntity.noContent().build();
  }
}
