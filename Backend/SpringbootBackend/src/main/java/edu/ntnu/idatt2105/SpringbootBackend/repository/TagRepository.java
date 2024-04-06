package edu.ntnu.idatt2105.SpringbootBackend.repository;

import edu.ntnu.idatt2105.SpringbootBackend.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * The {@code TagRepository} interface extends Spring Data's {@link JpaRepository} to provide
 * CRUD operations for {@link Tag} entities within the application's persistence layer.
 * This repository plays a pivotal role in managing tags, which are used to categorize and
 * organize questions within quizzes by topics or themes.
 * <p>
 * Through the {@code TagRepository}, the application can add, remove, and query tags, facilitating
 * the dynamic grouping of questions for better organization and retrieval based on their associated
 * tags. The {@code findByName} method, in particular, allows for the efficient retrieval of a tag
 * by its name, supporting operations such as tag lookup and validation during question creation
 * and editing processes.
 * <p>
 * By providing a straightforward interface for tag management, this repository aids in enhancing
 * the flexibility and usability of the quiz system, allowing users to filter and search for
 * questions and quizzes based on specific tags or topics of interest.
 *
 * @author Sander R. Skofsrud
 * @version 0.1
 * @since 0.1
 * @see Tag for the entity managed by this repository.
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {

    /**
     * Retrieves a {@link Tag} entity by its name.
     * This method is essential for checking the existence of tags and for associating questions
     * with tags based on tag names. It supports the application's need to efficiently categorize
     * questions and quizzes, thereby enhancing the overall organization and searchability of quiz
     * content.
     *
     * @param name The name of the tag to retrieve.
     * @return An {@link Optional} containing the {@link Tag} entity if found, or an empty
     *         {@link Optional} if no tag with the specified name exists.
     */
    Optional<Tag> findByName(String name);
}
