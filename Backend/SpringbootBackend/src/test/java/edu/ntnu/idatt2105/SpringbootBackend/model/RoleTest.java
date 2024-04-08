package edu.ntnu.idatt2105.SpringbootBackend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RoleTest {

    private Role role;

    @BeforeEach
    public void setup() {
        role = new Role();
    }

    @Test
    public void testId() {
        UUID id = UUID.randomUUID();
        role.setId(id);
        assertEquals(id, role.getId());
    }

    @Test
    public void testRole() {
        String roleName = "ROLE_USER";
        role.setRole(roleName);
        assertEquals(roleName, role.getRole());
    }

    @Test
    public void testUserRoles() {
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole());
        role.setUserRoles(userRoles);
        assertNotNull(role.getUserRoles());
        assertEquals(userRoles.size(), role.getUserRoles().size());
    }

    @Test
    public void testGetAuthority() {
        String roleName = "ROLE_USER";
        role.setRole(roleName);
        assertEquals(roleName, role.getAuthority());
    }

    @Test
    public void testToString() {
        String roleName = "ROLE_USER";
        role.setRole(roleName);
        assertEquals("Role(id=null, role=ROLE_USER)", role.toString());
    }

}
