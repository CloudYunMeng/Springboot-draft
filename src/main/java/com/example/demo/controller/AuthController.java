package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final UserService userService;
    
    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        try {
            User user = userService.registerUser(registrationDto);
            return ResponseEntity.ok(new LoginResponse(
                "User registered successfully", 
                user.getId(), 
                user.getEmail(), 
                user.getName()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new LoginResponse(e.getMessage(), null, null, null));
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        boolean isAuthenticated = userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
        
        if (isAuthenticated) {
            Optional<User> userOpt = userService.findUserByEmail(loginRequest.getEmail());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                return ResponseEntity.ok(new LoginResponse(
                    "Login successful", 
                    user.getId(), 
                    user.getEmail(), 
                    user.getName()
                ));
            }
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new LoginResponse("Invalid email or password", null, null, null));
    }
}
