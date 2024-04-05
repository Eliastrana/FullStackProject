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
