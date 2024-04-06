package edu.ntnu.idatt2105.SpringbootBackend.exception;

/**
 * Exception indicating that an image could not be found. This exception is thrown
 * when an operation attempts to access an image that does not exist in the database
 * or the specified location. It typically occurs during retrieval operations where
 * an image's identifier does not match any existing images.
 *
 * @author Vegard Johnsen
 *
 */
public class ImageNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code ImageNotFoundException} with the specified detail message.
     * The detail message provides more information about the missing image, such as its
     * identifier or name.
     *
     * @param message The detail message explaining the reason the image could not be found.
     */
    public ImageNotFoundException(String message) {
        super(message);
    }
}
