package edu.ntnu.idatt2105.SpringbootBackend.mapper;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ntnu.idatt2105.SpringbootBackend.dto.UserDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.UserDetailsDTO;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

class UserMapperTest {

    @Autowired
    private UserMapper userMapper = new UserMapper();

    @Test
    void toUserDTOTest() {
        // Setup
        User user = new User();
        user.setUsername("johnDoe");
        user.setEmail("johndoe@example.com");

        // Act
        UserDTO result = userMapper.toUserDTO(user);

        // Assert
        assertEquals(user.getUsername(), result.getUsername());
    }
    @Test
void toUserDetailsTest() {
    // Setup
    User user = new User();
    user.setId(UUID.randomUUID());
    user.setUsername("janeDoe");
    user.setEmail("janedoe@example.com");

    // Act
    UserDetailsDTO result = userMapper.toUserDetails(user);

    // Assert
    assertNotNull(result);
    assertEquals(user.getId(), result.getId());
    assertEquals(user.getUsername(), result.getUsername());
    assertEquals(user.getEmail(), result.getEmail());
    }
@Test
void toUserDetailsWithNullUserTest() {
    // Act
    UserDetailsDTO result = userMapper.toUserDetails(null);

    // Assert
    assertNull(result);
    }
}
