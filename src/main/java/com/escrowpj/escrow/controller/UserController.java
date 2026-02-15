package com.escrowpj.escrow.controller;

import com.escrowpj.escrow.dto.ApiResponse;
import com.escrowpj.escrow.dto.ProfileResponse;
import com.escrowpj.escrow.entity.User;
import com.escrowpj.escrow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile(Principal principal) {

        User user = userService.getProfile(principal.getName());

        ProfileResponse response = new ProfileResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()
        );

        return ResponseEntity.ok(
                new ApiResponse<>(true, response)
        );
    }
}
