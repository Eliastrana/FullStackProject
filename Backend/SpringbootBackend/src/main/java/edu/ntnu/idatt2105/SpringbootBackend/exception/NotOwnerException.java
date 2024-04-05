package edu.ntnu.idatt2105.SpringbootBackend.exception;

public class NotOwnerException extends RuntimeException {
    public NotOwnerException(String username) {
        super(String.format("User is not an owner: %s", username));
    }
}
