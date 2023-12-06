package com.vasu.ConsultantClientService.repository;

import com.vasu.ConsultantClientService.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository  extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.session.sessionId = :sessionId")
    Review findBySessionId(Long sessionId);
}
