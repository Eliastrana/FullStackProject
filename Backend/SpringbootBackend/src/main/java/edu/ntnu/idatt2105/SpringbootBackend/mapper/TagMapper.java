package edu.ntnu.idatt2105.SpringbootBackend.mapper;

import edu.ntnu.idatt2105.SpringbootBackend.dto.TagDTO;
import edu.ntnu.idatt2105.SpringbootBackend.model.Tag;
import edu.ntnu.idatt2105.SpringbootBackend.model.Question;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TagMapper {

  public TagDTO toTagDTO(Tag tag) {
    TagDTO dto = new TagDTO();
    dto.setId(tag.getId());
    dto.setName(tag.getName());
    dto.setQuestionIds(tag.getQuestions().stream().map(Question::getId).collect(Collectors.toSet()));
    return dto;
  }

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

  public Set<TagDTO> toDtoSet(Set<Tag> tags) {
    return tags.stream().map(this::toTagDTO).collect(Collectors.toSet());
  }

  public Set<Tag> toEntitySet(Set<TagDTO> dtos) {
    return dtos.stream().map(this::toEntity).collect(Collectors.toSet());
  }
}
