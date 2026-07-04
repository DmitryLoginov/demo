package dev.ldv.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Document {
    @Column(name = "document_number", nullable = false)
    private String documentNumber;

    @Column(name = "document_series", nullable = false)
    private String documentSeries;

    @Column(name = "document_type", nullable = false)
    private String documentType;
}