package edu.ntnu.idatt2105.SpringbootBackend.exception;

/**
 * Exception thrown when a user is not found in the database.
 */

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super(String.format("User, %s, not found", username));
    }
}
