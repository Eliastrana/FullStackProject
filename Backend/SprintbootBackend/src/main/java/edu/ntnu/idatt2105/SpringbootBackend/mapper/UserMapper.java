package edu.ntnu.idatt2105.SpringbootBackend.mapper;

import edu.ntnu.idatt2105.SpringbootBackend.dto.UserDTO;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toUserDTO(User user) {
        return new UserDTO(user.getUsername(), user.getEmail());
    }
}
