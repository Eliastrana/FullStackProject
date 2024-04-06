package edu.ntnu.idatt2105.SpringbootBackend.mapper;

import edu.ntnu.idatt2105.SpringbootBackend.dto.*;
import edu.ntnu.idatt2105.SpringbootBackend.model.*;
import edu.ntnu.idatt2105.SpringbootBackend.repository.CategoryRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class QuizMapperTest {

    @Mock
    private QuestionMapper questionMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private QuizMapper quizMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void toQuizDTO() {
        // Create a mock Quiz object
        UUID quizId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        UUID creatorId = UUID.randomUUID();
        UUID imageId = UUID.randomUUID();
        Category category = new Category(categoryId, "TestCategory", "Test Description");
        User creator = new User(creatorId, "creatorUsername", "creator@example.com", null, false, false, false, false, null);
        Image image = new Image();
        image.setId(imageId);
        image.setFileType("png");
        image.setFilename("test.png");
        image.setFileContent(new byte[]{1, 2, 3, 4, 5});
        Quiz quiz = new Quiz();
        quiz.setId(quizId);
        quiz.setTitle("Test Quiz");
        quiz.setDescription("Test Description");
        quiz.setCreator(creator);
        quiz.setCategory(category);
        quiz.setImage(image);



        // Mock the behavior of categoryRepository to return the expected category
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // Call the method to be tested
        QuizDTO quizDTO = quizMapper.toQuizDTO(quiz);

        // Verify the result
        assertEquals(quizId, quizDTO.getId());
        assertEquals("Test Quiz", quizDTO.getTitle());
        assertEquals("Test Description", quizDTO.getDescription());
        assertEquals(creatorId, quizDTO.getCreatorId());
        assertEquals(categoryId, quizDTO.getCategoryId());
        assertEquals(imageId, quizDTO.getImageId());

        // Verify that categoryRepository.findById() was called
        verify(categoryRepository, times(1)).findById(categoryId);
    }
    @Test
    void toQuizDTO_WithNullCategory_ShouldReturnDTOWithNullCategoryId() {
        // Create a mock Quiz object with a null category
        UUID quizId = UUID.randomUUID();
        UUID creatorId = UUID.randomUUID();
        UUID imageId = UUID.randomUUID();
        User creator = new User(creatorId, "creatorUsername", "creator@example.com");
        Image image = new Image(imageId, "image.jpg", "image/jpeg", new byte[]{}, LocalDateTime.now());
        Quiz quiz = new Quiz(quizId, "Test Quiz", "Test Description", creator, null, image, Difficulty.EASY, true);

        // Call the method to be tested
        QuizDTO quizDTO = quizMapper.toQuizDTO(quiz);

        // Verify that the DTO's categoryId is null
        assertNull(quizDTO.getCategoryId());
    }

    @Test
    void toQuizDTO_WithNullImage_ShouldReturnDTOWithNullImageId() {
        // Create a mock Quiz object with a null image
        UUID quizId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        UUID creatorId = UUID.randomUUID();
        Category category = new Category(categoryId, "TestCategory", "Test Description");
        User creator = new User(creatorId, "creatorUsername", "creator@example.com");
        Quiz quiz = new Quiz(quizId, "Test Quiz", "Test Description", creator, category, null, Difficulty.EASY, true);

        // Mock the behavior of categoryRepository to return the expected category
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // Call the method to be tested
        QuizDTO quizDTO = quizMapper.toQuizDTO(quiz);

        // Verify that the DTO's imageId is null
        assertNull(quizDTO.getImageId());
    }

    @Test
    void toEntity_WithQuestions_ShouldMapQuestionsCorrectly() {
        // Create a mock QuizCreateDTO with questions
        UUID creatorId = UUID.randomUUID();
        User creator = new User(creatorId, "creatorUsername", "creator@example.com");
        Category category = new Category(UUID.randomUUID(), "TestCategory", "Test Description");
        QuizCreateDTO quizCreateDTO = new QuizCreateDTO("Test Quiz", "Test Description", Difficulty.EASY, true, creatorId, category.getId());
        Set<QuestionCreateDTO> questionCreateDTOs = new HashSet<>();
        questionCreateDTOs.add(new QuestionCreateDTO("Question 1", QuestionType.MULTIPLE_CHOICE, null, null));
        questionCreateDTOs.add(new QuestionCreateDTO("Question 2", QuestionType.OPEN_ENDED, null, null));
        quizCreateDTO.setQuestions(questionCreateDTOs);

        // Mock the behavior of questionMapper to return a mock Question for each questionCreateDTO
        when(questionMapper.toEntity(any(QuestionCreateDTO.class), any(Quiz.class)))
                .thenAnswer(invocation -> {
                    QuestionCreateDTO questionCreateDTO = invocation.getArgument(0);
                    return new Question(questionCreateDTO.getText(), questionCreateDTO.getQuestionType(), null, null, null);
                });

        // Call the method to be tested
        Quiz resultQuiz = quizMapper.toEntity(quizCreateDTO, creator, category);

        // Verify that all questions were mapped correctly
        assertEquals(2, resultQuiz.getQuestions().size());
        assertTrue(resultQuiz.getQuestions().stream().anyMatch(q -> q.getText().equals("Question 1")));
        assertTrue(resultQuiz.getQuestions().stream().anyMatch(q -> q.getText().equals("Question 2")));
    }

}
