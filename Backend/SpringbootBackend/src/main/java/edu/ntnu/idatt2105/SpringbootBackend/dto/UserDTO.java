package edu.ntnu.idatt2105.SpringbootBackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "User DTO for login")
@Data
public class UserDTO {
    @Schema(required = true, example = "johnDoe", description = "Username of the user")
    private String username;

    @Schema(required = true, example = "Password123", description = "Password of the user")
    private String password;

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
