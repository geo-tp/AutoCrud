package com.autocrud.main.controllers;

import com.autocrud.main.dtos.CreateUserRequestDTO;
import com.autocrud.main.dtos.UserResponseDTO;
import com.autocrud.main.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public UserResponseDTO createUser(@RequestBody CreateUserRequestDTO userRequestDTO) {
        if (userRequestDTO.getRoles() == null || userRequestDTO.getRoles().isEmpty()) {
            userRequestDTO.setRoles(Arrays.asList("ROLE_USER"));
        }

        return userService.createUser(
            userRequestDTO.getEmail(),
            userRequestDTO.getPassword(),
            userRequestDTO.getRoles()
        );
    }
}