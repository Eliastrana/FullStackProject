package edu.ntnu.idatt2105.SpringbootBackend.repository;

import edu.ntnu.idatt2105.SpringbootBackend.model.Answer;
import edu.ntnu.idatt2105.SpringbootBackend.model.Question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * The {@code AnswerRepository} interface extends the Spring Data {@link JpaRepository} to provide
 * repository operations for the {@link Answer} entity. It facilitates the abstraction of common database
 * operations, allowing for the easy persistence, retrieval, update, and deletion of Answer entities within
 * the application.
 *<p>
 * This repository interface specifically includes custom methods beyond the standard CRUD operations provided
 * by {@link JpaRepository}, such as finding an answer by its associated {@link Question} and the answer text.
 * These methods enable targeted queries that support the application's business logic, particularly in the
 * context of quizzes and questions management.
 *<p>
 * Use of {@code AnswerRepository} simplifies data access layers by leveraging Spring Data JPA's capabilities
 * to automatically implement repository interfaces. This allows developers to focus more on the business logic
 * and less on boilerplate code associated with data access.
 *
 * @author Vegard Johnsen
 * @version 0.1
 * @since 0.1
 * @see Answer for the entity being managed by this repository
 * @see Question for the related entity used in custom query methods
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer, UUID> {

    /**
     * Finds an {@link Answer} by its associated {@link Question} and the answer text.
     * This method enables the lookup of specific answers within the context of a particular
     * question, facilitating features such as answer verification or deduplication.
     *
     * @param question The {@link Question} to which the answer belongs.
     * @param text The text content of the answer.
     * @return An {@link Optional} containing the found {@link Answer} if present; otherwise, an empty {@link Optional}.
     */
    Optional<Answer> findByQuestionAndText(Question question, String text);
}
