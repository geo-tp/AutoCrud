package com.autocrud.main.controllers;

import com.autocrud.main.dtos.LoginRequestDTO;
import com.autocrud.main.dtos.LoginResponseDTO;
import com.autocrud.main.services.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService jwtTokenService;

    public AuthController(AuthenticationManager authenticationManager, AuthService jwtTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())
        );

        String username = authentication.getName();
        Long userId = jwtTokenService.extractUserId(authentication);
        String token = jwtTokenService.generateToken(username, userId);
        
        return new LoginResponseDTO(token);
    }
}
