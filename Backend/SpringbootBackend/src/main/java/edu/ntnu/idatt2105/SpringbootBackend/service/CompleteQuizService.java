package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.dto.AnswerCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.AnswerDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.CompleteQuestionDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.CompleteQuizDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuestionCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuizCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.AnswerNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.QuizNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.TagNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.model.Answer;
import edu.ntnu.idatt2105.SpringbootBackend.model.Question;
import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;
import edu.ntnu.idatt2105.SpringbootBackend.model.Tag;
import edu.ntnu.idatt2105.SpringbootBackend.repository.QuizRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.CategoryRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.QuestionRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.TagRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.AnswerRepository;
import edu.ntnu.idatt2105.SpringbootBackend.mapper.TagMapper;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CompleteQuizService {

    private final QuizService quizService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final QuizRepository quizRepository;
    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final AnswerRepository answerRepository;

    @Autowired
    public CompleteQuizService(
        QuizService quizService, 
        QuestionService questionService, 
        AnswerService answerService, 
        QuizRepository quizRepository, 
        CategoryRepository categoryRepository, 
        QuestionRepository questionRepository,
        TagRepository tagRepository,
        TagMapper tagMapper,
        AnswerRepository answerRepository
        ) 
        {
        this.quizService = quizService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.quizRepository = quizRepository;
        this.categoryRepository = categoryRepository;
        this.questionRepository = questionRepository;
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
        this.answerRepository = answerRepository;
    }

    @Transactional
    public void createCompleteQuiz(CompleteQuizDTO completeQuizDTO) throws QuizNotFoundException {
        // Create the quiz first
        QuizCreateDTO quizCreateDTO = new QuizCreateDTO(
            completeQuizDTO.getTitle(),
            completeQuizDTO.getDescription(),
            completeQuizDTO.getCreatorId(),
            completeQuizDTO.getCategoryId(),
    null
);

        var quizDTO = quizService.createQuiz(quizCreateDTO);

        // For each question in the CompleteQuizDTO, create the question and its answers
        for (var question : completeQuizDTO.getQuestions()) {
            // Inside the loop for creating questions
            // Example conversion (assuming you have appropriate constructors/getters/setters)
        List<AnswerCreateDTO> answerCreateDTOs = question.getAnswers().stream()
        .map(answerDto -> new AnswerCreateDTO(answerDto.getText(), answerDto.isCorrect()))
        .collect(Collectors.toList());

        // Then use the converted list with the builder
    QuestionCreateDTO questionCreateDTO = QuestionCreateDTO.builder()
        .text(question.getText())
        .questionType(question.getQuestionType())
        .multimediaLink(question.getMultimediaLink())
        .tags(question.getTags()) // Assuming this is correctly set up
        .answers(answerCreateDTOs) // Use the converted list
        .build();

            var createdQuestionDTO = questionService.createQuestion(quizDTO.getId(), questionCreateDTO);

            // Create answers for this question
            // Inside the loop for creating questions and their answers
            // Inside the loop for creating questions and their answers
            question.getAnswers().forEach(answerCreateDto -> {
            try {
            // Convert AnswerCreateDTO to AnswerDTO
            AnswerDTO answerDto = new AnswerDTO();
            answerDto.setText(answerCreateDto.getText());
            answerDto.setCorrect(answerCreateDto.isCorrect());
            AnswerDTO createdAnswer = answerService.createAnswer(createdQuestionDTO.getId(), answerDto);
            } catch (Exception e) {
                throw new RuntimeException("Error creating answer: " + e.getMessage(), e);
                }
            });
        }
    }

    @Transactional(readOnly = true)
    public CompleteQuizDTO getCompleteQuizById(UUID quizId) throws QuizNotFoundException {
    Quiz quiz = quizRepository.findById(quizId)
            .orElseThrow(() -> new QuizNotFoundException("Quiz not found with id: " + quizId));

        List<CompleteQuestionDTO> completeQuestionDTOs = quiz.getQuestions().stream().map(question -> {
            List<AnswerDTO> answerDTOs = question.getAnswers().stream().map(answer -> 
                new AnswerDTO(answer.getId(), answer.getText(), answer.isCorrect())
            ).collect(Collectors.toList());

            return new CompleteQuestionDTO(question.getId(), question.getText(), question.getQuestionType(), question.getMultimediaLink(), question.getTags().stream().map(Tag::getId).collect(Collectors.toSet()), answerDTOs);
        }).collect(Collectors.toList());

        return new CompleteQuizDTO(quiz.getId(), quiz.getTitle(), quiz.getDescription(), quiz.getCreator().getId(), quiz.getCategory().getId(), completeQuestionDTOs);
    }

    @Transactional
    public void updateCompleteQuiz(UUID quizId, CompleteQuizDTO completeQuizDTO) throws QuizNotFoundException {
        // Fetch the existing quiz
        Quiz quiz = quizRepository.findById(quizId)
            .orElseThrow(() -> new QuizNotFoundException("Quiz not found with id: " + quizId));

        // Update quiz details
        quiz.setTitle(completeQuizDTO.getTitle());
        quiz.setDescription(completeQuizDTO.getDescription());
        quiz.setCategory(categoryRepository.findById(completeQuizDTO.getCategoryId()).orElse(null));

        // Handle questions
        for (CompleteQuestionDTO completeQuestionDTO : completeQuizDTO.getQuestions()) {
            if (completeQuestionDTO.getId() == null) {
                // This is a new question
                createNewQuestionForQuiz(quiz, completeQuestionDTO);
            } else {
                // This is an existing question
                updateExistingQuestion(completeQuestionDTO);
            }
    }

    // Remove questions not present in the updated quiz
    removeQuestionsNotIncludedInUpdate(quiz, completeQuizDTO);

    quizRepository.save(quiz);
}

private void createNewQuestionForQuiz(Quiz quiz, CompleteQuestionDTO completeQuestionDTO) {
    Question question = new Question();
    question.setQuiz(quiz);
    question.setText(completeQuestionDTO.getText());
    question.setQuestionType(completeQuestionDTO.getQuestionType());
    question.setMultimediaLink(completeQuestionDTO.getMultimediaLink());

    // Fetch and associate existing tags
    Set<Tag> tags = completeQuestionDTO.getTags().stream()
        .map(tagId -> tagRepository.findById(tagId)
            .orElseThrow(() -> new TagNotFoundException(tagId)))
        .collect(Collectors.toSet());
    question.setTags(tags);

    Question savedQuestion = questionRepository.save(question);

    // Handle answers for the new question
    completeQuestionDTO.getAnswers().forEach(answerCreateDto -> {
        AnswerDTO answerDto = new AnswerDTO();
        answerDto.setText(answerCreateDto.getText());
        answerDto.setCorrect(answerCreateDto.isCorrect());
        // Assuming createAnswer method has been appropriately adjusted to accept AnswerDTO
        answerService.createAnswer(savedQuestion.getId(), answerDto);
    });
}



@Transactional
public void updateExistingQuestion(CompleteQuestionDTO completeQuestionDTO) {
    Question question = questionRepository.findById(completeQuestionDTO.getId())
        .orElseThrow(() -> new RuntimeException("Question not found with id: " + completeQuestionDTO.getId()));

    question.setText(completeQuestionDTO.getText());
    question.setQuestionType(completeQuestionDTO.getQuestionType());
    question.setMultimediaLink(completeQuestionDTO.getMultimediaLink());

    // Update tags
    Set<Tag> updatedTags = completeQuestionDTO.getTags().stream()
        .map(tagId -> tagRepository.findById(tagId).orElseThrow(() -> new TagNotFoundException(tagId)))
        .collect(Collectors.toSet());
    question.setTags(updatedTags);

    // Update answers
    updateQuestionAnswers(question, completeQuestionDTO.getAnswers());

    questionRepository.save(question);
}

private void updateQuestionAnswers(Question question, List<AnswerDTO> newAnswersDTOs) {
    // Create a map of existing answers for easy ID lookup
    Map<UUID, Answer> existingAnswersMap = question.getAnswers().stream()
            .collect(Collectors.toMap(Answer::getId, answer -> answer));

    // Prepare a set to track IDs of answers that need to be kept
    Set<UUID> answerIdsToKeep = new HashSet<>();

    for (AnswerDTO dto : newAnswersDTOs) {
        Answer answer;
        if (dto.getId() != null && existingAnswersMap.containsKey(dto.getId())) {
            // This is an update to an existing answer
            answer = existingAnswersMap.get(dto.getId());
        } else {
            // This is a new answer
            answer = new Answer();
            answer.setQuestion(question);
        }
        // Update answer details
        answer.setText(dto.getText());
        answer.setCorrect(dto.isCorrect());
        Answer savedAnswer = answerRepository.save(answer);
        
        // Track this answer ID as one to keep
        answerIdsToKeep.add(savedAnswer.getId());
    }

    // Remove answers that are no longer needed
    question.getAnswers().removeIf(answer -> !answerIdsToKeep.contains(answer.getId()));
    
    // Assuming you have a method in the AnswerRepository to bulk delete by IDs if necessary
}


@Transactional
private void removeQuestionsNotIncludedInUpdate(Quiz quiz, CompleteQuizDTO updatedQuizDTO) {
    Set<UUID> updatedQuestionIds = updatedQuizDTO.getQuestions().stream()
                                                  .filter(q -> q.getId() != null) // Ensure null IDs are filtered out
                                                  .map(CompleteQuestionDTO::getId)
                                                  .collect(Collectors.toSet());

    // Find questions to remove by their absence in the updatedQuizDTO
    List<Question> questionsToRemove = quiz.getQuestions().stream()
                                           .filter(question -> !updatedQuestionIds.contains(question.getId()))
                                           .collect(Collectors.toList());

    // Explicitly delete answers for each question to remove, if not using cascade deletion
    questionsToRemove.forEach(question -> {
        answerRepository.deleteAll(question.getAnswers()); // This assumes you have such a method in your AnswerRepository
        questionRepository.delete(question);
    });

    // This step might be redundant if you're using a JPA provider that automatically synchronizes the in-memory state with the database upon transaction commit
    quiz.getQuestions().removeAll(questionsToRemove);

    // Save changes to the quiz
    quizRepository.save(quiz);
}

@Transactional
public void deleteCompleteQuiz(UUID quizId) throws QuizNotFoundException {
    Quiz quiz = quizRepository.findById(quizId).orElseThrow(() ->
            new QuizNotFoundException("Quiz not found with id: " + quizId));

    // Optional: Explicitly delete answers associated with each question if not using cascade delete
    quiz.getQuestions().forEach(question -> 
            answerRepository.deleteAll(question.getAnswers()));

    // Delete the quiz. This will also delete all questions associated with the quiz if cascade delete is configured.
    quizRepository.delete(quiz);
}





}
