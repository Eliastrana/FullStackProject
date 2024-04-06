package edu.ntnu.idatt2105.SpringbootBackend.repository;

import edu.ntnu.idatt2105.SpringbootBackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * The {@code CategoryRepository} interface extends the Spring Data {@link JpaRepository} for managing
 * {@link Category} entities, offering a high-level abstraction for common database operations. It supports
 * the standard CRUD operations and includes additional methods for querying categories by their names.
 * <p>
 * This repository enables efficient category management within the application, facilitating the creation,
 * retrieval, update, and deletion of categories. Custom query methods, such as checking the existence of a
 * category by name or fetching a category by its name, support the application's requirements for handling
 * categories uniquely and ensuring data integrity.
 * <p>
 * Leveraging the capabilities of Spring Data JPA, {@code CategoryRepository} reduces the need for boilerplate
 * data access code and simplifies the interaction with the database, allowing developers to focus on
 * application-specific business logic.
 *
 * @author Vegard Johnsen, Sander R. Skofsrud
 * @version 0.1
 * @since 0.1
 * @see Category for the entity managed by this repository.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    /**
     * Checks if a category with the specified name already exists in the repository.
     * This method is useful for enforcing unique category names within the application,
     * ensuring that categories can be identified and referred to unambiguously.
     *
     * @param categoryName The name of the category to check for existence.
     * @return {@code true} if a category with the specified name exists, {@code false} otherwise.
     */
    boolean existsByCategoryName(String categoryName);
    /**
     * Retrieves a {@link Category} by its name.
     * This method allows for the fetching of a category based on its unique name, supporting
     * operations that require reference to specific categories, such as categorization of quizzes
     * or questions.
     *
     * @param categoryName The name of the category to retrieve.
     * @return An {@link Optional} containing the found {@link Category} if present; otherwise, an empty {@link Optional}.
     */
    Optional<Category> findByCategoryName(String categoryName);
}
