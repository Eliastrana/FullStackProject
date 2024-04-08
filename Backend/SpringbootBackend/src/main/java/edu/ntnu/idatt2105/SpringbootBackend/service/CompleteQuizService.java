package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.dto.CompleteQuestionDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.CompleteQuizDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.CategoryNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.CreatorNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.QuizNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.TagNotFoundException;
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

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Provides comprehensive services for handling the lifecycle of quizzes, including their creation,
 * retrieval, updating, and deletion. This service facilitates the management of quizzes and their
 * related entities such as questions and answers, ensuring a cohesive quiz management experience.
 *
 * @author Vegard Johnsen, Sander Rom Skofsrud
 * @version 0.1
 * @since 0.1
 * @see Quiz
 * @see QuizRepository
 */
@Service
public class CompleteQuizService {

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
    private final Logger logger = LoggerFactory.getLogger(CompleteQuizService.class);

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

    /**
     * Creates a complete quiz based on the provided {@link CompleteQuizDTO}, including its questions
     * and potential answers. This method orchestrates the creation of a quiz and its related entities,
     * ensuring consistency across the quiz structure.
     *
     * @param completeQuizDTO The {@link CompleteQuizDTO} containing the quiz details.
     * @return The unique identifier ({@link UUID}) of the created quiz.
     */
@Transactional
public UUID createCompleteQuiz(CompleteQuizDTO completeQuizDTO) {
    User creator = userRepository.findById(completeQuizDTO.getCreatorId())
            .orElseThrow(() -> new CreatorNotFoundException("Creator with ID: " + completeQuizDTO.getCreatorId() + " not found."));

    Category category = null;
    category = categoryRepository.findById(completeQuizDTO.getCategoryId())
            .orElseThrow(() -> new CategoryNotFoundException("Category with ID: " + completeQuizDTO.getCategoryId() + " not found."));


    Quiz quiz = new Quiz();
            quiz.setTitle(completeQuizDTO.getTitle());
            quiz.setDescription(completeQuizDTO.getDescription());
            quiz.setCreator(creator);
            quiz.setCategory(category);
            quiz.setDifficulty(completeQuizDTO.getDifficulty());
            quiz.setPublic(completeQuizDTO.getIsPublic());
            logger.info(String.valueOf(quiz.isPublic()));
            logger.info(String.valueOf(completeQuizDTO.getIsPublic()));
    
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
    logger.info(String.valueOf(savedQuiz.isPublic()));
    return savedQuiz.getId();
}


    /**
     * Retrieves the details of a complete quiz identified by its UUID, including its questions
     * and answers, encapsulated in a {@link CompleteQuizDTO}.
     *
     * @param quizId The UUID of the quiz to retrieve.
     * @return A {@link CompleteQuizDTO} representing the retrieved quiz.
     * @throws QuizNotFoundException If the quiz is not found.
     */

    @Transactional(readOnly = true)
    public CompleteQuizDTO getCompleteQuizById(UUID quizId) throws QuizNotFoundException {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found with id: " + quizId));
    
        return quizMapper.toCompleteQuizDTO(quiz);
    }


    /**
     * Updates an existing quiz, identified by its UUID, with new information provided in a
     * {@link CompleteQuizDTO}. This includes updates to the quiz's questions and answers.
     *
     * @param quizId The UUID of the quiz to update.
     * @param completeQuizDTO The {@link CompleteQuizDTO} containing the new quiz details.
     * @throws QuizNotFoundException If the quiz to update is not found.
     */
    @Transactional
    public void updateCompleteQuiz(UUID quizId, CompleteQuizDTO completeQuizDTO) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found with id: " + quizId));

