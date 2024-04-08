package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.dto.AnswerDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.AnswerNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.QuestionNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.model.Answer;
import edu.ntnu.idatt2105.SpringbootBackend.model.Question;
import edu.ntnu.idatt2105.SpringbootBackend.repository.AnswerRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * The {@code AnswerService} class handles the business logic for answer operations. It provides
 * methods to create, retrieve, update, and delete answers within the system. This service interacts
 * with the {@link AnswerRepository} and {@link QuestionRepository} to perform CRUD operations
 * on answers and to ensure the integrity of the relationship between answers and questions.
 *
 * @author : Vegard Johnsen
 * @version 0.1
 * @since 0.1
 * @see Answer
 * @see Question
 * @see AnswerRepository
 * @see QuestionRepository
 */

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    /**
     * Creates a new answer and associates it with a question identified by {@code questionId}.
     * This method ensures the question exists before proceeding to create an answer.
     *
     * @param questionId The UUID of the question to which the answer will be associated.
     * @param answerDTO  The data transfer object containing the answer details.
     * @return An {@link AnswerDTO} representing the newly created answer.
     * @throws QuestionNotFoundException If the question associated with {@code questionId} does not exist.
     */
    @Transactional
    public AnswerDTO createAnswer(UUID questionId, AnswerDTO answerDTO) throws QuestionNotFoundException {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(questionId));

        Answer answer = new Answer();
        answer.setText(answerDTO.getText());
        answer.setCorrect(answerDTO.isCorrect());
        answer.setQuestion(question);

        answer = answerRepository.save(answer);

        return new AnswerDTO(answer.getId(), answer.getText(), answer.isCorrect());
    }

    /**
     * Retrieves an answer by its UUID.
     *
     * @param answerId The UUID of the answer to retrieve.
     * @return An {@link AnswerDTO} representing the requested answer.
     * @throws AnswerNotFoundException If no answer with the given {@code answerId} exists.
     */
    public AnswerDTO getAnswerById(UUID answerId) throws AnswerNotFoundException {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found with id: " + answerId));

        return new AnswerDTO(answer.getId(), answer.getText(), answer.isCorrect());
    }

    /**
     * Updates an existing answer identified by {@code answerId} with new details provided in {@code answerDTO}.
     *
     * @param answerId  The UUID of the answer to update.
     * @param answerDTO The data transfer object containing the updated answer details.
     * @return An {@link AnswerDTO} representing the updated answer.
     * @throws AnswerNotFoundException If no answer with the given {@code answerId} exists.
     */
    @Transactional
    public AnswerDTO updateAnswer(UUID answerId, AnswerDTO answerDTO) throws AnswerNotFoundException {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found with id: " + answerId));

        answer.setText(answerDTO.getText());
        answer.setCorrect(answerDTO.isCorrect());

        answer = answerRepository.save(answer);

        return new AnswerDTO(answer.getId(), answer.getText(), answer.isCorrect());
    }

    /**
     * Deletes an answer identified by {@code answerId}.
     *
     * @param answerId The UUID of the answer to delete.
     * @throws AnswerNotFoundException If no answer with the given {@code answerId} exists.
     */
    @Transactional
    public void deleteAnswer(UUID answerId) throws AnswerNotFoundException {
        if (!answerRepository.existsById(answerId)) {
            throw new AnswerNotFoundException("Answer not found with id: " + answerId);
        }
        answerRepository.deleteById(answerId);
    }
}
