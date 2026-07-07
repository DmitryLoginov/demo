package dev.ldv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientFilter {
    private int page = 0;
    private int size = 20;
    private String lastName;
    private String firstName;
    private String middleName;
    private String status;
    private Long mdmCode;
}