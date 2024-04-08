package edu.ntnu.idatt2105.SpringbootBackend.repository;

import edu.ntnu.idatt2105.SpringbootBackend.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * The {@code QuizRepository} interface extends the Spring Data {@link JpaRepository} for managing
 * {@link Quiz} entities within the quiz application. It provides a robust abstraction layer for
 * performing standard CRUD operations on quizzes, in addition to facilitating the creation and
 * management of custom queries as required by the application's business logic.
 * <p>
 * Through this repository, quizzes can be retrieved, saved, updated, and deleted seamlessly, allowing
 * for the efficient manipulation of quiz data. The repository's methods enable interactions with
 * the database that underlies the quiz management system, simplifying the process of data access
 * and persistence.
 * <p>
 * The `QuizRepository` is essential for supporting the core functionalities of the quiz application,
 * such as quiz creation, modification, and retrieval, thus ensuring that quizzes can be managed
 * effectively and intuitively by both the application's users and its backend services.
 *
 * @author Vegard Johnsen
 * @version 0.1
 * @since 0.1
 * @see Quiz for the entity managed by this repository.
 */
@Repository
public interface QuizRepository extends JpaRepository<Quiz, UUID> {
  List<Quiz> findAllByIsPublicTrue();
}
