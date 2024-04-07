/* 
package edu.ntnu.idatt2105.SpringbootBackend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

import edu.ntnu.idatt2105.SpringbootBackend.dto.AnswerCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.CompleteQuestionDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.CompleteQuizDTO;
import edu.ntnu.idatt2105.SpringbootBackend.mapper.AnswerMapper;
import edu.ntnu.idatt2105.SpringbootBackend.mapper.QuestionMapper;
import edu.ntnu.idatt2105.SpringbootBackend.model.Answer;
import edu.ntnu.idatt2105.SpringbootBackend.model.Category;
import edu.ntnu.idatt2105.SpringbootBackend.model.Difficulty;
import edu.ntnu.idatt2105.SpringbootBackend.model.Image;
import edu.ntnu.idatt2105.SpringbootBackend.model.Question;
import edu.ntnu.idatt2105.SpringbootBackend.model.QuestionType;
import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;
import edu.ntnu.idatt2105.SpringbootBackend.model.Tag;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.repository.AnswerRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.CategoryRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.QuestionRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.QuizRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.UserRepository;
import edu.ntnu.idatt2105.SpringbootBackend.service.CompleteQuizService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class CreateCompleteQuizServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private ImageService imageService;

    @Mock
    private QuestionService questionService;

    @Mock
    QuestionMapper questionMapper;

    @Mock
    QuestionRepository questionRepository;

    @Mock
    private AnswerService answerService;

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private AnswerMapper answerMapper;


    @InjectMocks
    private CompleteQuizService completeQuizService;

    @Test
    void createCompleteQuiz_Success() {
        // Arrange
        UUID creatorId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        User creator = new User();
        creator.setId(creatorId);
        Category category = new Category();
        category.setId(categoryId);

        AnswerCreateDTO answer1 = new AnswerCreateDTO("Norway", true);
        AnswerCreateDTO answer2 = new AnswerCreateDTO("Sweden", false);
        AnswerCreateDTO answer3 = new AnswerCreateDTO("Denmark", false);

        // Adding these answers to a Set as they will be associated with a question
        Set<AnswerCreateDTO> answers = Set.of(answer1, answer2, answer3);

        CompleteQuestionDTO questionDTO = new CompleteQuestionDTO();
        questionDTO.setText("What is the capital of Norway?");
        questionDTO.setQuestionType(QuestionType.MULTIPLE_CHOICE);
        questionDTO.setMultimediaLink("https://example.com/image/norway_capital.png");
        questionDTO.setTags(Set.of("Geography", "Europe"));
        questionDTO.setAnswers(answers);
        questionDTO.setImageName("norway_capital.png");
        questionDTO.setImageType("png");
        questionDTO.setImageData("iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==");

        CompleteQuizDTO completeQuizDTO = new CompleteQuizDTO();
        completeQuizDTO.setTitle("European Capitals Quiz");
        completeQuizDTO.setDescription("A quiz testing your knowledge of European capitals.");
        completeQuizDTO.setCreatorId(creatorId);
        completeQuizDTO.setCategoryId(categoryId);
        completeQuizDTO.setDifficulty(Difficulty.EASY);
        completeQuizDTO.setPublic(true);
        completeQuizDTO.setQuestions(Set.of(questionDTO));

        
        // Initialize the rest of completeQuizDTO with test data
    
        when(userRepository.findById(creatorId)).thenReturn(Optional.of(creator));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

// Stubbing with a detailed ArgumentMatcher
when(questionMapper.toEntity(ArgumentMatchers.argThat(new ArgumentMatcher<CompleteQuestionDTO>() {
    @Override
    public boolean matches(CompleteQuestionDTO dto) {
        // You can add more checks here if needed
        return "What is the capital of Norway?".equals(dto.getText());
    }
}), any(Quiz.class))).thenAnswer(invocation -> {
    CompleteQuestionDTO dto = invocation.getArgument(0);
    Quiz quiz = invocation.getArgument(1);

    Question question = new Question();
    question.setText(dto.getText());
    question.setQuiz(quiz);
    question.setQuestionType(dto.getQuestionType());
    question.setMultimediaLink(dto.getMultimediaLink());
    question.setCreationDate(LocalDateTime.now());

    // Simulate image processing
    if (dto.getImageData() != null && !dto.getImageData().isEmpty()) {
        byte[] decodedImg = Base64.getDecoder().decode(dto.getImageData());
        Image image = new Image();
        image.setFilename(dto.getImageName());
        image.setFileType(dto.getImageType());
        image.setData(decodedImg);
        // Note: In real usage, you might need to save the image or set its ID
        question.setImage(image);
    }

    // Simulate tags setting
    if (dto.getTags() != null) {
        Set<Tag> tags = dto.getTags().stream()
                .map(tagName -> {
                    Tag tag = new Tag();
                    tag.setName(tagName);
                    // Note: In real usage, you might need to check if the tag exists
                    return tag;
                })
                .collect(Collectors.toSet());
        question.setTags(tags);
    }

    // Simulate answers setting
    if (dto.getAnswers() != null) {
        Set<Answer> newAnswers = dto.getAnswers().stream()
                .map(answerDTO -> {
                    Answer answer = new Answer();
                    answer.setText(answerDTO.getText());
                    answer.setCorrect(answerDTO.isCorrect());
                    // Note: In real usage, you might need to associate the answer with the question
                    return answer;
                })
                .collect(Collectors.toSet());
        question.setAnswers(newAnswers);
    }

    return question;
});
    
    

        // Mock more interactions as necessary...
    
        // Act
        UUID resultId = completeQuizService.createCompleteQuiz(completeQuizDTO);
    
        // Assert
        assertNotNull(resultId);
        // Further assertions and verifications...
        // 
    }
    

    // Additional test methods...
}
*/