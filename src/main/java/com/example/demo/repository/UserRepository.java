package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Find user by email
    Optional<User> findByEmail(String email);
    
    // Find users by name containing (case insensitive)
    List<User> findByNameContainingIgnoreCase(String name);
    
    // Find users by age greater than
    List<User> findByAgeGreaterThan(Integer age);
    
    // Custom query example
    @Query("SELECT u FROM User u WHERE u.age BETWEEN ?1 AND ?2")
    List<User> findUsersByAgeBetween(Integer minAge, Integer maxAge);
}
