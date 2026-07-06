package dev.ldv.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientFilter {
    private int page = 0;
    private int size = 20;
    private String lastName;
    private Long mdmCode;
}