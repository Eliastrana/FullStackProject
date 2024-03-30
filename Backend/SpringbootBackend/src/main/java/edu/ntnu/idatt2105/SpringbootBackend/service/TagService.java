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

@Service
public class TagService {

  private final TagRepository tagRepository;
  private final TagMapper tagMapper;

  @Autowired
  public TagService(TagRepository tagRepository, TagMapper tagMapper) {
    this.tagRepository = tagRepository;
    this.tagMapper = tagMapper;
  }

  public Tag createTag(Tag tag) {
    Optional<Tag> existingTag = tagRepository.findByName(tag.getName());
    if (existingTag.isPresent()) {
      throw new TagAlreadyExistsException("Tag with name " + tag.getName() + " already exists.");
    }
    return tagRepository.save(tag);
  }

  public List<TagDTO> getAllTags() {
    return tagRepository.findAll().stream()
            .map(tagMapper::toTagDTO)
            .collect(Collectors.toList());
  }

  public Tag getTagById(UUID id) {
    return tagRepository.findById(id).orElseThrow(() -> new TagNotFoundException(id));
  }

  public Tag updateTag(UUID id, Tag tagDetails) {
    Tag tag = tagRepository.findById(id).orElseThrow(() -> new TagNotFoundException(id));
    tag.setName(tagDetails.getName());
    // Perform any other updates here
    return tagRepository.save(tag);
  }

  public void deleteTag(UUID id) {
    Tag tag = tagRepository.findById(id).orElseThrow(() -> new TagNotFoundException(id));
    tagRepository.delete(tag);
  }

  public Optional<Tag> findTagByName(String name) {
    return tagRepository.findByName(name);
  }

// Any additional business logic methods related to tags could go here, for example:
  // Method to associate a tag with a question
  // Method to search tags by name
}