package dev.ldv.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import dev.ldv.api.model.ClientStatus;
import dev.ldv.utils.ObjectMapperUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientFilter {
    private int page = 0;
    private int size = 20;
    private String firstName;
    private String lastName;
    private String middleName;
    private ClientStatus status;
    private Long mdmCode;

    @Override
    public String toString() {
        try {
            return ObjectMapperUtils.writeValueAsString(this);
        } catch (JsonProcessingException jsonProcessingException) {
            return super.toString();
        }
    }
}