package com.autocrud.main.services;

import com.autocrud.main.entities.CustomUserDetails;
import com.autocrud.main.entities.User;
import com.autocrud.main.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Returning CustomUserDetails with userId, email, password, and authorities
        return new CustomUserDetails(user.getId(), user.getEmail(), user.getPassword(), user.getAuthorities());
    }
}
