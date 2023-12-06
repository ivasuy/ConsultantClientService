package com.vasu.ConsultantClientService.repository;

import com.vasu.ConsultantClientService.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByClientId(Long clientId);

    List<Session> findByConsultantId(Long consultantId);

    boolean existsBySessionDateAndSessionStartTimeAndSessionEndTimeAndClientIdAndConsultantId(Date sessionDate, Date sessionStartTime, Date sessionEndTime, Long clientId, Long consultantId);

    boolean existsBySessionDateAndSessionStartTimeAndSessionEndTimeAndConsultantId(Date sessionDate, Date sessionStartTime, Date sessionEndTime, Long consultantId);
}
