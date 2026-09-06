package dev.ldv.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import dev.ldv.api.model.ClientStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import dev.ldv.utils.ObjectMapperUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
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
    private ClientStatus status;

    @Embedded
    private Document document;

    @Column(name = "mdm_code", unique = true)
    private Long mdmCode;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            timezone = "UTC"
    )
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            timezone = "UTC"
    )
    private Instant updatedAt;

    @Override
    public String toString() {
        try {
            return ObjectMapperUtils.writeValueAsString(this);
        } catch (JsonProcessingException jsonProcessingException) {
            return super.toString();
        }
    }
}