package edu.ntnu.idatt2105.SpringbootBackend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserDTO {
    private String username;
    private String password;
    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
