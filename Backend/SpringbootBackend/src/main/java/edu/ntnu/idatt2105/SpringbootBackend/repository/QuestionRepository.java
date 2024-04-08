package edu.ntnu.idatt2105.SpringbootBackend.repository;

import java.util.UUID;
import edu.ntnu.idatt2105.SpringbootBackend.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * The {@code QuestionRepository} interface extends the Spring Data {@link JpaRepository} for managing
 * {@link Question} entities, allowing for the performance of standard CRUD operations along with custom
 * queries specific to the handling of questions within the quiz application.
 * <p>
 * It provides methods to retrieve, save, update, and delete questions, as well as to perform more
 * specialized queries such as fetching all questions associated with a particular quiz. This
 * repository thus plays a crucial role in managing the data layer related to quiz questions, enabling
 * seamless data manipulation and interaction within the system.
 * <p>
 * By abstracting the complexity of direct database access, `QuestionRepository` allows for cleaner,
 * more maintainable code in the service layer, focusing on business logic without being bogged down
 * by database access details.
 *
 * @author Vegard Johnsen, Sander R. Skofsrud
 * @version 0.1
 * @since 0.1
 * @see Question for the entity managed by this repository.
 */
public interface QuestionRepository extends JpaRepository<Question, UUID>{
    List<Question> findAllByQuizId(UUID quizId);   
}
