package edu.ntnu.idatt2105.SpringbootBackend.model;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;



@Entity
@Data
@Table(name = "user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Or your chosen strategy
    private UUID id; // Consider using a simple generated value for the join table ID

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

}
