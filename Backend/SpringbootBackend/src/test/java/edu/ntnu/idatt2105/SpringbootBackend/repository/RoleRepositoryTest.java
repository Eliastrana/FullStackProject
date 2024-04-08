package edu.ntnu.idatt2105.SpringbootBackend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import edu.ntnu.idatt2105.SpringbootBackend.model.Role;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;

@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testFindByRole() {
        Role role = new Role();
        role.setRole("ROLE_USER");
        role = roleRepository.save(role);

        List<Role> foundRole = roleRepository.findByRole("ROLE_USER");

        assertTrue(foundRole.size() == 1);
        assertEquals(role.getRole(), foundRole.get(0).getRole());
    }
}
