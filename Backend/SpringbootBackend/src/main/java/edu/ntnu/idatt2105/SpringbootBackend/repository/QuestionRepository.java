package edu.ntnu.idatt2105.SpringbootBackend.repository;

import edu.ntnu.idatt2105.SpringbootBackend.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
    List<Question> findAllByQuizId(UUID quizId);
}
