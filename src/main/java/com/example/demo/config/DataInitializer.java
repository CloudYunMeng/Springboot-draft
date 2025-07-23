package com.example.demo.config;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Only initialize data if database is empty
        if (userRepository.count() == 0) {
            System.out.println("Initializing sample data...");
            
            userRepository.save(new User("John Doe", "john.doe@example.com", passwordEncoder.encode("password123"), 28));
            userRepository.save(new User("Jane Smith", "jane.smith@example.com", passwordEncoder.encode("password123"), 25));
            userRepository.save(new User("Bob Johnson", "bob.johnson@example.com", passwordEncoder.encode("password123"), 32));
            userRepository.save(new User("Alice Brown", "alice.brown@example.com", passwordEncoder.encode("password123"), 29));
            
            System.out.println("Sample data initialized successfully!");
        } else {
            System.out.println("Database already contains data. Skipping initialization.");
        }
    }
}
