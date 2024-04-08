package edu.ntnu.idatt2105.SpringbootBackend.exception;

public class PasswordDoesNotMeetRequirements extends RuntimeException {
    public PasswordDoesNotMeetRequirements(String message) {
        super(String.format("Password does not meet requirements: %s", message));
    }
}
