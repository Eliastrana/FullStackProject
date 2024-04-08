package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.model.Difficulty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class DifficultyServiceTest {

    @InjectMocks
    private DifficultyService difficultyService;

    @Test
    void getAllDifficulties_ReturnsAllValues() {
        // Act
        List<Difficulty> difficulties = difficultyService.getAllDifficulties();

        // Assert
        assertNotNull(difficulties, "The returned list should not be null.");
        assertEquals(Difficulty.values().length, difficulties.size(), "The size of the returned list should match the number of enum constants.");

        // Optionally, verify each Difficulty enum is present in the returned list
        for (Difficulty difficulty : Difficulty.values()) {
            boolean containsDifficulty = difficulties.contains(difficulty);
            assertTrue(containsDifficulty, "The list should contain the difficulty: " + difficulty);
        }
    }
    @Test
void getAllDifficulties_ReturnsImmutableList() {
    List<Difficulty> difficulties = difficultyService.getAllDifficulties();

    Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
        difficulties.add(Difficulty.EASY); // Attempt to modify the list
    });

    assertNotNull(exception);
}
@Test
void getAllDifficulties_ContainsNoNullElements() {
    List<Difficulty> difficulties = difficultyService.getAllDifficulties();
    assertFalse(difficulties.contains(null), "The list should not contain null elements.");
}
}