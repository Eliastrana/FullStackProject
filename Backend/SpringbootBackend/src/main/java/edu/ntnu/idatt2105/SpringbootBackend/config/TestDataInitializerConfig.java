package edu.ntnu.idatt2105.SpringbootBackend.config;

import edu.ntnu.idatt2105.SpringbootBackend.model.*;
import edu.ntnu.idatt2105.SpringbootBackend.repository.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("test") // This makes sure that the bean is only loaded when the "test" profile is active
public class TestDataInitializerConfig {

    @Bean
    CommandLineRunner initData(UserRepository userRepository,
                               RoleRepository roleRepository,
                               CategoryRepository categoryRepository,
                               QuizRepository quizRepository,
                               QuestionRepository questionRepository,
                               TagRepository tagRepository,
                               AnswerRepository answerRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            // Create roles
            Role userRole = new Role();
            userRole.setRole("ROLE_USER");
            roleRepository.save(userRole);

            Role adminRole = new Role();
            adminRole.setRole("ROLE_ADMIN");
            roleRepository.save(adminRole);

            // Create users
            User user1 = new User();
            user1.setUsername("testUser");
            user1.setPassword(passwordEncoder.encode("password"));
            user1.setEmail("testUser@example.com");
            user1.setEnabled(true);
            user1.setAccountNonExpired(true);
            user1.setAccountNonLocked(true);
            user1.setCredentialsNonExpired(true);
            userRepository.save(user1);

            // Set user roles
            Set<UserRole> userRoles = new HashSet<>();
            UserRole userUserRole = new UserRole();
            userUserRole.setUser(user1);
            userUserRole.setRole(userRole);
            userRoles.add(userUserRole);
            user1.setUserRoles(userRoles);
            userRepository.save(user1);

            // Create categories
            Category mathCategory = new Category();
            mathCategory.setCategoryName("Mathematics");
            mathCategory.setDescription("Mathematical challenges");
            categoryRepository.save(mathCategory);

            // Create quizzes
            Quiz mathQuiz = new Quiz();
            mathQuiz.setTitle("Basic Math Quiz");
            mathQuiz.setDescription("A simple quiz on basic mathematics.");
            mathQuiz.setDifficulty(Difficulty.EASY);
            mathQuiz.setCategory(mathCategory);
            mathQuiz.setCreator(user1);
            mathQuiz.setPublic(true);
            quizRepository.save(mathQuiz);

            // Create questions and answers
            Question question1 = new Question();
            question1.setQuiz(mathQuiz);
            question1.setText("What is 2 + 2?");
            question1.setQuestionType(QuestionType.MULTIPLE_CHOICE);
            question1.setCreationDate(LocalDateTime.now());
            questionRepository.save(question1);

            Answer answer1 = new Answer();
            answer1.setText("4");
            answer1.setCorrect(true);
            answer1.setQuestion(question1);
            answerRepository.save(answer1);

            Answer answer2 = new Answer();
            answer2.setText("22");
            answer2.setCorrect(false);
            answer2.setQuestion(question1);
            answerRepository.save(answer2);

            // Set answers to question
            Set<Answer> answers = new HashSet<>(Arrays.asList(answer1, answer2));
            question1.setAnswers(answers);
            questionRepository.save(question1);

            // Optionally, create and assign tags to questions
            Tag mathTag = new Tag();
            mathTag.setName("Arithmetic");
            tagRepository.save(mathTag);

            question1.setTags(new HashSet<>(Arrays.asList(mathTag)));
            questionRepository.save(question1);

            // Print some feedback
            System.out.println("Initialized database with sample data.");
        };
    }
}
