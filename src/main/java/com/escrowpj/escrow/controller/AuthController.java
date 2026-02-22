package com.escrowpj.escrow.controller;

import com.escrowpj.escrow.dto.*;
import com.escrowpj.escrow.entity.User;
import com.escrowpj.escrow.repository.UserRepository;
import com.escrowpj.escrow.security.JwtUtil;
import com.escrowpj.escrow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final UserRepository userRepository;
    //  REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        try {

            User savedUser = userService.register(user);

            RegisterResponse registerResponse = new RegisterResponse(
                    savedUser.getId(),
                    savedUser.getName(),
                    savedUser.getEmail(),
                    savedUser.getRole().name()
            );

            HashMap<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "User registered successfully");
            response.put("data", registerResponse);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            return ResponseEntity.status(400).body(
                    new ApiResponse<>(false, e.getMessage())
            );
        }
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(
            @RequestBody AuthRequest request
    ) {

        // 1️ Check if email exists first
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null) {
            return ResponseEntity.status(404).body(
                    new ApiResponse<>(false, "Email is not registered")
            );
        }

        try {

            // 2️ Authenticate password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            // 3️ Generate token
            String token = jwtUtil.generateToken(
                    user.getEmail(),
                    user.getRole().name()
            );

            LoginResponse loginResponse = new LoginResponse(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getRole().name(),
                    token
            );

            return ResponseEntity.ok(
                    new ApiResponse<>(true, loginResponse)
            );

        } catch (BadCredentialsException e) {

            return ResponseEntity.status(401).body(
                    new ApiResponse<>(false, "Incorrect password")
            );
        }
    }

}
