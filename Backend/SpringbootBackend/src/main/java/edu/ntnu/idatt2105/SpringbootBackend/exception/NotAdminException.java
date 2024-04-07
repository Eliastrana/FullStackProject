package edu.ntnu.idatt2105.SpringbootBackend.exception;

public class NotAdminException extends RuntimeException {
    public NotAdminException(String username) {
        super(String.format("User is not an owner: %s", username));
    }
}
