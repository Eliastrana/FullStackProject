package edu.ntnu.idatt2105.SpringbootBackend.repository;

import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setEmail("test@email.com");
        user = userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername("testUser");

        assertTrue(foundUser.isPresent());
        assertEquals(user.getUsername(), foundUser.get().getUsername());
    }
    @Test
public void testDeleteByUsername() {
    // Create a new user
    User user = new User();
    user.setUsername("testUser");
    user.setPassword("testPassword");
    user.setEmail("test@email.com");
    userRepository.save(user);

    // Verify that the user exists
    Optional<User> foundUser = userRepository.findByUsername("testUser");
    assertTrue(foundUser.isPresent());
    assertEquals(user.getUsername(), foundUser.get().getUsername());

    // Delete the user
    userRepository.deleteByUsername("testUser");

    // Verify that the user no longer exists
    Optional<User> deletedUser = userRepository.findByUsername("testUser");
    assertTrue(deletedUser.isEmpty());
}

}