package edu.ntnu.idatt2105.SpringbootBackend.exception;

/**
 * Exception indicating that a specific user quiz attempt could not be found.
 * This exception is thrown when an operation attempts to access or modify a user quiz attempt
 * by its identifier or other distinguishing details, but no such attempt exists in the system.
 *
 * @author Vegard Johnsen
 * @version 0.1
 * @since 0.1
 */

public class UserQuizAttemptNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code UserQuizAttemptNotFoundException} with a detailed message
     * indicating the reason for the exception, typically including the identifier or criteria
     * used in the failed search for the user quiz attempt.
     *
     * @param message the detail message that explains why the exception was thrown, often including
     * the specific identifier or search criteria for the user quiz attempt that was not found.
     */
    public UserQuizAttemptNotFoundException(String message) {
        super(String.format("UserQuizAttempt, %s, is not found:",  message));
    }
}
