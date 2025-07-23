package com.example.demo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "drafts")
public class Draft {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column
    private String description;
    
    @Column(nullable = false)
    private Integer numberOfWinners;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column
    private LocalDateTime executedAt;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DraftStatus status;
    
    // Many-to-many relationship with User for winners
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "draft_winners",
        joinColumns = @JoinColumn(name = "draft_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> winners = new ArrayList<>();
    
    // Many-to-many relationship with User for participants
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "draft_participants",
        joinColumns = @JoinColumn(name = "draft_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> participants = new ArrayList<>();
    
    public enum DraftStatus {
        PENDING,    // Draft created but not executed
        EXECUTED,   // Draft has been run and winners selected
        CANCELLED   // Draft was cancelled
    }
    
    // Constructors
    public Draft() {
        this.createdAt = LocalDateTime.now();
        this.status = DraftStatus.PENDING;
    }
    
    public Draft(String title, String description, Integer numberOfWinners) {
        this();
        this.title = title;
        this.description = description;
        this.numberOfWinners = numberOfWinners;
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
    
    public DraftStatus getStatus() {
        return status;
    }
    
    public void setStatus(DraftStatus status) {
        this.status = status;
    }
    
    public List<User> getWinners() {
        return winners;
    }
    
    public void setWinners(List<User> winners) {
        this.winners = winners;
    }
    
    public List<User> getParticipants() {
        return participants;
    }
    
    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }
    
    // Helper methods
    public void addParticipant(User user) {
        if (!participants.contains(user)) {
            participants.add(user);
        }
    }
    
    public void addWinner(User user) {
        if (!winners.contains(user)) {
            winners.add(user);
        }
    }
    
    public boolean isExecuted() {
        return status == DraftStatus.EXECUTED;
    }
    
    public boolean isPending() {
        return status == DraftStatus.PENDING;
    }
}
