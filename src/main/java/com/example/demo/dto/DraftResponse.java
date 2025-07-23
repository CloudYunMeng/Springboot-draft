package com.example.demo.dto;

import com.example.demo.model.Draft.DraftStatus;
import java.time.LocalDateTime;
import java.util.List;

public class DraftResponse {
    private Long id;
    private String title;
    private String description;
    private Integer numberOfWinners;
    private DraftStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime executedAt;
    private List<UserDto> participants;
    private List<UserDto> winners;
    
    // Simple User DTO for participants and winners
    public static class UserDto {
        private Long id;
        private String name;
        private String email;
        
        public UserDto() {}
        
        public UserDto(Long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }
        
        // Getters and setters
        public Long getId() {
            return id;
        }
        
        public void setId(Long id) {
            this.id = id;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
    }
    
    // Constructors
    public DraftResponse() {}
    
    public DraftResponse(Long id, String title, String description, Integer numberOfWinners, 
                        DraftStatus status, LocalDateTime createdAt, LocalDateTime executedAt,
                        List<UserDto> participants, List<UserDto> winners) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.numberOfWinners = numberOfWinners;
        this.status = status;
        this.createdAt = createdAt;
        this.executedAt = executedAt;
        this.participants = participants;
        this.winners = winners;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Integer getNumberOfWinners() {
        return numberOfWinners;
    }
    
    public void setNumberOfWinners(Integer numberOfWinners) {
        this.numberOfWinners = numberOfWinners;
    }
    
    public DraftStatus getStatus() {
        return status;
    }
    
    public void setStatus(DraftStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getExecutedAt() {
        return executedAt;
    }
    
    public void setExecutedAt(LocalDateTime executedAt) {
        this.executedAt = executedAt;
    }
    
    public List<UserDto> getParticipants() {
        return participants;
    }
    
    public void setParticipants(List<UserDto> participants) {
        this.participants = participants;
    }
    
    public List<UserDto> getWinners() {
        return winners;
    }
    
    public void setWinners(List<UserDto> winners) {
        this.winners = winners;
    }
}