        // Update quiz details
        quiz.setTitle(completeQuizDTO.getTitle());
        quiz.setDescription(completeQuizDTO.getDescription());
        Category category = categoryRepository.findById(completeQuizDTO.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category with ID: " + completeQuizDTO.getCategoryId() + " not found."));
        quiz.setCategory(category);
        quiz.setDifficulty(completeQuizDTO.getDifficulty());
        quiz.setPublic(completeQuizDTO.getIsPublic());

        Image image = processImage(completeQuizDTO.getImageName(), completeQuizDTO.getImageType(), completeQuizDTO.getImageData());
        quiz.setImage(image);

        // Existing questions handling
        List<UUID> updatedQuestionIds = completeQuizDTO.getQuestions().stream()
                .map(CompleteQuestionDTO::getId)
                .collect(Collectors.toList());

        // Remove questions not included in the updated DTO
        quiz.getQuestions().removeIf(question -> !updatedQuestionIds.contains(question.getId()));

        // Update and add questions
        completeQuizDTO.getQuestions().forEach(questionDTO -> {
            if(questionDTO.getId() != null) {
                questionRepository.findById(questionDTO.getId()).ifPresent(question -> {
                    updateExistingQuestionFromDTO(question, questionDTO);
                });
            } else {
                // Add new question
                Question newQuestion = questionMapper.toEntity(questionDTO, quiz);
                questionRepository.save(newQuestion);
            }
        });

        quizRepository.save(quiz);
    }


    private void updateExistingQuestionFromDTO(Question question, CompleteQuestionDTO dto) {
        question.setText(dto.getText());
        question.setQuestionType(dto.getQuestionType());
        question.setMultimediaLink(dto.getMultimediaLink());

        // Clear previous answers and tags before updating
        question.getAnswers().clear();
        question.getTags().clear();

        // Process tags
        Set<Tag> tags = dto.getTags().stream()
                .map(tagName -> tagRepository.findByName(tagName)
                        .orElseGet(() -> Tag.builder().name(tagName).build()))
                .collect(Collectors.toSet());
        question.setTags(tags);

        // Process answers
        dto.getAnswers().forEach(answerDTO -> {
            Answer answer = new Answer();
            answer.setText(answerDTO.getText());
            answer.setCorrect(answerDTO.isCorrect());
            answer.setQuestion(question);
            question.getAnswers().add(answer);
        });

        questionRepository.save(question);
    }


    private Image processImage(String imageName, String imageType, String imageData) {
        if (imageName == null || imageType == null || imageData == null) {
            return null; // If any parameter is missing, return null
        }

        // Decode the image data from Base64
        byte[] decodedImg = Base64.getDecoder().decode(imageData);

        // Create a new Image entity or update the existing one
        Image image = new Image();
        image.setFilename(imageName);
        image.setFileType(imageType);
        image.setData(decodedImg);
        image.setSize(decodedImg.length);

        // Save the image entity to the database
        return imageRepository.save(image);
    }


    /**
     * Deletes an existing quiz and its related entities, such as questions and answers,
     * identified by its UUID.
     *
     * @param quizId The UUID of the quiz to delete.
     * @throws QuizNotFoundException If the quiz to delete is not found.
     */

@Transactional
public void deleteCompleteQuiz(UUID quizId) throws QuizNotFoundException {
    Quiz quiz = quizRepository.findById(quizId).orElseThrow(() ->
            new QuizNotFoundException("Quiz not found with id: " + quizId));

        quiz.getQuestions().forEach(question ->
                answerRepository.deleteAll(question.getAnswers()));

        quizRepository.delete(quiz);
    }


    public List<CompleteQuizDTO> getCompleteQuizzesByTag(String tag) throws TagNotFoundException {
        Tag tagEntity = tagRepository.findByName(tag)
                .orElseThrow(() -> new TagNotFoundException(tag));

        // Stream over the questions, map each question to its quiz, and collect unique quizzes
        Set<Quiz> quizzes = tagEntity.getQuestions().stream()
                .map(Question::getQuiz)
                .collect(Collectors.toSet());

        if (quizzes.isEmpty()) {
            throw new QuizNotFoundException("No quizzes found with tag: " + tag);
        }

        // Map each quiz to CompleteQuizDTO
        return quizzes.stream()
                .map(quizMapper::toCompleteQuizDTO)
                .collect(Collectors.toList());
    }

}
