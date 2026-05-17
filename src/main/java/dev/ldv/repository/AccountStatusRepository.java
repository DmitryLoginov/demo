package dev.ldv.repository;

import dev.ldv.model.AccountStatus;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountStatusRepository extends JpaRepository<AccountStatus, Integer> {
}