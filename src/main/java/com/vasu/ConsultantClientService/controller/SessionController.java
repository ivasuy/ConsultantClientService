package com.vasu.ConsultantClientService.controller;

import com.vasu.ConsultantClientService.model.response.ResponseModel;
import com.vasu.ConsultantClientService.model.request.SessionRequest;
import com.vasu.ConsultantClientService.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PostMapping("/session/book-session")
    public ResponseModel bookSession(@RequestBody SessionRequest sessionRequest){
        return sessionService.bookSession(sessionRequest);
    }

    @GetMapping("/session/get-session-history")
    public ResponseModel getSessionHistory(@RequestParam Long clientId){
        return sessionService.getSessionHistory(clientId);
    }

    @GetMapping("/session/get-upcoming-session")
    public ResponseModel getUpcomingSession(@RequestParam Long consultantId){
        return sessionService.getUpcomingSession(consultantId);
    }
}
