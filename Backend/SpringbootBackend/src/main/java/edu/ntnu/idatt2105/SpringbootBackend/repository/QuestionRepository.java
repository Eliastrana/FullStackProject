package edu.ntnu.idatt2105.SpringbootBackend.repository;

import java.util.UUID;
import edu.ntnu.idatt2105.SpringbootBackend.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, UUID>{
    List<Question> findAllByQuizId(UUID quizId);

    
}
