package edu.ntnu.idatt2105.SpringbootBackend.exception;

/**
 * Exception indicating that a specified quiz could not be found.
 * This exception is thrown in scenarios where an operation is requested on a quiz
 * that does not exist in the system, identified by a specific criterion such as a UUID or a name.
 *
 * @author Vegard Johnsen
 * @version 0.1
 * @since 0.1
 * @see edu.ntnu.idatt2105.SpringbootBackend.service.QuizService
 * @see edu.ntnu.idatt2105.SpringbootBackend.controller.QuizController
 */
public class QuizNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code QuizNotFoundException} with a message specifying the detail
     * of the quiz that was not found.
     *
     * @param message The detail of the quiz that was not found, aiding in identifying the missing entity.
     */
    public QuizNotFoundException(String message) {
        super(String.format("Quiz, %s, is not found:",  message));
    }
}
