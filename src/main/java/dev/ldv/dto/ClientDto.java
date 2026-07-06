package dev.ldv.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;

import dev.ldv.model.ClientStatus;
import dev.ldv.utils.ObjectMapperUtils;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class ClientDto {
    private UUID id;
    private Long mdmCode;
    private String firstName;
    private String lastName;
    private String middleName;
    private String citizenship;
    private ClientStatus clientStatus;
    private String documentNumber;
    private String documentSeries;
    private String documentType;
    private Instant createdAt;
    private Instant updatedAt;
    private boolean hasAccounts = true;

    @Override
    public String toString() {
        try {
            return ObjectMapperUtils.writeValueAsString(this);
        } catch (JsonProcessingException jsonProcessingException) {
            return super.toString();
        }
    }
}