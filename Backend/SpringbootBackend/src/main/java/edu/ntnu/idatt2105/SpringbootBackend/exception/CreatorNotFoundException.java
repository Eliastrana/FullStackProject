package edu.ntnu.idatt2105.SpringbootBackend.exception;

/**
 * Exception thrown when a creator (typically a user or entity responsible for creating content) cannot be found.
 * This is used in contexts where operations require the existence of a specific creator,
 * such as attributing content or accessing creator-specific information.
 *
 * @author Vegard Johnsen
 *
 */

public class CreatorNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code CreatorNotFoundException} with the specified detail message.
     *
     * @param string The detail message identifying the creator that was not found.
     */
    public CreatorNotFoundException(String string) {
        super(String.format("Creator: %s not found.", string));
    }
    
}
