package edu.ntnu.idatt2105.SpringbootBackend.exception;

import java.util.UUID;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(UUID message) {
        super(String.format("Question, %s, is not found:",  message));
    }
    
}
