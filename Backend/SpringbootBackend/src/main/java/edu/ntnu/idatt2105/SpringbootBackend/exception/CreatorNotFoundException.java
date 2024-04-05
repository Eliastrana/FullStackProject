package edu.ntnu.idatt2105.SpringbootBackend.exception;


public class CreatorNotFoundException extends RuntimeException {
    public CreatorNotFoundException(String string) {
        super(String.format("Creator: %s not found.", string));
    }
    
}
