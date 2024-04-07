package edu.ntnu.idatt2105.SpringbootBackend.exception;

public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException(String message) {
        super(message);
    }
}
