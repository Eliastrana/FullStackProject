package edu.ntnu.idatt2105.SpringbootBackend.exception;

public class QuizNotFoundException extends RuntimeException {
    public QuizNotFoundException(String message) {
        super(String.format("Quiz, %s, is not found:",  message));
    }
}
