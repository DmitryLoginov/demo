package dev.ldv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.ldv.model.Account;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    boolean existsByClientId(UUID clientId);

    boolean existsByClientIdAndStatusName(UUID clientId, String statusName);
}