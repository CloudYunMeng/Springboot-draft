package com.example.demo.controller;

import com.example.demo.dto.DraftCreateRequest;
import com.example.demo.dto.DraftResponse;
import com.example.demo.model.Draft.DraftStatus;
import com.example.demo.service.DraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drafts")
@CrossOrigin(origins = "*")
public class DraftController {
    
    @Autowired
    private DraftService draftService;
    
    // Create a new draft
    @PostMapping
    public ResponseEntity<?> createDraft(@RequestBody DraftCreateRequest request) {
        try {
            DraftResponse response = draftService.createDraft(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating draft: " + e.getMessage());
        }
    }
    
    // Get all drafts
    @GetMapping
    public ResponseEntity<List<DraftResponse>> getAllDrafts() {
        try {
            List<DraftResponse> drafts = draftService.getAllDrafts();
            return ResponseEntity.ok(drafts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Get draft by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getDraftById(@PathVariable Long id) {
        try {
            DraftResponse response = draftService.getDraftById(id);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving draft: " + e.getMessage());
        }
    }
    
    // Get drafts by status
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getDraftsByStatus(@PathVariable String status) {
        try {
            DraftStatus draftStatus = DraftStatus.valueOf(status.toUpperCase());
            List<DraftResponse> drafts = draftService.getDraftsByStatus(draftStatus);
            return ResponseEntity.ok(drafts);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body("Invalid status. Valid values are: PENDING, EXECUTED, CANCELLED");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving drafts: " + e.getMessage());
        }
    }
    
    // Execute a draft (select winners)
    @PostMapping("/{id}/execute")
    public ResponseEntity<?> executeDraft(@PathVariable Long id) {
        try {
            DraftResponse response = draftService.executeDraft(id);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error executing draft: " + e.getMessage());
        }
    }
    
    // Cancel a draft
    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelDraft(@PathVariable Long id) {
        try {
            DraftResponse response = draftService.cancelDraft(id);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error cancelling draft: " + e.getMessage());
        }
    }
    
    // Get drafts where a user is a participant
    @GetMapping("/participant/{userId}")
    public ResponseEntity<?> getDraftsByParticipant(@PathVariable Long userId) {
        try {
            List<DraftResponse> drafts = draftService.getDraftsByParticipant(userId);
            return ResponseEntity.ok(drafts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving participant drafts: " + e.getMessage());
        }
    }
    
    // Get drafts where a user is a winner
    @GetMapping("/winner/{userId}")
    public ResponseEntity<?> getDraftsByWinner(@PathVariable Long userId) {
        try {
            List<DraftResponse> drafts = draftService.getDraftsByWinner(userId);
            return ResponseEntity.ok(drafts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving winner drafts: " + e.getMessage());
        }
    }
    
    // Health check endpoint
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Draft service is running");
    }
}
