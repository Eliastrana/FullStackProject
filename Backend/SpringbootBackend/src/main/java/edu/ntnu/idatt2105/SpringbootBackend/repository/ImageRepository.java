package edu.ntnu.idatt2105.SpringbootBackend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ntnu.idatt2105.SpringbootBackend.model.Image;

public interface ImageRepository extends JpaRepository<Image, UUID> {
    
    
    
}
