package edu.ntnu.idatt2105.SpringbootBackend.exception;

public class AnswerNotFoundException extends RuntimeException {
    public AnswerNotFoundException(String message) {
        super(String.format("Answer, %s, is not found:",  message));
    }
}
