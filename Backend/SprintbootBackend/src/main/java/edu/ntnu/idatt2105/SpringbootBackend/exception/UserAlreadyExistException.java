package edu.ntnu.idatt2105.SpringbootBackend.exception;

/**
 * Exception thrown when a user already exists in the database.
 */

public class UserAlreadyExistException extends IllegalArgumentException {
    public UserAlreadyExistException(String username) {
        super(String.format("User, %s, already exists", username));
    }
}
