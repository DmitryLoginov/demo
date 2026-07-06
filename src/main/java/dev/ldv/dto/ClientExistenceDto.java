package dev.ldv.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import dev.ldv.model.ClientStatus;
import dev.ldv.utils.ObjectMapperUtils;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ClientExistenceDto {
    private boolean exists;
    private UUID clientId;
    private ClientStatus status;

    @Override
    public String toString() {
        try {
            return ObjectMapperUtils.writeValueAsString(this);
        } catch (JsonProcessingException jsonProcessingException) {
            return super.toString();
        }
    }
}