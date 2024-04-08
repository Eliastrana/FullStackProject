package edu.ntnu.idatt2105.SpringbootBackend.model;


/**
 * Defines the difficulty levels available for quizzes within the quiz system.
 * The {@code Difficulty} enum encapsulates the range of complexities a quiz can
 * have, from {@code EASY} to {@code HARD}, allowing quizzes to be categorized
 * and searched based on their challenge level.
 *
 * @author Vegard Johnsen
 * @version 0.1
 * @since 0.1
 */
public enum Difficulty {
    EASY,
    MEDIUM,
    HARD
}
