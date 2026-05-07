package dev.ldv.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "client")
@Getter
@Setter
@ToString
public class Client {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "citizenship")
    private String citizenship;

    @Column(name = "client_type")
    private String clientType;

    @Column(name = "document_number", nullable = false)
    private String documentNumber;

    @Column(name = "document_series", nullable = false)
    private String documentSeries;

    @Column(name = "document_type", nullable = false)
    private String documentType;

    @Column(name = "mdm_code")
    private Long mdmCode;
}