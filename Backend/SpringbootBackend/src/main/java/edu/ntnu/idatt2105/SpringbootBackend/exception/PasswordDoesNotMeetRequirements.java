package edu.ntnu.idatt2105.SpringbootBackend.exception;

public class PasswordDoesNotMeetRequirements extends RuntimeException {
    public PasswordDoesNotMeetRequirements(String message) {
        super(String.format(message, "Password does not meet password requirements"));
    }
}
