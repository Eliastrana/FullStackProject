package edu.ntnu.idatt2105.SpringbootBackend.exception;

/**
 * Exception indicating that a tag already exists in the system.
 * This exception is thrown when an attempt is made to create a new tag
 * with a name that is already used by an existing tag. Ensures that all tags
 * have unique names within the system.
 *
 * @author Sander rom skofsrud
 * @version 0.1
 * @since 0.1
 */
public class TagAlreadyExistsException extends RuntimeException {

  /**
   * Constructs a new {@code TagAlreadyExistsException} with the specified detail message.
   * The detail message provides more information about the tag that already exists.
   *
   * @param message the detail message. The detail message is saved for
   * later retrieval by the {@link #getMessage()} method.
   */
  public TagAlreadyExistsException(String message) {
    super(message);
  }
}
