package com.example.demo.config;

import com.example.demo.model.User;
import com.example.demo.model.Draft;
import com.example.demo.model.Draft.DraftStatus;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.DraftRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final DraftRepository draftRepository;
    private final PasswordEncoder passwordEncoder;
    
    public DataInitializer(UserRepository userRepository, DraftRepository draftRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.draftRepository = draftRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Initialize users if database is empty
        if (userRepository.count() == 0) {
            System.out.println("Initializing sample users...");
            
            // Create sample users
            userRepository.save(new User("John Doe", "john.doe@example.com", passwordEncoder.encode("password123"), 28));
            userRepository.save(new User("Jane Smith", "jane.smith@example.com", passwordEncoder.encode("password123"), 25));
            userRepository.save(new User("Bob Johnson", "bob.johnson@example.com", passwordEncoder.encode("password123"), 32));
            userRepository.save(new User("Alice Brown", "alice.brown@example.com", passwordEncoder.encode("password123"), 29));
            userRepository.save(new User("Charlie Wilson", "charlie.wilson@example.com", passwordEncoder.encode("password123"), 35));
            
            System.out.println("Sample users initialized successfully!");
        } else {
            System.out.println("Users already exist. Skipping user initialization.");
        }
        
        // Initialize drafts if they don't exist
        if (draftRepository.count() == 0) {
            System.out.println("Initializing sample drafts...");
            
            // Get all users for draft participants
            List<User> allUsers = userRepository.findAll();
            if (allUsers.size() >= 3) {
                User john = allUsers.get(0);
                User jane = allUsers.get(1);
                User bob = allUsers.get(2);
                User alice = allUsers.size() > 3 ? allUsers.get(3) : john;
                User charlie = allUsers.size() > 4 ? allUsers.get(4) : jane;
                
                // Create sample drafts
                Draft monthlyPrize = new Draft();
                monthlyPrize.setTitle("Monthly Prize Draw");
                monthlyPrize.setDescription("Monthly prize draw for all employees");
                monthlyPrize.setNumberOfWinners(2);
                monthlyPrize.setStatus(DraftStatus.PENDING);
                monthlyPrize.setCreatedAt(LocalDateTime.now().minusDays(2));
                monthlyPrize.setParticipants(Arrays.asList(john, jane, bob, alice, charlie));
                draftRepository.save(monthlyPrize);
                
                Draft teamLunch = new Draft();
                teamLunch.setTitle("Free Team Lunch");
                teamLunch.setDescription("Lucky draw for free team lunch");
                teamLunch.setNumberOfWinners(1);
                teamLunch.setStatus(DraftStatus.PENDING);
                teamLunch.setCreatedAt(LocalDateTime.now().minusDays(1));
                teamLunch.setParticipants(Arrays.asList(john, jane, bob));
                draftRepository.save(teamLunch);
                
                // Create an executed draft example
                Draft completedDraft = new Draft();
                completedDraft.setTitle("Previous Week Raffle");
                completedDraft.setDescription("Last week's completed raffle");
                completedDraft.setNumberOfWinners(1);
                completedDraft.setStatus(DraftStatus.EXECUTED);
                completedDraft.setCreatedAt(LocalDateTime.now().minusDays(7));
                completedDraft.setExecutedAt(LocalDateTime.now().minusDays(5));
                completedDraft.setParticipants(Arrays.asList(alice, charlie));
                completedDraft.setWinners(Arrays.asList(alice));
                draftRepository.save(completedDraft);
                
                System.out.println("Sample drafts initialized successfully!");
            } else {
                System.out.println("Not enough users to create sample drafts.");
            }
        } else {
            System.out.println("Drafts already exist. Skipping draft initialization.");
        }
    }
}
