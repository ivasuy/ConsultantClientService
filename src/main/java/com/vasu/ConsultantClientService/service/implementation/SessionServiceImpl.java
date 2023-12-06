package com.vasu.ConsultantClientService.service.implementation;

import com.vasu.ConsultantClientService.entity.Session;
import com.vasu.ConsultantClientService.model.response.ResponseModel;
import com.vasu.ConsultantClientService.model.request.SessionRequest;
import com.vasu.ConsultantClientService.repository.SessionRepository;
import com.vasu.ConsultantClientService.service.SessionService;
import com.vasu.ConsultantClientService.utils.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public ResponseModel bookSession(SessionRequest sessionRequest) {
        if (sessionRepository.
                existsBySessionDateAndSessionStartTimeAndSessionEndTimeAndClientIdAndConsultantId(
                sessionRequest.getSessionDate(),
                sessionRequest.getSessionStartTime(),
                sessionRequest.getSessionEndTime(),
                sessionRequest.getClientId(),
                sessionRequest.getConsultantId())) return ResponseModel.builder()
                    .status(2L)
                    .message("Session already exists")
                    .data(null)
                    .build();

        if (sessionRepository.
                existsBySessionDateAndSessionStartTimeAndSessionEndTimeAndConsultantId(
                sessionRequest.getSessionDate(),
                sessionRequest.getSessionStartTime(),
                sessionRequest.getSessionEndTime(),
                sessionRequest.getConsultantId())) return ResponseModel.builder()
                    .status(2L)
                    .message("Session is already booked for this Time")
                    .data(null)
                    .build();

        Session session = Session.builder()
                .clientId(sessionRequest.getClientId())
                .consultantId(sessionRequest.getConsultantId())
                .sessionDate(sessionRequest.getSessionDate())
                .sessionStartTime(sessionRequest.getSessionStartTime())
                .sessionEndTime(sessionRequest.getSessionEndTime())
                .build();
        sessionRepository.save(session);

        String sessionHashId = HashUtils.encode(session.getSessionId());
        return ResponseModel.builder()
                .status(1L)
                .message("Session Created SuccessFully Your Session Id is: " + sessionHashId)
                .data(null)
                .build();
    }

    @Override
    public ResponseModel getSessionHistory(Long clientId) {
        List<Session> sessions = sessionRepository.findByClientId(clientId);
        List<Session> validSessions = sessions.stream()
                .filter(session -> {
                    Date sessionEndTime = session.getSessionEndTime();
                    Date curr = Date.from(Instant.now());
                    return sessionEndTime.before(curr);
                })
                .collect(Collectors.toList());

        if (validSessions.isEmpty()) return ResponseModel.builder()
                .status(2L)
                .message("No Session History Found")
                .data(null)
                .build();

        return ResponseModel.builder()
                .status(1L)
                .message("Session History")
                .data(validSessions)
                .build();
    }

    @Override
    public ResponseModel getUpcomingSession(Long consultantId) {
        List<Session> sessions = sessionRepository.findByConsultantId(consultantId);
        List<Session> upcomingSessions = sessions.stream()
                .filter(session -> {
                    Date sessionStartTime = session.getSessionStartTime();
                    Date curr = Date.from(Instant.now());
                    return sessionStartTime.after(curr);
                })
                .collect(Collectors.toList());

        if (upcomingSessions.isEmpty()) return ResponseModel.builder()
                    .status(2L)
                    .message("No Upcoming Sessions Found")
                    .data(null)
                    .build();

        return ResponseModel.builder()
                .status(1L)
                .message("Upcoming Sessions")
                .data(upcomingSessions)
                .build();
    }
}
