package edu.ntnu.idatt2105.SpringbootBackend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserCreationDTO {
    private String username;
    private String password;
    private String email;

    public UserCreationDTO(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
