package edu.ntnu.idatt2105.SpringbootBackend.exception;

public class FileTooLargeException extends RuntimeException {
    public FileTooLargeException(String message) {
        super(message);
    }
    
}
