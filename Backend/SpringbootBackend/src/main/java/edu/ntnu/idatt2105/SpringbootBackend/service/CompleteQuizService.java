package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.dto.AnswerCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.AnswerDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.CompleteQuestionDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.CompleteQuizDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.CategoryNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.CreatorNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.QuizNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.TagNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.QuestionNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.model.Answer;
import edu.ntnu.idatt2105.SpringbootBackend.model.Category;
import edu.ntnu.idatt2105.SpringbootBackend.model.Image;
import edu.ntnu.idatt2105.SpringbootBackend.model.Question;
import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;
import edu.ntnu.idatt2105.SpringbootBackend.model.Tag;
import edu.ntnu.idatt2105.SpringbootBackend.repository.QuizRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.CategoryRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.ImageRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.QuestionRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.TagRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.AnswerRepository;
import edu.ntnu.idatt2105.SpringbootBackend.mapper.AnswerMapper;
import edu.ntnu.idatt2105.SpringbootBackend.mapper.QuestionMapper;
import edu.ntnu.idatt2105.SpringbootBackend.mapper.QuizMapper;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.repository.UserRepository;

import java.util.UUID;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CompleteQuizService {

    private final AnswerService answerService;
    private final QuizRepository quizRepository;
    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;
    private final TagRepository tagRepository;
    private final AnswerRepository answerRepository;
    private final QuizMapper quizMapper;
    private final UserRepository userRepository;
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;
    private final ImageRepository imageRepository;

    @Autowired
    public CompleteQuizService(
        QuizService quizService, 
        QuestionService questionService, 
        AnswerService answerService, 
        QuizRepository quizRepository, 
        CategoryRepository categoryRepository, 
        QuestionRepository questionRepository,
        TagRepository tagRepository,
        AnswerRepository answerRepository,
        QuizMapper quizMapper,
        UserRepository userRepository,
        QuestionMapper questionMapper,
        AnswerMapper answerMapper,
        ImageRepository imageRepository
        ) 
        {
        this.answerService = answerService;
        this.quizRepository = quizRepository;
        this.categoryRepository = categoryRepository;
        this.questionRepository = questionRepository;
        this.tagRepository = tagRepository;
        this.answerRepository = answerRepository;
        this.quizMapper = quizMapper;
        this.userRepository = userRepository;
        this.questionMapper = questionMapper;
        this.answerMapper = answerMapper;
        this.imageRepository = imageRepository;

    }

@Transactional
public UUID createCompleteQuiz(CompleteQuizDTO completeQuizDTO) {
    User creator = userRepository.findById(completeQuizDTO.getCreatorId())
            .orElseThrow(() -> new CreatorNotFoundException("Creator with ID: " + completeQuizDTO.getCreatorId() + " not found."));

    Category category = null;
    if (completeQuizDTO.getCategoryName() != null && !completeQuizDTO.getCategoryName().isEmpty()) {
        category = categoryRepository.findByCategoryName(completeQuizDTO.getCategoryName())
                .orElseThrow(() -> new CategoryNotFoundException(completeQuizDTO.getCategoryName()));
    }
    
            Quiz quiz = new Quiz();
            quiz.setTitle(completeQuizDTO.getTitle());
            quiz.setDescription(completeQuizDTO.getDescription());
            quiz.setCreator(creator);
            quiz.setCategory(category);
            quiz.setDifficulty(completeQuizDTO.getDifficulty());
            quiz.setPublic(completeQuizDTO.isPublic());

    
    Image image = processImage(completeQuizDTO.getImageName(), completeQuizDTO.getImageType(), completeQuizDTO.getImageData());
    if (image != null) {
        quiz.setImage(image);
    }
    // Save the quiz entity with the category set
    Quiz savedQuiz = quizRepository.save(quiz);

    // Process each question in the DTO as a new question.
    completeQuizDTO.getQuestions().forEach(questionDTO -> {
    Question question = questionMapper.toEntity(questionDTO, savedQuiz);
    questionRepository.save(question);
    
    // Process and save each answer associated with the question
    questionDTO.getAnswers().forEach(answerCreateDTO -> {

        Optional<Answer> existingAnswer = answerRepository.findByQuestionAndText(question, answerCreateDTO.getText());
        Answer answer;
        if (existingAnswer.isPresent()) {
            answer = existingAnswer.get();
            answer.setCorrect(answerCreateDTO.isCorrect());
        } else {
            answer = answerMapper.toEntity(answerCreateDTO, question);
        }
        answerRepository.save(answer);
        });
    });
    
    return savedQuiz.getId(); // Return the ID of the saved quiz
}

    

    @Transactional(readOnly = true)
    public CompleteQuizDTO getCompleteQuizById(UUID quizId) throws QuizNotFoundException {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found with id: " + quizId));
    
        return quizMapper.toCompleteQuizDTO(quiz);
    }
    

    @Transactional
    public void updateCompleteQuiz(UUID quizId, CompleteQuizDTO completeQuizDTO) throws QuizNotFoundException {
        // Fetch the existing quiz
        Quiz quiz = quizRepository.findById(quizId)
            .orElseThrow(() -> new QuizNotFoundException("Quiz not found with id: " + quizId));

        // Update quiz details
        quiz.setTitle(completeQuizDTO.getTitle());
        quiz.setDescription(completeQuizDTO.getDescription());
        quiz.setCategory(categoryRepository.findByCategoryName(completeQuizDTO.getCategoryName()).orElse(null));
        quiz.setDifficulty(completeQuizDTO.getDifficulty());

        if (completeQuizDTO.getImageData() != null && !completeQuizDTO.getImageData().isEmpty()) {
            byte[] decodedImg = Base64.getDecoder().decode(completeQuizDTO.getImageData());
            Image image = Image.builder()
                    .filename(completeQuizDTO.getImageName())
                    .fileType(completeQuizDTO.getImageType())
                    .size(decodedImg.length)
                    .data(decodedImg)
                    .build();
        image = imageRepository.save(image);
        }

        completeQuizDTO.getQuestions().forEach(questionDTO -> {
            Question question = questionMapper.toEntity(questionDTO, quiz);
            questionRepository.save(question); // Save as a new question.
            
            // Process and save answers for the new question.
            questionDTO.getAnswers().forEach(answerDTO -> {
                Answer answer = answerMapper.toEntity(answerDTO, question);
                answerRepository.save(answer); // Save each new answer.
            });
        });

    quizRepository.save(quiz);
}





@Transactional
public void updateExistingQuestions(List<CompleteQuestionDTO> questionDTOs, UUID quizId) {
    List<Question> existingQuestions = questionRepository.findAllByQuizId(quizId);

    for (CompleteQuestionDTO dto : questionDTOs) {
        // Find an existing question based on some unique attributes
        Question question = findMatchingQuestion(existingQuestions, dto)
            .orElseThrow(() -> new QuestionNotFoundException("Question not found"));

        // Proceed with updates as before
        updateQuestionDetails(question, dto);
    }
}

private Optional<Question> findMatchingQuestion(List<Question> questions, CompleteQuestionDTO dto) {
    return questions.stream()
        .filter(q -> q.getText().equals(dto.getText()) && q.getMultimediaLink().equals(dto.getMultimediaLink()))
        .findFirst();
}

private void updateQuestionDetails(Question question, CompleteQuestionDTO dto) {
    question.setText(dto.getText());
    question.setQuestionType(dto.getQuestionType());
    question.setMultimediaLink(dto.getMultimediaLink());

    // Update or create image for the question
    if (dto.getImageData() != null && !dto.getImageData().isEmpty()) {
        Image image = processImage(dto.getImageName(), dto.getImageType(), dto.getImageData());
        question.setImage(image);
    }

    // Handle tags: find or create by name
    dto.getTags().stream()
                              .map(tagName -> tagRepository.findByName(tagName)
                                         .orElseGet(() -> {
                                             Tag newTag = new Tag();
                                             newTag.setName(tagName);
                                             return tagRepository.save(newTag);
                                         }))
                              .collect(Collectors.toSet());

    // Update answers
    updateAnswers(question, dto.getAnswers());
    
}

private void updateAnswers(Question question, Set<AnswerCreateDTO> dtoAnswers) {
    // Map existing answers for easy lookup
    Map<String, Answer> existingAnswers = question.getAnswers().stream()
                                                  .collect(Collectors.toMap(Answer::getText, answer -> answer));
    Set<Answer> updatedAnswers = new HashSet<>();

    for (AnswerCreateDTO dtoAnswer : dtoAnswers) {
        Answer answer = existingAnswers.getOrDefault(dtoAnswer.getText(),
                        new Answer()); // Create new if not found by text
        answer.setText(dtoAnswer.getText());
        answer.setCorrect(dtoAnswer.isCorrect());
        answer.setQuestion(question);
        updatedAnswers.add(answer);
    }

    // Remove answers that are not in the updated list
    question.getAnswers().retainAll(updatedAnswers);
    question.getAnswers().addAll(updatedAnswers);
}



@Transactional
public void deleteCompleteQuiz(UUID quizId) throws QuizNotFoundException {
    Quiz quiz = quizRepository.findById(quizId).orElseThrow(() ->
            new QuizNotFoundException("Quiz not found with id: " + quizId));

    quiz.getQuestions().forEach(question -> 
            answerRepository.deleteAll(question.getAnswers()));

    quizRepository.delete(quiz);
    }

    private Image processImage(String imageName, String imageType, String imageData) {
        if (imageName != null && imageType != null && imageData != null && !imageData.isEmpty()) {
            byte[] decodedImg = Base64.getDecoder().decode(imageData);
            return imageRepository.save(Image.builder()
                    .filename(imageName)
                    .fileType(imageType)
                    .size(decodedImg.length)
                    .data(decodedImg)
                    .build());
        }
        return null;
    }
}
