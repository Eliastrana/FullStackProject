package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.model.Role;
import edu.ntnu.idatt2105.SpringbootBackend.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Transactional
    public Role createRole(Role role) {
        // Add validation or business logic as necessary
        return roleRepository.save(role);
    }

    // Implement update and delete methods as needed
}
