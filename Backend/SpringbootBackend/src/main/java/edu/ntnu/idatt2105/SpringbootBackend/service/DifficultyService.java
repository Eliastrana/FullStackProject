package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.model.Difficulty;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * The {@code DifficultyService} class provides functionalities related to handling the
 * {@link Difficulty} enumeration. This includes retrieving a list of all possible
 * difficulty levels defined within the quiz system.
 *
 * @author Vegard Johnsen
 * @version 0.1
 * @since 0.1
 * @see Difficulty for the enumeration this service handles.
 */
@Service
public class DifficultyService {


    /**
     * Retrieves a list of all defined {@link Difficulty} levels available in the system.
     * This method facilitates the exposure of available difficulty levels for quizzes,
     * allowing for a dynamic presentation and selection in user interfaces.
     *
     * @return A {@link List} of {@link Difficulty} enumerations representing all possible
     * difficulty levels defined.
     */
    public List<Difficulty> getAllDifficulties() {
        return Arrays.asList(Difficulty.values());
    }
}
