package com.vasu.ConsultantClientService.service;

import com.vasu.ConsultantClientService.model.response.ResponseModel;
import com.vasu.ConsultantClientService.model.request.SessionRequest;

public interface SessionService {
    ResponseModel bookSession(SessionRequest sessionRequest);

    ResponseModel getSessionHistory(Long clientId);

    ResponseModel getUpcomingSession(Long consultantId);
}
