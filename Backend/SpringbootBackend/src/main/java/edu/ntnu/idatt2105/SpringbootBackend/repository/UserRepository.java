package edu.ntnu.idatt2105.SpringbootBackend.repository;

import edu.ntnu.idatt2105.SpringbootBackend.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for {@link User} entities.
 * Provides an abstract interface to the database
 * for handling the persistence and retrieval of User entities.
 * This repository leverages Spring Data JPA
 * to reduce boilerplate code for database operations.
 * <p>
 * It includes methods for user-specific queries, such as finding a user by username, beyond the standard
 * CRUD operations provided by {@link JpaRepository}.
 * </p>
 *
 * @author Vegard Johnsen, Sander R. Skofsrud
 * @see User
 * @see JpaRepository
 * @since 0.1
 * @version 0.1
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Retrieves an {@link Optional} of a {@link User} based on the provided username.
     * This method is used to support login operations and user validation checks, ensuring that usernames
     * are unique within the system.
     * <p>
     * If a user with the specified username exists, it returns an {@link Optional} containing the user.
     * If the user does not exist, it returns an empty {@link Optional}.
     * </p>
     *
     * @param username the username of the user to find.
     * @return An {@link Optional} containing the found user, or an empty {@link Optional} if no user is found.
     */
    Optional<User> findByUsername(String username);
    void deleteByUsername(String username);
}
