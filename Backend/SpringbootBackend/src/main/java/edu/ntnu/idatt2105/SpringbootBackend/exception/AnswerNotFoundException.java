package edu.ntnu.idatt2105.SpringbootBackend.exception;

/**
 * Exception thrown when an answer cannot be found in the database.
 * This typically indicates that the answer with the specified identifier does not exist,
 * or it is not accessible due to privacy or deletion.
 *
 * @author Vegard Johnsen
 * @version 0.1
 * @since 0.1
 *
 * @see edu.ntnu.idatt2105.SpringbootBackend.service.AnswerService#getAnswerById
 */
public class AnswerNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code AnswerNotFoundException} with the specified detail message.
     * The detail message is formatted with the identifier of the answer that could not be found.
     *
     * @param message The identifier of the answer that was not found,
     * used to generate the detail message (which is saved for later retrieval
     * by the {@link #getMessage()} method).
     */
    public AnswerNotFoundException(String message) {
        super(String.format("Answer, %s, is not found:",  message));
    }
}
