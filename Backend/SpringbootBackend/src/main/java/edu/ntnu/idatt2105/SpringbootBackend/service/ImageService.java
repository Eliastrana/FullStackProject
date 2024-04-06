package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.exception.FileTooLargeException;
import edu.ntnu.idatt2105.SpringbootBackend.model.Image;
import edu.ntnu.idatt2105.SpringbootBackend.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

/**
 * The {@code ImageService} class provides functionalities related to handling images
 * within the system. It includes operations such as storing images and retrieving them by ID.
 * It uses the {@link ImageRepository} for persistence operations and ensures file size restrictions
 * are enforced when storing images.
 *
 * @author Vegard Johnsen
 * @version 0.1
 * @since 0.1
 * @see Image for the entity this service handles.
 * @see ImageRepository for the persistence layer this service interacts with.
 */
@Service
public class ImageService {

    private static  final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    @Autowired
    private ImageRepository imageRepository;

    /**
     * Stores an image in the repository. The image is built from the provided {@link MultipartFile}
     * object. If the file size exceeds the maximum allowed size, a {@link FileTooLargeException} is thrown.
     *
     * @param file The {@link MultipartFile} containing the image to be stored.
     * @return The saved {@link Image} entity.
     * @throws IOException If an I/O error occurs reading the file content.
     * @throws FileTooLargeException If the file size exceeds the maximum allowed limit.
     */
    public Image storeImage(MultipartFile file) throws IOException {

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new FileTooLargeException("File size exceeds the limit of 10MB: " + file.getSize() + " bytes");
        }
        String fileName = file.getOriginalFilename();
        Image image = Image.builder()
                .filename(fileName)
                .fileType(file.getContentType())
                .size(file.getSize())
                .data(file.getBytes())
                .build();
        return imageRepository.save(image);
    }

    /**
     * Retrieves an {@link Image} entity by its ID.
     *
     * @param id The unique identifier of the image to retrieve.
     * @return An {@link Optional<Image>} containing the image if found, or an empty Optional otherwise.
     */
    public Optional<Image> getImage(UUID id) {
        return imageRepository.findById(id);
    }
}
