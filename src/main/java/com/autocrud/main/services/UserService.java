package com.autocrud.main.services;

import com.autocrud.main.dtos.UserResponseDTO;
import com.autocrud.main.entities.User;
import com.autocrud.main.exceptions.custom.EmailAlreadyUsedException;
import com.autocrud.main.exceptions.custom.PasswordTooShortException;
import com.autocrud.main.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO createUser(String email, String password, List<String> roles) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyUsedException();
        }

        if (password.length() < 8) {
            throw new PasswordTooShortException();
        }

        String hashedPassword = passwordEncoder.encode(password);
        User user = new User(email, hashedPassword);
        user.setRoles(roles);

        userRepository.save(user);

        return new UserResponseDTO(user.getEmail(), user.getRoles());
    }
}
