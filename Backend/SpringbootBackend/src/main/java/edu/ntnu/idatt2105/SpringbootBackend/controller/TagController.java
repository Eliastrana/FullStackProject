package edu.ntnu.idatt2105.SpringbootBackend.controller;

import edu.ntnu.idatt2105.SpringbootBackend.dto.TagDTO;
import edu.ntnu.idatt2105.SpringbootBackend.model.Tag;
import edu.ntnu.idatt2105.SpringbootBackend.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * The {@code TagController} class provides REST APIs for managing tags within the system.
 * It offers functionality to create, retrieve, update, and delete tags, leveraging the {@link TagService}.
 * These APIs ensure that only authenticated users can perform write operations, enhancing security.
 *
 * @author Vegard Johnsen
 * @version 0.1
 * @since 0.1
 * @see Tag
 * @see TagDTO
 * @see TagService
 * @see ResponseEntity
 * @see HttpStatus
 */
@io.swagger.v3.oas.annotations.tags.Tag(name = "Tag Management")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagController {

  private final TagService tagService;

  /**
   * Creates a new tag in the system. This endpoint requires authentication and accepts
   * a {@link Tag} object in the request body.
   *
   * @param tag The {@link Tag} to be created.
   * @return A {@link ResponseEntity} with the created {@link Tag} and HTTP status {@link HttpStatus#CREATED}.
   */
  @Operation(summary = "Create a new tag", description = "Allows creation of a new tag", responses = {
    @ApiResponse(responseCode = "201", description = "Tag created successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid request")
  }, security = @SecurityRequirement(name = "bearerAuth"))
  @PostMapping("/")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
    Tag createdTag = tagService.createTag(tag);
    return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
  }

   /**
   * Retrieves all tags available in the system. This endpoint does not require any
   * specific identifier and returns a list of all tags.
   *
   * @return A {@link ResponseEntity} containing a list of {@link TagDTO} objects and HTTP status {@link HttpStatus#OK}.
   */
  @Operation(summary = "Get all tags", description = "Retrieves a list of all tags", responses = {
    @ApiResponse(responseCode = "200", description = "Fetched all tags successfully")
  })
  @GetMapping("/")
  public ResponseEntity<List<TagDTO>> getAllTags() {
    List<TagDTO> tags = tagService.getAllTags();
    return ResponseEntity.ok(tags);
  }

  /**
   * Retrieves a specific tag by its unique identifier. Utilizes the {@link TagService} to
   * search for the tag. If the tag is found, it is returned within a {@link ResponseEntity}; otherwise,
   * a NOT FOUND status is returned.
   *
   * @param id The unique identifier (UUID) of the tag to retrieve.
   * @return A {@link ResponseEntity} containing the found {@link Tag} if it exists, otherwise a NOT FOUND response.
   */
  @Operation(summary = "Get a tag by ID", description = "Retrieves a single tag by its unique identifier", responses = {
    @ApiResponse(responseCode = "200", description = "Tag fetched successfully"),
    @ApiResponse(responseCode = "404", description = "Tag not found")})
  @GetMapping("/{id}")
  public ResponseEntity<Tag> getTagById(@PathVariable UUID id) {
    Tag tag = tagService.getTagById(id);
    return ResponseEntity.ok(tag);
  }

  /**
   * Updates an existing tag identified by its UUID with new information provided in the request body.
   * This operation calls the {@link TagService} to apply the updates. If successful, the updated tag is returned;
   * if the tag is not found, a NOT FOUND response is generated.
   *
   * @param id         The UUID of the tag to be updated.
   * @param tagDetails A {@link Tag} object containing the updated tag details.
   * @return A {@link ResponseEntity} with the updated {@link Tag}, or a NOT FOUND status if the tag does not exist.
   */
  @Operation(summary = "Update a tag", description = "Updates an existing tag with new information", responses = {
    @ApiResponse(responseCode = "200", description = "Tag updated successfully"),
    @ApiResponse(responseCode = "404", description = "Tag not found"),
    @ApiResponse(responseCode = "400", description = "Invalid request")})
  @PutMapping("/{id}")
  public ResponseEntity<Tag> updateTag(@PathVariable UUID id, @RequestBody Tag tagDetails) {
    Tag updatedTag = tagService.updateTag(id, tagDetails);
    return ResponseEntity.ok(updatedTag);
  }

  /**
   * Deletes a tag from the system using its unique identifier. This method uses the {@link TagService}
   * to attempt deletion. If the operation is successful, a NO CONTENT response is returned; if the tag
   * cannot be found, a NOT FOUND response is provided.
   *
   * @param id The UUID of the tag to delete.
   * @return A {@link ResponseEntity<Void>} indicating the outcome of the delete operation.
   */
  @Operation(summary = "Delete a tag", description = "Deletes a tag by its ID", responses = {
    @ApiResponse(responseCode = "204", description = "Tag deleted successfully"),
    @ApiResponse(responseCode = "404", description = "Tag not found")})
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
    tagService.deleteTag(id);
    return ResponseEntity.noContent().build();
  }
}