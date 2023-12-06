package com.vasu.ConsultantClientService.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PreSessionRequest {
    private String documentName;
    private String documentDescription;
    private String sessionId;
}
