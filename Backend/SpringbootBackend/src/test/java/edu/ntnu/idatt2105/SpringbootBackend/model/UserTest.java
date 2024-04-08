package edu.ntnu.idatt2105.SpringbootBackend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class UserTest {

    private User user;

    @BeforeEach
    public void setup() {
        user = new User();
    }

    @Test
    public void testId() {
        UUID id = UUID.randomUUID();
        user.setId(id);
        assertEquals(id, user.getId());
    }

    @Test
    public void testUsername() {
        String username = "testUser";
        user.setUsername(username);
        assertEquals(username, user.getUsername());
    }

    @Test
    public void testPassword() {
        String password = "testPassword";
        user.setPassword(password);
        assertEquals(password, user.getPassword());
    }

    @Test
    public void testEmail() {
        String email = "test@example.com";
        user.setEmail(email);
        assertEquals(email, user.getEmail());
    }

    @Test
    public void testUserRoles() {
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole());
        user.setUserRoles(userRoles);
        assertNotNull(user.getUserRoles());
        assertEquals(userRoles.size(), user.getUserRoles().size());
    }

    @Test
    public void testToString() {
        String username = "testUser";
        user.setUsername(username);
        assertEquals("User(id=null, username=testUser, password=null, email=null, accountNonExpired=false, accountNonLocked=false, credentialsNonExpired=false, enabled=false)", user.toString());
    }
}
