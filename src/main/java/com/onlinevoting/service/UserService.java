package com.onlinevoting.service;

import com.onlinevoting.dto.RegisterRequest;
import com.onlinevoting.entity.User;
import com.onlinevoting.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // Encrypt password before storing
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        // Normal registration creates a voter
        user.setRole("VOTER");

        return userRepository.save(user);
    }
}