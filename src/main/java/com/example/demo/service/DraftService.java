package com.example.demo.service;

import com.example.demo.dto.DraftCreateRequest;
import com.example.demo.dto.DraftResponse;
import com.example.demo.model.Draft;
import com.example.demo.model.Draft.DraftStatus;
import com.example.demo.model.User;
import com.example.demo.repository.DraftRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DraftService {
    
    @Autowired
    private DraftRepository draftRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    private final SecureRandom secureRandom = new SecureRandom();
    
    // Create a new draft
    public DraftResponse createDraft(DraftCreateRequest request) {
        // Validate request
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Draft title cannot be empty");
        }
        
        if (request.getNumberOfWinners() == null || request.getNumberOfWinners() <= 0) {
            throw new IllegalArgumentException("Number of winners must be greater than 0");
        }
        
        // Create draft entity
        Draft draft = new Draft();
        draft.setTitle(request.getTitle().trim());
        draft.setDescription(request.getDescription() != null ? request.getDescription().trim() : "");
        draft.setNumberOfWinners(request.getNumberOfWinners());
        draft.setStatus(DraftStatus.PENDING);
        draft.setCreatedAt(LocalDateTime.now());
        
        // Add participants
        List<User> participants = new ArrayList<>();
        if (request.getParticipantIds() != null && !request.getParticipantIds().isEmpty()) {
            // Use specified participants
            participants = userRepository.findAllById(request.getParticipantIds());
            if (participants.size() != request.getParticipantIds().size()) {
                throw new IllegalArgumentException("Some participant IDs are invalid");
            }
        } else {
            // Use all users as participants
            participants = userRepository.findAll();
        }
        
        // Validate participant count vs winners
        if (participants.size() < request.getNumberOfWinners()) {
            throw new IllegalArgumentException(
                "Number of winners (" + request.getNumberOfWinners() + 
                ") cannot exceed number of participants (" + participants.size() + ")"
            );
        }
        
        draft.setParticipants(participants);
        
        // Save draft
        Draft savedDraft = draftRepository.save(draft);
        
        return convertToResponse(savedDraft);
    }
    
    // Execute a draft (select winners randomly)
    public DraftResponse executeDraft(Long draftId) {
        Optional<Draft> optionalDraft = draftRepository.findById(draftId);
        if (!optionalDraft.isPresent()) {
            throw new IllegalArgumentException("Draft not found with ID: " + draftId);
        }
        
        Draft draft = optionalDraft.get();
        
        // Check if draft can be executed
        if (draft.getStatus() != DraftStatus.PENDING) {
            throw new IllegalStateException("Draft can only be executed when status is PENDING");
        }
        
        List<User> participants = draft.getParticipants();
        if (participants.isEmpty()) {
            throw new IllegalStateException("No participants found for this draft");
        }
        
        if (participants.size() < draft.getNumberOfWinners()) {
            throw new IllegalStateException(
                "Not enough participants (" + participants.size() + 
                ") for the required number of winners (" + draft.getNumberOfWinners() + ")"
            );
        }
        
        // Select winners randomly
        List<User> winners = selectRandomWinners(participants, draft.getNumberOfWinners());
        draft.setWinners(winners);
        draft.setStatus(DraftStatus.EXECUTED);
        draft.setExecutedAt(LocalDateTime.now());
        
        // Save updated draft
        Draft savedDraft = draftRepository.save(draft);
        
        return convertToResponse(savedDraft);
    }
    
    // Get all drafts
    public List<DraftResponse> getAllDrafts() {
        List<Draft> drafts = draftRepository.findAllByOrderByCreatedAtDesc();
        return drafts.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Get draft by ID
    public DraftResponse getDraftById(Long draftId) {
        Optional<Draft> optionalDraft = draftRepository.findById(draftId);
        if (!optionalDraft.isPresent()) {
            throw new IllegalArgumentException("Draft not found with ID: " + draftId);
        }
        return convertToResponse(optionalDraft.get());
    }
    
    // Get drafts by status
    public List<DraftResponse> getDraftsByStatus(DraftStatus status) {
        List<Draft> drafts = draftRepository.findByStatusOrderByCreatedAtDesc(status);
        return drafts.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Cancel a draft
    public DraftResponse cancelDraft(Long draftId) {
        Optional<Draft> optionalDraft = draftRepository.findById(draftId);
        if (!optionalDraft.isPresent()) {
            throw new IllegalArgumentException("Draft not found with ID: " + draftId);
        }
        
        Draft draft = optionalDraft.get();
        
        if (draft.getStatus() != DraftStatus.PENDING) {
            throw new IllegalStateException("Only pending drafts can be cancelled");
        }
        
        draft.setStatus(DraftStatus.CANCELLED);
        Draft savedDraft = draftRepository.save(draft);
        
        return convertToResponse(savedDraft);
    }
    
    // Get drafts where a user is a participant
    public List<DraftResponse> getDraftsByParticipant(Long userId) {
        List<Draft> drafts = draftRepository.findDraftsByParticipantId(userId);
        return drafts.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Get drafts where a user is a winner
    public List<DraftResponse> getDraftsByWinner(Long userId) {
        List<Draft> drafts = draftRepository.findDraftsByWinnerId(userId);
        return drafts.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Private helper method to select random winners
    private List<User> selectRandomWinners(List<User> participants, int numberOfWinners) {
        if (numberOfWinners >= participants.size()) {
            // If we need all or more participants as winners, return all
            return new ArrayList<>(participants);
        }
        
        // Create a copy of participants list to avoid modifying the original
        List<User> shuffledParticipants = new ArrayList<>(participants);
        
        // Use SecureRandom for cryptographically secure randomness
        Collections.shuffle(shuffledParticipants, secureRandom);
        
        // Return the first N participants after shuffling
        return shuffledParticipants.subList(0, numberOfWinners);
    }
    
    // Convert Draft entity to DraftResponse DTO
    private DraftResponse convertToResponse(Draft draft) {
        List<DraftResponse.UserDto> participantDtos = draft.getParticipants().stream()
                .map(user -> new DraftResponse.UserDto(user.getId(), user.getName(), user.getEmail()))
                .collect(Collectors.toList());
        
        List<DraftResponse.UserDto> winnerDtos = draft.getWinners().stream()
                .map(user -> new DraftResponse.UserDto(user.getId(), user.getName(), user.getEmail()))
                .collect(Collectors.toList());
        
        return new DraftResponse(
                draft.getId(),
                draft.getTitle(),
                draft.getDescription(),
                draft.getNumberOfWinners(),
                draft.getStatus(),
                draft.getCreatedAt(),
                draft.getExecutedAt(),
                participantDtos,
                winnerDtos
        );
    }
}
