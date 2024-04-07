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

@io.swagger.v3.oas.annotations.tags.Tag(name = "Tag Management")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagController {

  private final TagService tagService;

  @Operation(summary = "Create a new tag", description = "Allows creation of a new tag", responses = {
    @ApiResponse(responseCode = "201", description = "Tag created successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid request")
  }, security = @SecurityRequirement(name = "bearerAuth"))
  @PostMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
    Tag createdTag = tagService.createTag(tag);
    return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
  }

  @Operation(summary = "Get all tags", description = "Retrieves a list of all tags", responses = {
    @ApiResponse(responseCode = "200", description = "Fetched all tags successfully")
  })
  @GetMapping
  public ResponseEntity<List<TagDTO>> getAllTags() {
    List<TagDTO> tags = tagService.getAllTags();
    return ResponseEntity.ok(tags);
  }

  @Operation(summary = "Get a tag by ID", description = "Retrieves a single tag by its unique identifier", responses = {
    @ApiResponse(responseCode = "200", description = "Tag fetched successfully"),
    @ApiResponse(responseCode = "404", description = "Tag not found")})
  @GetMapping("/{id}")
  public ResponseEntity<Tag> getTagById(@PathVariable UUID id) {
    Tag tag = tagService.getTagById(id);
    return ResponseEntity.ok(tag);
  }

  @Operation(summary = "Update a tag", description = "Updates an existing tag with new information", responses = {
    @ApiResponse(responseCode = "200", description = "Tag updated successfully"),
    @ApiResponse(responseCode = "404", description = "Tag not found"),
    @ApiResponse(responseCode = "400", description = "Invalid request")})
  @PutMapping("/{id}")
  public ResponseEntity<Tag> updateTag(@PathVariable UUID id, @RequestBody Tag tagDetails) {
    Tag updatedTag = tagService.updateTag(id, tagDetails);
    return ResponseEntity.ok(updatedTag);
  }

  @Operation(summary = "Delete a tag", description = "Deletes a tag by its ID", responses = {
    @ApiResponse(responseCode = "204", description = "Tag deleted successfully"),
    @ApiResponse(responseCode = "404", description = "Tag not found")})
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
    tagService.deleteTag(id);
    return ResponseEntity.noContent().build();
  }
}