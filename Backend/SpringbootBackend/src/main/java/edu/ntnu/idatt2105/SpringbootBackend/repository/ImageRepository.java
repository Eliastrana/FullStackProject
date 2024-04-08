    package edu.ntnu.idatt2105.SpringbootBackend.repository;

    import java.util.UUID;

    import org.springframework.data.jpa.repository.JpaRepository;

    import edu.ntnu.idatt2105.SpringbootBackend.model.Image;

    /**
     * The {@code ImageRepository} interface extends the Spring Data {@link JpaRepository} for managing
     * {@link Image} entities, facilitating common database operations such as create, read, update,
     * and delete (CRUD) for images stored in the application.
     *
     * This repository serves as an abstraction layer between the application's business logic and
     * the underlying database, simplifying the management of images associated with quizzes and questions.
     * It provides a straightforward interface for storing and retrieving images, thereby enhancing the
     * multimedia aspect of the quiz system.
     *
     * Utilizing the Spring Data JPA repository abstracts away the boilerplate data access code, enabling
     * more focus on the application's core functionality. The {@code ImageRepository} specifically deals
     * with image data storage, making it an essential part of the application's infrastructure for
     * handling image uploads and associations.
     *
     * @author Vegard Johnsen, Sander R. Skofsrud
     * @version 0.1
     * @since 0.1
     * @see Image for the entity managed by this repository.
     */
    public interface ImageRepository extends JpaRepository<Image, UUID> {



    }
