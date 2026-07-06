package dev.ldv.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;

import dev.ldv.model.ClientStatus;
import dev.ldv.utils.ObjectMapperUtils;

import java.util.UUID;

@Getter
@Setter
public class ClientShortDto {
    private UUID id;
    private Long mdmCode;
    private String firstName;
    private String lastName;
    private String middleName;
    private ClientStatus clientStatus;

    @Override
    public String toString() {
        try {
            return ObjectMapperUtils.writeValueAsString(this);
        } catch (JsonProcessingException jsonProcessingException) {
            return super.toString();
        }
    }
}