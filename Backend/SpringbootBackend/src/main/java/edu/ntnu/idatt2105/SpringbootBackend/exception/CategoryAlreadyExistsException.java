package edu.ntnu.idatt2105.SpringbootBackend.exception;

/**
 * Exception thrown when an attempt is made to create a new category with a name that already exists in the system.
 * This ensures that category names are unique within the application, preventing duplicates.
 *
 * @author Sander rom skofsrud
 * @version 0.1
 * @since 0.1
 *
 * @see edu.ntnu.idatt2105.SpringbootBackend.service.CategoryService#createCategory
 */
public class CategoryAlreadyExistsException extends RuntimeException {

  /**
   * Constructs a new {@code CategoryAlreadyExistsException} with the specified category name.
   * The detail message is formatted to include the name of the category that caused the exception,
   * making it clear to the caller which category name is already in use.
   *
   * @param categoryName The name of the category that already exists,
   * and hence caused this exception to be thrown. This name is incorporated into the exception's detail message.
   */
  public CategoryAlreadyExistsException(String categoryName) {
    super("Category already exists with name: " + categoryName);
  }
}