package dev.ldv.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import dev.ldv.utils.ObjectMapperUtils;

@Embeddable
@Getter
@Setter
public class Document {
    @Column(name = "document_number", nullable = false)
    private String documentNumber;

    @Column(name = "document_series", nullable = false)
    private String documentSeries;

    @Column(name = "document_type", nullable = false)
    private String documentType;

    @Override
    public String toString() {
        try {
            return ObjectMapperUtils.writeValueAsString(this);
        } catch (JsonProcessingException jsonProcessingException) {
            return super.toString();
        }
    }
}