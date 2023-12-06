package com.vasu.ConsultantClientService.controller;

import com.vasu.ConsultantClientService.model.request.CreatePreSessionRequest;
import com.vasu.ConsultantClientService.model.response.ResponseModel;
import com.vasu.ConsultantClientService.service.PreSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PreSessionController {

    @Autowired
    private PreSessionService preSessionService;

    @PostMapping("/preSession/create-documents")
    public ResponseModel createDocument(@RequestBody CreatePreSessionRequest createPreSessionRequest){
        return preSessionService.createDocument(createPreSessionRequest);
    }

    @GetMapping("/preSession/get-documents-to-upload")
    public ResponseModel getDocumentForClient(@RequestParam String sessionId){
        return preSessionService.getDocumentForClient(sessionId);
    }

    @PostMapping(value = "/preSession/upload-documents",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseModel uploadDocument(
            @RequestParam String sessionId,
            @RequestPart(value = "files") List<MultipartFile> files) {
        return preSessionService.uploadDocument(sessionId, files);
    }

    @GetMapping("/preSession/get-documents")
    public ResponseModel getDocumentForConsultant(@RequestParam String sessionId) {
        return preSessionService.getDocumentForConsultant(sessionId);
    }

}
