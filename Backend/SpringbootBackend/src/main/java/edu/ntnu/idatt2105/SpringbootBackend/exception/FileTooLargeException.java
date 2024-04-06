package edu.ntnu.idatt2105.SpringbootBackend.exception;


/**
 * Exception thrown when an attempted file upload exceeds the maximum allowed size.
 * This can occur in various scenarios where file uploads are part of the application's functionality,
 * such as uploading images, documents, or other media types. The exception message typically
 * includes details about the maximum file size allowed and the actual size of the attempted upload.
 *
 * @author Vegard Johnsen
 *
 */
public class FileTooLargeException extends RuntimeException {

    /**
     * Constructs a new {@code FileTooLargeException} with the specified detail message.
     * The detail message explains the reason for the exception, such as the allowed maximum
     * file size and the size of the file that triggered this exception.
     *
     * @param message The detail message explaining why the exception was thrown.
     */
    public FileTooLargeException(String message) {
        super(message);
    }
    
}
