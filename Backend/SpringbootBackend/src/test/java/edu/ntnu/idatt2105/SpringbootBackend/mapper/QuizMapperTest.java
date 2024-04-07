package edu.ntnu.idatt2105.SpringbootBackend.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import edu.ntnu.idatt2105.SpringbootBackend.dto.AnswerCreateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.CompleteQuestionDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.CompleteQuizDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.QuizDTO;
import edu.ntnu.idatt2105.SpringbootBackend.model.Category;
import edu.ntnu.idatt2105.SpringbootBackend.model.Difficulty;
import edu.ntnu.idatt2105.SpringbootBackend.model.Image;
import edu.ntnu.idatt2105.SpringbootBackend.model.QuestionType;
import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.repository.ImageRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class QuizMapperTest {

    @Mock
    private QuestionMapper questionMapper;

    @InjectMocks
    private QuizMapper quizMapper;

    @Mock
    private ImageRepository imageRepository;

    @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
}

    @Test
    public void testToQuizDTO() {
        // Setup
        Category category = new Category(UUID.randomUUID(), "Science", "Science quizzes");
        Image image = new Image(UUID.randomUUID(), "image.jpg", "png", 123L, new byte[]{});
        User creator = new User();
        creator.setId(UUID.randomUUID());
        creator.setUsername("user");
        creator.setEmail("test@mail.com");
        creator.setPassword("password");
        
        Quiz quiz = new Quiz();
        quiz.setId(UUID.randomUUID());
        quiz.setTitle("Science Quiz");
        quiz.setDescription("A quiz about science");
        quiz.setCategory(category);
        quiz.setImage(image);
        quiz.setDifficulty(Difficulty.EASY);
        quiz.setPublic(true);
        quiz.setCreator(creator);
        
        // Act
        QuizDTO result = quizMapper.toQuizDTO(quiz);

        // Assert
        assertEquals(quiz.getId(), result.getId());
        assertEquals(quiz.getTitle(), result.getTitle());
        assertEquals(quiz.getDescription(), result.getDescription());
        assertEquals(quiz.getCategory().getId(), result.getCategoryId());
        assertEquals(quiz.getImage().getId(), result.getImageId());
        assertEquals(quiz.getDifficulty(), result.getDifficulty());
        assertEquals(quiz.getCreator().getId(), result.getCreatorId());
    }

    @Test
    void updateQuizFromDTO() {
    // Assuming UUIDs for ID fields and an enum for difficulty
    UUID categoryId = UUID.randomUUID();
    UUID creatorId = UUID.randomUUID();
    UUID imageId = UUID.randomUUID();

    QuizDTO quizDTO = QuizDTO.builder()
            .id(UUID.randomUUID()) // Assuming this ID is not used in update
            .title("Updated Title")
            .description("Updated Description")
            .difficulty(Difficulty.HARD) // Assuming Difficulty is an enum
            .isPublic(true) // Notice the change to match the assertion
            .categoryId(categoryId)
            .creatorId(creatorId)
            .imageId(imageId)
            .build();

    // Setup existing Quiz with default values
    Quiz existingQuiz = new Quiz();
    existingQuiz.setCategory(new Category(UUID.randomUUID(), "Original Category", "Original Description"));
    existingQuiz.setCreator(new User(UUID.randomUUID(), "Original Creator", "Original Password", "original@email.com", false, false, false, false, null));
    existingQuiz.setImage(new Image(UUID.randomUUID(), "Original Imagename", "original fileType", 123L, new byte[]{}));

    // Act: Perform the update
    Quiz updatedQuiz = quizMapper.updateQuizFromDTO(quizDTO, existingQuiz);

    // Assert: Verify that the existingQuiz object's fields are updated correctly
    assertEquals(quizDTO.getTitle(), updatedQuiz.getTitle());
    assertEquals(quizDTO.getDescription(), updatedQuiz.getDescription());
    // For category and creator, since the test setup doesn't actually fetch them from a repository,
    // we're asserting based on the IDs set directly.
    assertEquals(categoryId, updatedQuiz.getCategory().getId());
    assertEquals(creatorId, updatedQuiz.getCreator().getId());
    assertEquals(imageId, updatedQuiz.getImage().getId());
    // Assuming a method to convert Difficulty enum to String if necessary
    assertEquals(quizDTO.getDifficulty().toString().strip(), updatedQuiz.getDifficulty().toString().strip());
    assertEquals(quizDTO.getIsPublic(), updatedQuiz.isPublic());
    }

    @Test
    void toEntityFromCompleteQuizDTOWithQuestionsTest() {
        // Setup CompleteQuizDTO with questions
        CompleteQuizDTO quizDTO = new CompleteQuizDTO();
        quizDTO.setTitle("Science Quiz");
        quizDTO.setDescription("A quiz covering basic science topics.");
        quizDTO.setDifficulty(Difficulty.EASY); // Assuming difficulty is a String. Adjust if it's an enum
        quizDTO.setIsPublic(true);

        // Setup questions and answers
        Set<CompleteQuestionDTO> questions = new HashSet<>();
        CompleteQuestionDTO question1 = new CompleteQuestionDTO();
        question1.setText("What is the chemical symbol for water?");
        question1.setQuestionType(QuestionType.FILL_IN_BLANK); // Assuming this is a String. Adjust if it's an enum
        question1.setMultimediaLink("https://example.com/image.jpg");
        question1.setImageName("imageName");
        question1.setImageType("image/jpeg");
        question1.setImageData("base64String");

        Set<AnswerCreateDTO> answers = new HashSet<>();
        AnswerCreateDTO answer1 = new AnswerCreateDTO();
        answer1.setText("H2O");
        answer1.setCorrect(true);
        answers.add(answer1);

        question1.setAnswers(answers);
        questions.add(question1);

        quizDTO.setQuestions(questions);

        // Set image data
        quizDTO.setImageData("base64Image");
        quizDTO.setImageName("image.jpg");
        quizDTO.setImageType("image/jpeg");

        User creator = new User(UUID.randomUUID(), "creator@example.com", "Creator Name", "password", false, false, false, false, null);
        Category category = new Category(UUID.randomUUID(), "Science", "Science Description");

        // Mocking the image repository response
        when(imageRepository.save(any(Image.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Quiz quiz = quizMapper.toEntity(quizDTO, creator, category);

        // Assert
        assertEquals(quizDTO.getTitle(), quiz.getTitle());
        assertEquals(quizDTO.getDescription(), quiz.getDescription());
        assertEquals(quizDTO.getDifficulty(), quiz.getDifficulty());
        assertEquals(quizDTO.getIsPublic(), quiz.isPublic());
        assertNotNull(quiz.getImage());
        assertEquals(1, quiz.getQuestions().size()); // Verifying one question was added
        verify(imageRepository, times(1)).save(any(Image.class)); // Verify image was saved once
    }
}
