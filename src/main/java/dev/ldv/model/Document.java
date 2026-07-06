package dev.ldv.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@ToString
public class Document {
    @Column(name = "document_number", nullable = false)
    private String documentNumber;

    @Column(name = "document_series", nullable = false)
    private String documentSeries;

    @Column(name = "document_type", nullable = false)
    private String documentType;
}