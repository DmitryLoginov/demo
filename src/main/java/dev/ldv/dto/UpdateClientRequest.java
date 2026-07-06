package dev.ldv.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import dev.ldv.utils.ObjectMapperUtils;

@Getter
@Setter
public class UpdateClientRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String middleName;
    @NotBlank
    private String citizenship;
    @NotBlank
    private String documentNumber;
    @NotBlank
    private String documentSeries;
    @NotBlank
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