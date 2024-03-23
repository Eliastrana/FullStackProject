package edu.ntnu.idatt2105.SpringbootBackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "User Creation DTO")
@NoArgsConstructor
@Data
public class UserCreationDTO {
    @Schema(required = true, example = "johnDoe", description = "Username of the new user")
    private String username;

    @Schema(required = true, example = "Password123", description = "Password of the new user")
    private String password;

    @Schema(required = true, example = "john.doe@example.com", description = "Email of the new user")
    private String email;

    public UserCreationDTO(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
