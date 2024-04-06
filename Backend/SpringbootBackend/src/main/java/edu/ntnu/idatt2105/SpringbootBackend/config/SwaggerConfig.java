package edu.ntnu.idatt2105.SpringbootBackend.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;


/**
 * Configuration class for setting up Swagger OpenAPI documentation.
 * This class configures the OpenAPI specifications for the application,
 * defining security requirements and schemes for the API documentation.
 * The security scheme is set up to use HTTP bearer tokens, specifically JWT tokens,
 * for authenticating API requests. This configuration is crucial for correctly
 * documenting the security aspects of the API endpoints in the generated OpenAPI documentation.
 *
 * The {@link #customOpenAPI()} method defines the security requirements globally
 * for all API endpoints, indicating that a bearer token is expected for authentication.
 * The {@link #api()} method groups all endpoints under the "/api/**" path into a single
 * OpenAPI group, making it easier to organize and navigate the API documentation.
 *
 * Usage of JWT tokens for security is specified, aligning with the application's
 * authentication mechanism. This setup helps ensure that the API documentation accurately
 * reflects the security measures in place for accessing the application's endpoints.
 *
 * @author Sander rom skofsrud
 * @version 0.1
 * @since 0.1
 *
 * @see OpenAPI for the OpenAPI configuration setup
 * @see SecurityScheme for details on the security scheme configuration
 * @see GroupedOpenApi for grouping API endpoints into a single OpenAPI documentation group
 */
@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .components(new io.swagger.v3.oas.models.Components()
                    .addSecuritySchemes("bearerAuth", new SecurityScheme()
                            .name("bearerAuth")
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")));
  }

  @Bean
  public GroupedOpenApi api() {
    return GroupedOpenApi.builder()
            .group("spring")
            .pathsToMatch("/api/**")
            .build();
  }
}
