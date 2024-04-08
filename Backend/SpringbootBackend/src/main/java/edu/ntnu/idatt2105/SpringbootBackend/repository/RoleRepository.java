package edu.ntnu.idatt2105.SpringbootBackend.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ntnu.idatt2105.SpringbootBackend.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    List<Role> findByRole(String role);
}
