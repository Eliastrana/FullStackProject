package edu.ntnu.idatt2105.SpringbootBackend.exception;

import java.util.UUID;

/**
 * Exception indicating that a requested tag cannot be found in the system.
 * This can occur when querying for a tag by its name or unique identifier (ID),
 * and no matching tag is present. It is used to signal that an operation
 * referring to a specific tag cannot proceed due to the tag's absence.
 *
 * @author Vegard Johnsen, sander rom skofsrud
 * @version 0.1
 * @since 0.1
 */
public class TagNotFoundException extends RuntimeException {

  /**
   * Constructs a new {@code TagNotFoundException} with a detail message constructed using the given tag name.
   *
   * @param name the name of the tag that was not found.
   */
  public TagNotFoundException(String name) {
    super("Tag not found with name: " + name);
  }

  /**
   * Constructs a new {@code TagNotFoundException} with a detail message constructed using the given tag ID.
   *
   * @param id the unique identifier (UUID) of the tag that was not found.
   */
  public TagNotFoundException(UUID id) {
    super("Tag not found with id: " + id);
  }
}
