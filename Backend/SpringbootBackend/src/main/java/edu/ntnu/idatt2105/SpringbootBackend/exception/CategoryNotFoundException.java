package edu.ntnu.idatt2105.SpringbootBackend.exception;

import java.util.UUID;

/**
 * Exception thrown when a category with a specified identifier or name cannot be found in the system.
 * This can occur when performing operations that require an existing category, such as retrieving, updating, or deleting.
 *
 * @author Sander rom skofsrud, Vegard Johnsen
 *
 */
public class CategoryNotFoundException extends RuntimeException {

  /**
   * Constructs a new {@code CategoryNotFoundException} with the specified name.
   *
   * @param name The name of the category that was not found.
   */
  public CategoryNotFoundException(String name) {
    super("Category not found with id: " + name);
  }

  /**
   * Constructs a new {@code CategoryNotFoundException} with the specified UUID.
   *
   * @param id The UUID of the category that was not found.
   */
  public CategoryNotFoundException(UUID id) {
    super("Category not found with id: " + id);
  }
}