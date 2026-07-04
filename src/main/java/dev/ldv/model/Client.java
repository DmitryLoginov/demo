package dev.ldv.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import dev.ldv.utils.ObjectMapperUtils;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "client")
@Getter
@Setter
public class Client {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Embedded
    private Name fullName;

    @Column(name = "citizenship")
    private String citizenship;

    @Column(name = "client_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ClientStatus clientStatus;

    @Embedded
    private Document document;

    @Column(name = "mdm_code")
    private Long mdmCode;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        try {
            return ObjectMapperUtils.writeValueAsString(this);
        } catch (JsonProcessingException jsonProcessingException) {
            return super.toString();
        }
    }
}