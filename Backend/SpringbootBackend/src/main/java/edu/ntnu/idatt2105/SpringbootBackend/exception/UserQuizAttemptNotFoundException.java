package edu.ntnu.idatt2105.SpringbootBackend.exception;

public class UserQuizAttemptNotFoundException extends RuntimeException {
    public UserQuizAttemptNotFoundException(String message) {
        super(String.format("UserQuizAttempt, %s, is not found:",  message));
    }
}
