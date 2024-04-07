package edu.ntnu.idatt2105.SpringbootBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Main application class for the Spring Boot backend API.
 * This class serves as the entry point for the Spring Boot application,
 * initializing and starting the application server.
 * It is responsible for bootstrapping the application,
 * setting up configuration, and making the application ready-to-handle requests.
 *
 * @author Vegard Johnsen, Sander R. Skofsrud
 * @see SpringApplication
 * @see SpringBootApplication
 * @version 0.1
 * @since 0.1
 */
@SpringBootApplication
public class SpringbootBackendApplication {
	/**
	 * Main method for starting the Spring Boot application.
	 * This method initializes the Spring Boot application context
	 * and starts the embedded Tomcat server to handle incoming requests.
	 *
	 * @param args Command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure()
                            .directory("/Users/vegardjohnsen/Documents/NTNU/Vaar23/Fullstack/prosjekt/Backend/SpringbootBackend")
                            .load();
        System.setProperty("SECRET_KEY", dotenv.get("SECRET_KEY"));
		System.setProperty("DATABASE_URL", dotenv.get("DATABASE_URL"));
		System.setProperty("DATABASE_USERNAME", dotenv.get("DATABASE_USERNAME"));
		System.setProperty("DATABASE_PASSWORD", dotenv.get("DATABASE_PASSWORD"));
		SpringApplication.run(SpringbootBackendApplication.class, args);
	}
}
