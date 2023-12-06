package com.vasu.ConsultantClientService.service;

import com.vasu.ConsultantClientService.model.request.CreatePreSessionRequest;
import com.vasu.ConsultantClientService.model.response.ResponseModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PreSessionService {
    ResponseModel createDocument(CreatePreSessionRequest createPreSessionRequest);

    ResponseModel getDocumentForClient(String sessionId);

    ResponseModel uploadDocument(String sessionId, List<MultipartFile> files);

    ResponseModel getDocumentForConsultant(String sessionId);
}
