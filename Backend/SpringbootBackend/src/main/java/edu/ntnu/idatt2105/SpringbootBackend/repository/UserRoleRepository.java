package edu.ntnu.idatt2105.SpringbootBackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.model.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    void deleteAllByUser(User user);
    Optional<UserRole> findByUserUsername(String username);
    boolean existsByUserUsernameAndRoleRole(String username, String role);
}