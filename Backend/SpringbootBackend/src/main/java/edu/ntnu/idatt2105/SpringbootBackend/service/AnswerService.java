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

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

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

    public AnswerDTO getAnswerById(UUID answerId) throws AnswerNotFoundException {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found with id: " + answerId));

        return new AnswerDTO(answer.getId(), answer.getText(), answer.isCorrect());
    }

    @Transactional
    public AnswerDTO updateAnswer(UUID answerId, AnswerDTO answerDTO) throws AnswerNotFoundException {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found with id: " + answerId));

        answer.setText(answerDTO.getText());
        answer.setCorrect(answerDTO.isCorrect());

        answer = answerRepository.save(answer);

        return new AnswerDTO(answer.getId(), answer.getText(), answer.isCorrect());
    }

    @Transactional
    public void deleteAnswer(UUID answerId) throws AnswerNotFoundException {
        if (!answerRepository.existsById(answerId)) {
            throw new AnswerNotFoundException("Answer not found with id: " + answerId);
        }
        answerRepository.deleteById(answerId);
    }
}
