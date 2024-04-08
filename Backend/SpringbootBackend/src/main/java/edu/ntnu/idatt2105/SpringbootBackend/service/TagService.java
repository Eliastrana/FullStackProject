package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.dto.TagDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.TagNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.TagAlreadyExistsException;
import edu.ntnu.idatt2105.SpringbootBackend.mapper.TagMapper;
import edu.ntnu.idatt2105.SpringbootBackend.model.Tag;
import edu.ntnu.idatt2105.SpringbootBackend.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
/**
 * The {@code TagService} class manages the creation, retrieval, updating, and deletion of tags in the system.
 * Utilizes {@link TagRepository} for persistence operations and {@link TagMapper} for converting between
 * entity and DTO forms. This service enables managing tags that can be associated with various entities
 * such as questions within the application.
 *
 * @author sander rom skofsrud
 * @version 1.0
 * @since 1.0
 * @see Tag
 * @see TagDTO
 */

@Service
public class TagService {

  private final TagRepository tagRepository;
  private final TagMapper tagMapper;

  /**
   * Constructs a {@code TagService} with necessary repository and mapper components.
   *
   * @param tagRepository The repository component for tag data persistence.
   * @param tagMapper The mapper component for converting between tag entities and DTOs.
   */
  @Autowired
  public TagService(TagRepository tagRepository, TagMapper tagMapper) {
    this.tagRepository = tagRepository;
    this.tagMapper = tagMapper;
  }

  /**
   * Creates and saves a new tag to the system. If a tag with the same name already exists,
   * throws a {@link TagAlreadyExistsException}.
   *
   * @param tag The {@link Tag} object to be created.
   * @return The saved {@link Tag} object.
   * @throws TagAlreadyExistsException if a tag with the same name already exists.
   */
  public Tag createTag(Tag tag) {
    Optional<Tag> existingTag = tagRepository.findByName(tag.getName());
    if (existingTag.isPresent()) {
      throw new TagAlreadyExistsException("Tag with name " + tag.getName() + " already exists.");
    }
    return tagRepository.save(tag);
  }

  /**
   * Retrieves all tags present in the system.
   *
   * @return A list of {@link TagDTO} representing all tags.
   */
  public List<TagDTO> getAllTags() {
    return tagRepository.findAll().stream()
            .map(tagMapper::toTagDTO)
            .collect(Collectors.toList());
  }

  /**
   * Retrieves a tag by its unique identifier.
   *
   * @param id The unique identifier of the tag.
   * @return The {@link Tag} object if found.
   * @throws TagNotFoundException if the tag is not found.
   */
  public Tag getTagById(UUID id) {
    return tagRepository.findById(id).orElseThrow(() -> new TagNotFoundException(id));
  }

  /**
   * Updates an existing tag with new details.
   *
   * @param id The unique identifier of the tag to update.
   * @param tagDetails The new details for the tag.
   * @return The updated {@link Tag} object.
   * @throws TagNotFoundException if the tag to be updated is not found.
   */
  public Tag updateTag(UUID id, Tag tagDetails) {
    Tag tag = tagRepository.findById(id).orElseThrow(() -> new TagNotFoundException(id));
    tag.setName(tagDetails.getName());
    // Perform any other updates here
    return tagRepository.save(tag);
  }

  /**
   * Deletes a tag by its unique identifier.
   *
   * @param id The unique identifier of the tag to delete.
   * @throws TagNotFoundException if the tag to be deleted is not found.
   */
  public void deleteTag(UUID id) {
    Tag tag = tagRepository.findById(id).orElseThrow(() -> new TagNotFoundException(id));
    tagRepository.delete(tag);
  }

  /**
   * Finds a tag by its name.
   *
   * @param name The name of the tag to find.
   * @return An {@link Optional<Tag>} containing the tag if found.
   */
  public Optional<Tag> findTagByName(String name) {
    return tagRepository.findByName(name);
  }

// Any additional business logic methods related to tags could go here, for example:
  // Method to associate a tag with a question
  // Method to search tags by name
}
