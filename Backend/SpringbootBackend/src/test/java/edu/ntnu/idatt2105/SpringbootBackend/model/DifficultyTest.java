package edu.ntnu.idatt2105.SpringbootBackend.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DifficultyTest {

    @Test
    public void testEnum() {
        // Ensure all expected values are present
        assertEquals(3, Difficulty.values().length);

        // Test the presence of each value
        assertNotNull(Difficulty.valueOf("EASY"));
        assertNotNull(Difficulty.valueOf("MEDIUM"));
        assertNotNull(Difficulty.valueOf("HARD"));
    }
    @Test
    public void testEnumValues() {
        // Test the values of each enum
        assertEquals("EASY", Difficulty.EASY.name());
        assertEquals("MEDIUM", Difficulty.MEDIUM.name());
        assertEquals("HARD", Difficulty.HARD.name());
    }

    @Test
    public void testEnumOrder() {
        // Test the order of the enums
        assertEquals(0, Difficulty.EASY.ordinal());
        assertEquals(1, Difficulty.MEDIUM.ordinal());
        assertEquals(2, Difficulty.HARD.ordinal());
    }
}
