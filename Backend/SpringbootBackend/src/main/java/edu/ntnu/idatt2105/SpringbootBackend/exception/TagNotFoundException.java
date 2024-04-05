package edu.ntnu.idatt2105.SpringbootBackend.exception;

import java.util.UUID;

public class TagNotFoundException extends RuntimeException {
  public TagNotFoundException(String name) {
    super("Tag not found with name: " + name);
  }

  public TagNotFoundException(UUID id) {
    super("Tag not found with id: " + id);
  }
}
