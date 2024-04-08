package edu.ntnu.idatt2105.SpringbootBackend.model;

/**
 * Enumerates the various types of questions that can be created within the quiz system.
 * Each type defines a different format and approach for presenting questions and collecting answers
 * from users. The {@code QuestionType} is critical for determining how a question is rendered
 * and interacted with in the user interface.
 * This enum is used throughout the system to ensure that questions are created, displayed, and
 * managed according to their intended format.
 *
 * @author Vegard Johnsen, Sander R. Skofsrud
 * @version 0.1
 * @since 0.1
 * @see Question for how this enum is applied to questions within the system.
 */
public enum QuestionType {
    MULTIPLE_CHOICE,
    STUDY,
    FILL_IN_BLANK
}
