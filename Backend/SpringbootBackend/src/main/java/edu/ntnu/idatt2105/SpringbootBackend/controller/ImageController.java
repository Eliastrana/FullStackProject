package edu.ntnu.idatt2105.SpringbootBackend.controller;

import edu.ntnu.idatt2105.SpringbootBackend.model.Image;
import edu.ntnu.idatt2105.SpringbootBackend.service.ImageService;
import edu.ntnu.idatt2105.SpringbootBackend.service.QuizService;
import edu.ntnu.idatt2105.SpringbootBackend.service.QuestionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;

/**
 * The {@link ImageController} class manages the uploading and retrieval of images
 * associated with quizzes and questions. It provides endpoints to upload an image
 * for a specific quiz or question and to retrieve an image by its unique identifier.
 *
 * @author Vegard Johnsen, Sander R. Skofsrud
 * @version 1.0
 * @since 1.0
 * @see ImageService
 * @see ImageController
 * @see QuizService
 * @see QuestionService
 */
@Tag(name = "Image Management")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuestionService questionService;

    private final Logger logger = LoggerFactory.getLogger(ImageController.class);

    /**
     * Uploads an image file and associates it with a specific quiz identified by its UUID.
     * The method stores the image using {@link ImageService} and updates the quiz's image reference
     * using {@link QuizService}.
     *
     * @param quizId The unique identifier (UUID) of the quiz to associate with the image.
     * @param file The uploaded image file.
     * @return A {@link ResponseEntity} with a success message if the image is uploaded and associated successfully,
     * or an error message and {@link HttpStatus#INTERNAL_SERVER_ERROR} if the process fails.
     */
    @Operation(summary = "Upload an image for a quiz", description = "Uploads an image to associate with a quiz")
    @ApiResponse(responseCode = "200", description = "Image uploaded successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping("/quizzes/{quizId}/image")
    public ResponseEntity<?> uploadImage(
            @PathVariable UUID quizId,
            @RequestParam("image") MultipartFile file) {
                try {
                    Image image = imageService.storeImage(file);
                    quizService.setImageForQuiz(quizId, image);
                    logger.info("Image uploaded successfully: {}", file.getOriginalFilename());

                    return ResponseEntity.ok().body("Image uploaded successfully: " + file.getOriginalFilename());
                } catch (Exception e) {
                    logger.error("Failed to upload image: {}", e.getMessage(), e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Could not upload the file: " + file.getOriginalFilename() + "!");
                }
            }

    /**
     * Retrieves an image by its unique identifier (UUID) and returns the image content
     * along with the correct content type. If the image is not found, a {@link HttpStatus#NOT_FOUND}
     * response is returned.
     *
     * @param imageId The unique identifier (UUID) of the image to retrieve.
     * @return A {@link ResponseEntity} containing the image data and content type if the image is found,
     * or a {@link HttpStatus#NOT_FOUND} response if the image does not exist.
     */
    @Operation(summary = "Get an image by ID", description = "Retrieves an image by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Successfully fetched the image")
    @ApiResponse(responseCode = "404", description = "Image not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/images/{imageId}")
    public ResponseEntity<byte[]> getImage(
            @PathVariable UUID imageId) {
        try {
            logger.info("Fetching image with ID: {}", imageId);
            Image image = imageService.getImage(imageId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found"));

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(image.getFileType()))
                    .body(image.getData());
        } catch (Exception e) {
            logger.error("Failed to fetch image: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Uploads an image and associates it with a question identified by its UUID.
     * This method allows for enhancing questions with visual content.
     *
     * @param questionId The UUID of the question to which the image is to be associated.
     * @param file       The {@link MultipartFile} representing the image to upload.
     * @return A {@link ResponseEntity} with an OK status and message indicating
     * successful upload, or INTERNAL_SERVER_ERROR if the upload fails.
     */
    @Operation(summary = "Upload an image for a question", description = "Uploads an image to associate with a question")
    @ApiResponse(responseCode = "200", description = "Image uploaded successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping("/questions/{questionId}/image")
    public ResponseEntity<?> uploadImageForQuestion(
            @PathVariable UUID questionId,
            @RequestParam("image") MultipartFile file) {
        try {
            Image image = imageService.storeImage(file);
            questionService.setImageForQuestion(questionId, image);
            logger.info("Image uploaded successfully: {}", file.getOriginalFilename());

            return ResponseEntity.ok().body("Image uploaded successfully: " + file.getOriginalFilename());
        } catch (Exception e) {
            logger.error("Failed to upload image: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not upload the file: " + file.getOriginalFilename() + "!");
        }
    }

}
