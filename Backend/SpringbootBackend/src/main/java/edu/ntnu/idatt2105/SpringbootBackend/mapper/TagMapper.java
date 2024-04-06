package edu.ntnu.idatt2105.SpringbootBackend.mapper;

import edu.ntnu.idatt2105.SpringbootBackend.dto.TagDTO;
import edu.ntnu.idatt2105.SpringbootBackend.model.Tag;
import edu.ntnu.idatt2105.SpringbootBackend.model.Question;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * The {@code TagMapper} class facilitates the mapping between {@link Tag} entities
 * and their corresponding {@link TagDTO} Data Transfer Objects. It provides functionality
 * to convert entities to DTOs and vice versa, allowing for easy data transfer
 * and manipulation between different layers of the application.
 *
 * @author sander rom skofsrud
 * @version 1.0
 * @since 1.0
 * @see Tag
 * @see TagDTO
 */
@Component
public class TagMapper {

  /**
   * Converts a {@link Tag} entity to a {@link TagDTO}.
   * This method maps the tag's id, name, and associated question ids to the DTO.
   *
   * @param tag the {@link Tag} entity to be converted
   * @return a {@link TagDTO} containing the tag's data
   */
  public TagDTO toTagDTO(Tag tag) {
    TagDTO dto = new TagDTO();
    dto.setId(tag.getId());
    dto.setName(tag.getName());
    dto.setQuestionIds(tag.getQuestions().stream().map(Question::getId).collect(Collectors.toSet()));
    return dto;
  }

  /**
   * Converts a {@link TagDTO} to a {@link Tag} entity.
   * This method is used when a new tag needs to be created or updated from the DTO data.
   *
   * @param dto the {@link TagDTO} containing the tag's data
   * @return a {@link Tag} entity based on the DTO's data
   */
  public Tag toEntity(TagDTO dto) {
    Tag tag = new Tag();
    tag.setId(dto.getId());
    tag.setName(dto.getName());
    tag.setQuestions(dto.getQuestionIds().stream().map(id -> {
      Question question = new Question();
      question.setId(id);
      return question;
    }).collect(Collectors.toSet()));
    return tag;
  }

  /**
   * Converts a set of {@link Tag} entities to a set of {@link TagDTO}.
   * This method is useful for converting collections of tags, such as when fetching all tags.
   *
   * @param tags a set of {@link Tag} entities
   * @return a set of {@link TagDTO} corresponding to the input tags
   */
  public Set<TagDTO> toDtoSet(Set<Tag> tags) {
    return tags.stream().map(this::toTagDTO).collect(Collectors.toSet());
  }


  /**
   * Converts a set of {@link TagDTO} to a set of {@link Tag} entities.
   * This method allows for batch conversion of tag DTOs to entities, useful for updating or creating multiple tags.
   *
   * @param dtos a set of {@link TagDTO}
   * @return a set of {@link Tag} entities based on the DTOs
   */
  public Set<Tag> toEntitySet(Set<TagDTO> dtos) {
    return dtos.stream().map(this::toEntity).collect(Collectors.toSet());
  }
}
