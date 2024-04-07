package edu.ntnu.idatt2105.SpringbootBackend.controller;

import edu.ntnu.idatt2105.SpringbootBackend.dto.CategoryDTO;
import edu.ntnu.idatt2105.SpringbootBackend.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * The CategoryController class is responsible for handling HTTP requests related to category operations.
 * It provides endpoints for creating, retrieving, updating, and deleting categories.
 * Utilizes {@link CategoryService} for business logic and interacts with {@link CategoryDTO} for data transfer.
 * <p>
 * This controller is part of the category management module of the application,
 * allowing for the manipulation and retrieval of category data essential for quiz categorization.
 *
 * @author Vegard Johnsen, Sander R. Skofsrud
 * @version 0.1
 * @since 0.1
 * @see CategoryService for category management operations
 * @see CategoryDTO for data transfer object structure
 */

@Tag(name = "Category Management")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

/**
   * Creates a new category with the provided details.
   * This method accepts a {@link CategoryDTO} object containing the category details,
   * processes it through the {@link CategoryService}, and saves the new category.
   *
   * @param categoryDTO The {@link CategoryDTO} object containing the new category's details.
   * @return A {@link ResponseEntity<CategoryDTO>} containing the created {@link CategoryDTO}
   * and the HTTP status {@link HttpStatus#CREATED} if the creation is successful;
   * otherwise, returns a response entity with an appropriate error status.
   *
   */
  @Operation(summary = "Create a new category", description = "Creates a new category with the provided details", responses = {
    @ApiResponse(responseCode = "201", description = "Category created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class))),
    @ApiResponse(responseCode = "400", description = "Bad request when the category details are invalid")}, security = {@SecurityRequirement(name = "bearerAuth")})
  @PostMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CategoryDTO> createCategory(
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Category details to be created", required = true, content = @Content(schema = @Schema(implementation = CategoryDTO.class)))
    @RequestBody CategoryDTO categoryDTO) {
      CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
    return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
  }

/**
   * Retrieves all categories from the system.
   * This method calls the {@link CategoryService} to fetch all existing categories
   * and returns them in a list. It does not require any specific identifier since it aims
   * to return all categories available in the system.
   *
   * @return A {@link ResponseEntity} containing a list of {@link CategoryDTO} objects
   * representing all categories and the HTTP status {@link HttpStatus#OK}.
   */
  @Operation(summary = "Get all categories", description = "Retrieves all the categories", responses = {
    @ApiResponse(responseCode = "200", description = "Categories fetched successfully", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CategoryDTO.class))))})
  @GetMapping
  public ResponseEntity<List<CategoryDTO>> getAllCategories() {
    List<CategoryDTO> categories = categoryService.getAllCategories();
    return new ResponseEntity<>(categories, HttpStatus.OK);
  }

/**
   * Retrieves a specific category by its unique identifier (ID).
   * This method uses the {@link CategoryService} to find a category by its ID.
   *
   * @param id The unique identifier (UUID) of the category to retrieve.
   * @return A {@link ResponseEntity<CategoryDTO>} containing the found {@link CategoryDTO}
   * if the category exists; otherwise, returns HTTP status {@link HttpStatus#NOT_FOUND}.
   */
  @Operation(summary = "Get a category by ID", description = "Retrieves a category by its unique identifier", responses = {
    @ApiResponse(responseCode = "200", description = "Category fetched successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class))),
    @ApiResponse(responseCode = "404", description = "Category not found")})
  @GetMapping("/{id}")
  public ResponseEntity<CategoryDTO> getCategoryById(
    @Parameter(description = "Unique identifier of the category", required = true)
    @PathVariable UUID id) {    CategoryDTO categoryDTO = categoryService.getCategoryById(id);
    return ResponseEntity.ok(categoryDTO);
  }

/**
   * Updates an existing category identified by its ID with new details.
   * This method takes a {@link CategoryDTO} with updated category details and an ID,
   * processes the update through the {@link CategoryService}, and returns the updated category.
   *
   * @param id The UUID of the category to update.
   * @param categoryDTO The {@link CategoryDTO} containing updated category details.
   * @return A {@link ResponseEntity<CategoryDTO>} containing the updated {@link CategoryDTO}
   * and HTTP status {@link HttpStatus#OK} if the update is successful; otherwise,
   * returns HTTP status {@link HttpStatus#NOT_FOUND} if the category is not found.
   */
  @Operation(summary = "Update a category", description = "Updates a category with the given ID and details", responses = {
    @ApiResponse(responseCode = "200", description = "Category updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class))),
    @ApiResponse(responseCode = "404", description = "Category not found")})
  @PutMapping("/{id}")
  public ResponseEntity<CategoryDTO> updateCategory(
    @Parameter(description = "Unique identifier of the category to be updated", required = true)
    @PathVariable UUID id,
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated category details", required = true, content = @Content(schema = @Schema(implementation = CategoryDTO.class)))
    @RequestBody CategoryDTO categoryDTO) {
      CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
    return ResponseEntity.ok(updatedCategory);
  }

/**
   * Deletes a category by its unique identifier (ID).
   * This method calls the {@link CategoryService} to delete a category based on its ID.
   *
   * @param id The UUID of the category to delete.
   * @return A {@link ResponseEntity<Void>} with HTTP status {@link HttpStatus#NO_CONTENT}
   * if the category is successfully deleted; otherwise, returns HTTP status {@link HttpStatus#NOT_FOUND}
   * if the category is not found.
   */
  @Operation(summary = "Delete a category", description = "Deletes a category with the given ID", responses = {
    @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
    @ApiResponse(responseCode = "404", description = "Category not found")})
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategory(
    @Parameter(description = "Unique identifier of the category to be deleted", required = true)
    @PathVariable UUID id) {
      categoryService.deleteCategory(id);
    return ResponseEntity.noContent().build();
  }
}