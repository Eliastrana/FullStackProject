package edu.ntnu.idatt2105.SpringbootBackend.exception;

public class UserAlreadyExistException extends IllegalArgumentException {
    public UserAlreadyExistException(String username) {
        super(String.format("User, %s, already exists", username));
    }
}
