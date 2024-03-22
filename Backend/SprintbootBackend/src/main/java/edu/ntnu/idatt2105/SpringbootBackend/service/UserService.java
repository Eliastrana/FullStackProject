package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.dto.UserDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.UserNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.repository.UserRepository;
import edu.ntnu.idatt2105.SpringbootBackend.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO findUser(UserDTO userDTO) {
        Optional<User> user = userRepository.findByUsername(userDTO.getUsername());
        user.ifPresent(Objects::requireNonNull);
        return userMapper.toUserDTO(user
                .orElseThrow(() -> new UserNotFoundException("User not found, username: " + userDTO.getUsername())));
    }

    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found, username: " + username));
    }
}