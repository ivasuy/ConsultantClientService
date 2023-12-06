package com.vasu.ConsultantClientService.service.implementation;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.vasu.ConsultantClientService.entity.PreSession;
import com.vasu.ConsultantClientService.entity.Session;
import com.vasu.ConsultantClientService.model.request.CreatePreSessionRequest;
import com.vasu.ConsultantClientService.model.request.PreSessionRequest;
import com.vasu.ConsultantClientService.model.response.ClientDocumentResponse;
import com.vasu.ConsultantClientService.model.response.ConsultantDocumentResponse;
import com.vasu.ConsultantClientService.model.response.ResponseModel;
import com.vasu.ConsultantClientService.repository.PreSessionRepository;
import com.vasu.ConsultantClientService.repository.SessionRepository;
import com.vasu.ConsultantClientService.service.PreSessionService;
import com.vasu.ConsultantClientService.utils.HashUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@Log4j2
public class PreSessionServiceImpl implements PreSessionService {

    @Autowired
    private PreSessionRepository preSessionRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Value("${application.bucket.name}")
    private String bucketName;
    @Autowired
    private AmazonS3 s3Client;

    @Override
    public ResponseModel createDocument(CreatePreSessionRequest createPreSessionRequest) {
        List<PreSessionRequest> documents = createPreSessionRequest.getDocuments();

        for (PreSessionRequest preSessionRequest : documents) {
            Long sessionId = HashUtils.decode(preSessionRequest.getSessionId());
            Session session = sessionRepository.findById(sessionId)
                    .orElseThrow(() -> new RuntimeException("Session not found for id: " + sessionId));

            PreSession preSession = PreSession.builder()
                    .documentName(preSessionRequest.getDocumentName())
                    .documentDescription(preSessionRequest.getDocumentDescription())
                    .isDocumentUploaded(false)
                    .session(session)
                    .build();
            preSessionRepository.save(preSession);
        }

        return ResponseModel.builder()
                .status(1L)
                .message("Documents Needed For THe Session Created SuccessFully")
                .data(null)
                .build();
    }

    @Override
    public ResponseModel getDocumentForClient(String sessionId) {
        Long currSessionId = HashUtils.decode(sessionId);
        sessionRepository.findById(currSessionId)
                .orElseThrow(() -> new RuntimeException("Session not found for id: " + sessionId));
        List<PreSession> preSessions = preSessionRepository.findBySessionIdList(currSessionId);

        List<ClientDocumentResponse> clientDocumentResponses = preSessions.stream()
                .map(preSession -> {
                    ClientDocumentResponse clientDocumentResponse = new ClientDocumentResponse();
                    clientDocumentResponse.setDocumentName(preSession.getDocumentName());
                    clientDocumentResponse.setDocumentDescription(preSession.getDocumentDescription());
                    return clientDocumentResponse;
                })
                .toList();

        return ResponseModel.builder()
                .status(1L)
                .message("Documents to upload for Session")
                .data(clientDocumentResponses)
                .build();
    }

    @Override
    public ResponseModel uploadDocument(String sessionId, List<MultipartFile> files) {
        Long currSessionId = HashUtils.decode(sessionId);
        sessionRepository.findById(currSessionId)
                .orElseThrow(() -> new RuntimeException("Session not found for id: " + sessionId));

        for (MultipartFile file : files) {
            File fileObj = convertMultiPartFileToFile(file);
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String currFileName = file.getOriginalFilename();
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
            fileObj.delete();
            String s3Url = s3Client.getUrl(bucketName, fileName).toString();

            List<PreSession> preSessions = preSessionRepository.findBySessionIdList(currSessionId);
            for(PreSession preSession : preSessions){
                String trimmedDocumentName = preSession.getDocumentName().replaceAll("\\.odt$", "");
                if(preSession.getDocumentName().equalsIgnoreCase(trimmedDocumentName)){
                    preSession.setDocumentData(s3Url);
                    preSession.setIsDocumentUploaded(true);
                    preSessionRepository.save(preSession);
                }
            }
        }

        return ResponseModel.builder()
                .status(1L)
                .message("PreSession Documents Uploaded Successfully")
                .data(null)
                .build();
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }

    @Override
    public ResponseModel getDocumentForConsultant(String sessionId) {
        Long currSessionId = HashUtils.decode(sessionId);
        sessionRepository.findById(currSessionId)
                .orElseThrow(() -> new RuntimeException("Session not found for id: " + sessionId));
        List<PreSession> preSessions = preSessionRepository.findBySessionIdList(currSessionId);

        List<ConsultantDocumentResponse> consultantDocumentResponses = preSessions.stream()
                .map(preSession -> {
                    ConsultantDocumentResponse consultantDocumentResponse = new ConsultantDocumentResponse();
                    consultantDocumentResponse.setDocumentName(preSession.getDocumentName());
                    consultantDocumentResponse.setDocumentDescription(preSession.getDocumentDescription());
                    consultantDocumentResponse.setIsDocumentUploaded(preSession.getIsDocumentUploaded());
                    consultantDocumentResponse.setDocumentData(preSession.getDocumentData());
                    return consultantDocumentResponse;
                })
                .toList();

        return ResponseModel.builder()
                .status(1L)
                .message("PreSession Documents for client")
                .data(consultantDocumentResponses)
                .build();
    }
}
