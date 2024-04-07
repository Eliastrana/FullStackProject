package edu.ntnu.idatt2105.SpringbootBackend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import edu.ntnu.idatt2105.SpringbootBackend.model.Role;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testFindByRole() {
        Role role = new Role();
        role.setRole("ROLE_USER");
        role = roleRepository.save(role);

        Optional<Role> foundRole = roleRepository.findByRole("ROLE_USER");

        assertTrue(foundRole.isPresent());
        assertEquals(role.getRole(), foundRole.get().getRole());
    }
}
