package edu.ntnu.idatt2105.SpringbootBackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String role;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserRole> userRoles;

    @Override
    public String getAuthority() {
        return "ROLE_" + role;
    }
}
