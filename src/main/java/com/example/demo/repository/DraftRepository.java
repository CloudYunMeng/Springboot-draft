package com.example.demo.repository;

import com.example.demo.model.Draft;
import com.example.demo.model.Draft.DraftStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DraftRepository extends JpaRepository<Draft, Long> {
    
    // Find all drafts by status
    List<Draft> findByStatus(DraftStatus status);
    
    // Find all drafts ordered by creation date (newest first)
    List<Draft> findAllByOrderByCreatedAtDesc();
    
    // Find drafts by status ordered by creation date
    List<Draft> findByStatusOrderByCreatedAtDesc(DraftStatus status);
    
    // Count drafts by status
    long countByStatus(DraftStatus status);
    
    // Find drafts where a specific user is a participant
    @Query("SELECT d FROM Draft d JOIN d.participants p WHERE p.id = :userId")
    List<Draft> findDraftsByParticipantId(@Param("userId") Long userId);
    
    // Find drafts where a specific user is a winner
    @Query("SELECT d FROM Draft d JOIN d.winners w WHERE w.id = :userId")
    List<Draft> findDraftsByWinnerId(@Param("userId") Long userId);
    
    // Find all pending drafts with participant count
    @Query("SELECT d FROM Draft d WHERE d.status = 'PENDING' AND SIZE(d.participants) > 0")
    List<Draft> findPendingDraftsWithParticipants();
}
