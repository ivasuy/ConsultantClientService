package com.vasu.ConsultantClientService.model.response;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultantDocumentResponse {
    private String documentName;
    private String documentDescription;
    private Boolean isDocumentUploaded;
    @Lob
    private String documentData;
}
