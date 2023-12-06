package com.vasu.ConsultantClientService.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDocumentResponse {
    private String documentName;
    private String documentDescription;
}
