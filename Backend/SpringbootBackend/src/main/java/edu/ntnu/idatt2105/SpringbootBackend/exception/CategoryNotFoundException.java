package edu.ntnu.idatt2105.SpringbootBackend.exception;

import java.util.UUID;

public class CategoryNotFoundException extends RuntimeException {
  public CategoryNotFoundException(String name) {
    super("Category not found with id: " + name);
  }

  public CategoryNotFoundException(UUID id) {
    super("Category not found with id: " + id);
  }
}