package com.vasu.ConsultantClientService.repository;

import com.vasu.ConsultantClientService.entity.PreSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreSessionRepository extends JpaRepository<PreSession, Long> {

    @Query("SELECT p FROM PreSession p WHERE p.session.sessionId = :sessionId")
    List<PreSession> findBySessionIdList(Long sessionId);

}
