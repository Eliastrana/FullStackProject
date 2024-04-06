package edu.ntnu.idatt2105.SpringbootBackend.exception;

/**
 * Exception thrown when an error occurs during the creation of an answer.
 * This could be due to various reasons such as invalid input data or database errors.
 *
 * @author Vegard Johnsen
 * @version 0.1
 * @since 0.1
 *
 * @see edu.ntnu.idatt2105.SpringbootBackend.service.AnswerService
 */
public class AnswerCreationException extends RuntimeException {
    /**
     * Constructs a new {@code AnswerCreationException} with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval
     * by the {@link #getMessage()} method)
     */
    public AnswerCreationException(String message) {
        super(message);
    }
}
