package com.escrowpj.escrow.controller;

import com.escrowpj.escrow.dto.*;
import com.escrowpj.escrow.entity.User;
import com.escrowpj.escrow.security.JwtUtil;
import com.escrowpj.escrow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    //  REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

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
    }


    //  LOGIN (FIXED FOR FRONTEND)
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @RequestBody AuthRequest request
    ) {

        // 1️ Authenticate
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2️ Get user
        User user = userService.getByEmail(request.getEmail());

        // 3️ Generate token
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        // 4️ Build response data
        LoginResponse loginResponse = new LoginResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name(), // REQUIRED BY FRONTEND
                token
        );

        // 5️ Final API response
        return ResponseEntity.ok(
                new ApiResponse<>(true, loginResponse)
        );
    }
}
