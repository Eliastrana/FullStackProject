package edu.ntnu.idatt2105.SpringbootBackend.startup;

import edu.ntnu.idatt2105.SpringbootBackend.controller.UserController;
import edu.ntnu.idatt2105.SpringbootBackend.dto.CategoryDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.UserCreationDTO;
import edu.ntnu.idatt2105.SpringbootBackend.model.Category;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.repository.CategoryRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.UserRepository;
import edu.ntnu.idatt2105.SpringbootBackend.security.AuthenticationResponse;
import edu.ntnu.idatt2105.SpringbootBackend.service.AuthenticationService;
import edu.ntnu.idatt2105.SpringbootBackend.service.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private UserRoleService userRoleService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Override
  public void run(String... args) {
    userRepository.findByUsername("admin").ifPresentOrElse(admin -> logger.info("Admin user already exists"), () -> {
      UserCreationDTO userCreationDTO = new UserCreationDTO("admin", "admin", "admin@email.com");
      authenticationService.register(userCreationDTO);
      User admin = userRepository.findByUsername("admin").orElseThrow();
      userRoleService.assignRoleToUser(admin.getUsername(), "ADMIN");
    });

    userRepository.findByUsername("user").ifPresentOrElse(user -> logger.info("User user already exists"), () -> {
      UserCreationDTO userCreationDTO = new UserCreationDTO("user", "user", "user@email.com");
      authenticationService.register(userCreationDTO);
    });

    // Categories to add
    String[] categories = {"Gaming", "Sports", "Science", "Music", "History", "Geography", "General Knowledge"};
    String[] descriptions = {
            "Games and gaming equipment",
            "Sporting goods and equipment",
            "Scientific equipment and supplies",
            "Musical instruments and equipment",
            "Historical artifacts and memorabilia",
            "Maps and geographical equipment",
            "General knowledge and trivia"
    };

    for (int i = 0; i < categories.length; i++) {
      if (!categoryRepository.existsByCategoryName(categories[i])) {
        Category newCategory = new Category(UUID.randomUUID(), categories[i], descriptions[i]);
        categoryRepository.save(newCategory);
      } else {
        logger.info("Category " + categories[i] + " already exists");
      }
    }
  }
}