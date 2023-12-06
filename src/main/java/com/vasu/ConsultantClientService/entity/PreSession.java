package com.vasu.ConsultantClientService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DOCUMENT_TABLE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PreSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;
    private String documentName;
    private String documentDescription;
    private Boolean isDocumentUploaded;
    private String documentData;
    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;
}
