package edu.ntnu.idatt2105.SpringbootBackend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRoleTest {

    private UserRole userRole;

    @BeforeEach
    public void setup() {
        userRole = new UserRole();
    }

    @Test
    public void testId() {
        UUID id = UUID.randomUUID();
        userRole.setId(id);
        assertEquals(id, userRole.getId());
    }

    @Test
    public void testUser() {
        User user = new User();
        userRole.setUser(user);
        assertEquals(user, userRole.getUser());
    }

    @Test
    public void testRole() {
        Role role = new Role();
        userRole.setRole(role);
        assertEquals(role, userRole.getRole());
    }

    // Add more tests for other methods in the UserRole class
}