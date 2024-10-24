package com.autocrud.main.controllers;

import com.autocrud.main.dtos.UserDTO;
import com.autocrud.main.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public UserDTO createUser(@RequestParam String email, @RequestParam String password, @RequestParam(required = false) List<String> roles) {
        if (roles == null || roles.isEmpty()) {
            roles = Arrays.asList("ROLE_USER");
        }

        return userService.createUser(email, password, roles);
    }
}
