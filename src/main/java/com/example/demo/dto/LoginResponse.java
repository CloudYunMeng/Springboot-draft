package com.example.demo.dto;

public class LoginResponse {
    private String message;
    private Long userId;
    private String email;
    private String name;
    
    // Default constructor
    public LoginResponse() {}
    
    // Constructor
    public LoginResponse(String message, Long userId, String email, String name) {
        this.message = message;
        this.userId = userId;
        this.email = email;
        this.name = name;
    }
    
    // Getters and Setters
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
