package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.dto.QuizCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuizDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.QuizNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;
import edu.ntnu.idatt2105.SpringbootBackend.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Transactional
    public QuizDTO createQuiz(QuizCreateDTO quizCreateDTO) {
        // Convert QuizCreateDTO to Quiz entity
        Quiz quiz = new Quiz(); // Assuming you have a constructor or setters to set these fields
        quiz.setTitle(quizCreateDTO.getTitle());
        quiz.setDescription(quizCreateDTO.getDescription());
        // Set other necessary fields from quizCreateDTO to quiz
        
        // Save the quiz entity
        quiz = quizRepository.save(quiz);
        
        // Convert saved Quiz entity to QuizDTO and return
        return new QuizDTO(quiz.getId(), quiz.getTitle(), quiz.getDescription(), quiz.getCreator().getId());
    }

    public List<QuizDTO> getAllQuizzes() {
        // Fetch all quizzes and convert to QuizDTO
        return quizRepository.findAll().stream()
                .map(quiz -> new QuizDTO(quiz.getId(), quiz.getTitle(), quiz.getDescription(), quiz.getCreator().getId()))
                .collect(Collectors.toList());
    }

    public QuizDTO getQuizById(UUID id) throws QuizNotFoundException {
        // Fetch quiz by id, throw QuizNotFoundException if not found
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found with id: " + id));
        
        // Convert Quiz entity to QuizDTO and return
        return new QuizDTO(quiz.getId(), quiz.getTitle(), quiz.getDescription(), quiz.getCreator().getId());
    }

    @Transactional
    public QuizDTO updateQuiz(UUID id, QuizDTO quizUpdateDTO) throws QuizNotFoundException {
        // Fetch quiz by id, throw QuizNotFoundException if not found
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found with id: " + id));
        
        // Update the quiz entity from quizUpdateDTO
        quiz.setTitle(quizUpdateDTO.getTitle());
        quiz.setDescription(quizUpdateDTO.getDescription());
        // Update other necessary fields
        
        // Save the updated quiz
        quiz = quizRepository.save(quiz);
        
        // Convert updated Quiz entity to QuizDTO and return
        return new QuizDTO(quiz.getId(), quiz.getTitle(), quiz.getDescription(), quiz.getCreator().getId());
    }

    public void deleteQuiz(UUID id) throws QuizNotFoundException {
        // Check if the quiz exists, throw QuizNotFoundException if not
        if (!quizRepository.existsById(id)) {
            throw new QuizNotFoundException("Quiz not found with id: " + id);
        }
        
        // Delete the quiz by id
        quizRepository.deleteById(id);
    }
}
