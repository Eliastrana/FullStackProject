package edu.ntnu.idatt2105.SpringbootBackend.exception;

public class CategoryAlreadyExistsException extends RuntimeException {
  public CategoryAlreadyExistsException(String categoryName) {
    super("Category already exists with name: " + categoryName);
  }
}