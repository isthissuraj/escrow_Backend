package com.escrowpj.escrow.service;

import com.escrowpj.escrow.dto.RegisterRequest;
import com.escrowpj.escrow.entity.Role;
import com.escrowpj.escrow.entity.User;
import com.escrowpj.escrow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // REGISTER
    public User register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // Convert DTO → Entity
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // CRITICAL: Hash password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Default role
        user.setRole(
                request.getRole() != null
                        ? request.getRole()
                        : Role.CLIENT
        );

        return userRepository.save(user);
    }

    //  GET BY EMAIL
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // GET PROFILE
    public User getProfile(String email) {
        return getByEmail(email);
    }
}