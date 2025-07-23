package com.example.demo.dto;

import java.util.List;

public class DraftCreateRequest {
    private String title;
    private String description;
    private Integer numberOfWinners;
    private List<Long> participantIds; // Optional: specific participants, or null for all users
    
    // Constructors
    public DraftCreateRequest() {}
    
    public DraftCreateRequest(String title, String description, Integer numberOfWinners, List<Long> participantIds) {
        this.title = title;
        this.description = description;
        this.numberOfWinners = numberOfWinners;
        this.participantIds = participantIds;
    }
    
    // Getters and Setters
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
    
    public List<Long> getParticipantIds() {
        return participantIds;
    }
    
    public void setParticipantIds(List<Long> participantIds) {
        this.participantIds = participantIds;
    }
}
