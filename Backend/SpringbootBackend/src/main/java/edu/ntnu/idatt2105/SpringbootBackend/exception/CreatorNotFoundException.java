package edu.ntnu.idatt2105.SpringbootBackend.exception;

import java.util.UUID;

public class CreatorNotFoundException extends RuntimeException {
    public CreatorNotFoundException(UUID creator) {
        super(String.format("Creator: %s not found.", creator));
    }
    
}
