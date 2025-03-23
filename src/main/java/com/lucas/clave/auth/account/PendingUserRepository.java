package com.lucas.clave.auth.account;

import com.lucas.clave.auth.account.entity.PendingUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PendingUserRepository extends JpaRepository<PendingUser, UUID> {
    Optional<PendingUser> findByToken(String token);
    boolean existsByEmail(String email);
}
