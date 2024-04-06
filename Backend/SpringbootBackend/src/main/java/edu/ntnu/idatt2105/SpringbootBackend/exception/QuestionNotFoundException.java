package edu.ntnu.idatt2105.SpringbootBackend.exception;

import java.util.UUID;

/**
 * Exception indicating that a specified question could not be found.
 * This exception is thrown in scenarios where an operation is requested on a question
 * that does not exist in the system, identified by either a UUID or a specific criterion.
 *
 * @author Vegard Johnsen
 *
 */
public class QuestionNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code QuestionNotFoundException} with a message containing the UUID of the question.
     *
     * @param message The UUID of the question that was not found.
     */
    public QuestionNotFoundException(UUID message) {
        super(String.format("Question, %s, is not found:",  message));
    }

    /**
     * Constructs a new {@code QuestionNotFoundException} with a message containing a descriptive detail
     * about the question that was not found.
     *
     * @param message A descriptive detail about the question that was not found.
     */
    public QuestionNotFoundException(String message) {
        super(String.format("Question, %s, is not found:",  message));
    }
    
}
