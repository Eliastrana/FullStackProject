package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.dto.QuestionCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuestionDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.QuestionNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.QuizNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.mapper.QuestionMapper;
import edu.ntnu.idatt2105.SpringbootBackend.model.Question;
import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;
import edu.ntnu.idatt2105.SpringbootBackend.repository.QuestionRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionMapper questionMapper;

    @Transactional
    public QuestionDTO createQuestion(UUID quizId, QuestionCreateDTO questionCreateDTO) throws QuizNotFoundException {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found with id: " + quizId));
        
        Question question = questionMapper.toQuestion(questionCreateDTO, quiz);
        question = questionRepository.save(question);
        
        return questionMapper.toQuestionDTO(question);
    }

    public QuestionDTO getQuestionById(UUID questionId) throws QuestionNotFoundException {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found with id: " + questionId));
        
        return questionMapper.toQuestionDTO(question);
    }

    @Transactional
    public QuestionDTO updateQuestion(UUID questionId, QuestionDTO questionDTO) throws QuestionNotFoundException {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found with id: " + questionId));
        
        // Assuming you have a method in QuestionMapper to update a Question entity from QuestionDTO
        question = questionMapper.updateQuestionFromDTO(questionDTO, question);
        question = questionRepository.save(question);
        
        return questionMapper.toQuestionDTO(question);
    }

    @Transactional
    public void deleteQuestion(UUID questionId) throws QuestionNotFoundException {
        if (!questionRepository.existsById(questionId)) {
            throw new QuestionNotFoundException("Question not found with id: " + questionId);
        }
        
        questionRepository.deleteById(questionId);
    }

    public List<QuestionDTO> getQuestionsByQuizId(UUID quizId) {
        List<Question> questions = questionRepository.findAllByQuizId(quizId);
        return questions.stream().map(questionMapper::toQuestionDTO).collect(Collectors.toList());
    }
}
