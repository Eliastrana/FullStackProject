package edu.ntnu.idatt2105.SpringbootBackend.repository;

import edu.ntnu.idatt2105.SpringbootBackend.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, UUID> {
    
}
