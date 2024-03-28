package edu.ntnu.idatt2105.SpringbootBackend.exception;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(String message) {
        super(String.format("Question not found: %s", message));
    }
    
}
