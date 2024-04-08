package edu.ntnu.idatt2105.SpringbootBackend.mapper;

import edu.ntnu.idatt2105.SpringbootBackend.dto.AnswerCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.AnswerDTO;
import edu.ntnu.idatt2105.SpringbootBackend.model.Answer;
import edu.ntnu.idatt2105.SpringbootBackend.model.Question;
import org.springframework.stereotype.Component;


/**
 * The {@code AnswerMapper} class is responsible for mapping between {@link Answer} entities and
 * {@link AnswerDTO} objects. This includes converting from entity to DTO and vice versa, which
 * facilitates the transfer of data between the service layer and the client.
 * This component plays a crucial role in abstracting the domain model and its persistence aspects
 * from the data transfer objects used in client-server communication.
 *
 * @author Vegard Johnsen
 * @version 1.0
 * @since 1.0
 * @see Answer
 * @see AnswerDTO
 * @see AnswerCreateDTO
 */
@Component
public class AnswerMapper {

    /**
     * Converts an {@link Answer} entity to a {@link AnswerDTO}.
     * This method maps the id, text, and correctness status from an {@link Answer} entity
     * to an {@link AnswerDTO}. If the {@code answer} parameter is null, the method returns null.
     *
     * @param answer the {@link Answer} entity to convert
     * @return the converted {@link AnswerDTO}, or null if the input is null
     */
    public AnswerDTO toDTO(Answer answer) {
        if (answer == null) {
            return null;
        }

        AnswerDTO dto = new AnswerDTO();
        dto.setId(answer.getId());
        dto.setText(answer.getText());
        dto.setCorrect(answer.isCorrect());
        // Assuming AnswerDTO has a field for questionId if needed
        // dto.setQuestionId(answer.getQuestion().getId());

        return dto;
    }

    /**
     * Converts an {@link AnswerCreateDTO} to an {@link Answer} entity.
     * This method creates a new {@link Answer} entity and sets its text and correctness status based on
     * an {@link AnswerCreateDTO}. It associates the new answer with a given {@link Question}.
     * If the {@code dto} parameter is null, the method returns null.
     *
     * @param dto      the {@link AnswerCreateDTO} to convert
     * @param question the {@link Question} to associate with the answer
     * @return the converted {@link Answer}, or null if the input DTO is null
     */
    public Answer toEntity(AnswerCreateDTO dto, Question question) {
        if (dto == null) {
            return null;
        }

        Answer answer = new Answer();
        answer.setText(dto.getText());
        answer.setCorrect(dto.isCorrect());
        answer.setQuestion(question); // Set the question this answer belongs to

        return answer;
    }

    /**
     * Updates an existing {@link Answer} entity from a {@link AnswerDTO}.
     * This method updates the text and correctness status of an existing {@link Answer} entity
     * based on the values provided in an {@link AnswerDTO}. If either parameter is null, the method
     * returns null.
     *
     * @param dto            the {@link AnswerDTO} containing the new values
     * @param existingAnswer the existing {@link Answer} to update
     * @return the updated {@link Answer}, or null if either parameter is null
     */
    public Answer updateEntityFromDTO(AnswerDTO dto, Answer existingAnswer) {
        if (dto == null || existingAnswer == null) {
            return null;
        }

        existingAnswer.setText(dto.getText());
        existingAnswer.setCorrect(dto.isCorrect());
        // Note: The question associated with the answer typically doesn't change in an update scenario

        return existingAnswer;
    }

    /**
     * Converts an {@link Answer} entity to a {@link AnswerDTO}.
     * This method is similar to {@link #toDTO(Answer)} but could be used for alternative
     * mapping strategies or additional processing.
     * If the {@code answer} parameter is null, the method returns null.
     *
     * @param answer the {@link Answer} entity to convert
     * @return the converted {@link AnswerDTO}, or null if the input is null
     */

    public AnswerDTO toAnswerDTO(Answer answer) {
        if (answer == null) {
            return null;
        }

        AnswerDTO dto = new AnswerDTO();
        dto.setId(answer.getId());
        dto.setText(answer.getText());
        dto.setCorrect(answer.isCorrect());
        return dto;
    }
}
