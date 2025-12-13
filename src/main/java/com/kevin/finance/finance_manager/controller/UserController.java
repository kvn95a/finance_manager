package com.kevin.finance.finance_manager.controller;

import com.kevin.finance.finance_manager.entity.User;
import com.kevin.finance.finance_manager.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // DTO for registration
    public static class RegisterRequest {
        public String username;
        public String password;
        public String email;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        try {
            User newUser = userService.register(req.username, req.password, req.email);
            return ResponseEntity.ok("User registered with ID: " + newUser.getId());
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
