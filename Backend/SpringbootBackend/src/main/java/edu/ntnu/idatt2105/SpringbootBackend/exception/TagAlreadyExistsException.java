package edu.ntnu.idatt2105.SpringbootBackend.exception;

public class TagAlreadyExistsException extends RuntimeException {
  public TagAlreadyExistsException(String message) {
    super(message);
  }
}
