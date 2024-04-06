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
        Quiz quiz = new Quiz(quizId, "Test Quiz", "Test Description", creator, category, image, Difficulty.EASY, true);

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

    // Add similar tests for other methods: toQuiz, updateQuizFromDTO, toEntity, and toCompleteQuizDTO
}
