package com.lucas.clave.auth.account;

import com.lucas.clave.auth.account.entity.PendingUser;
import com.lucas.clave.auth.account.model.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PendingUserService {

    private final PendingUserRepository pendingUserRepository;
    private final PasswordEncoder passwordEncoder;

    public PendingUser create(SignupRequest request) {
        String token = UUID.randomUUID().toString();

        PendingUser pendingUser = PendingUser.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .token(token)
                .expiresAt(LocalDateTime.now().plusHours(24))
                .build();

        return pendingUserRepository.save(pendingUser);
    }

    public boolean existsByEmail(String email) {
        return pendingUserRepository.existsByEmail(email);
    }

    public PendingUser findByToken(String token) {
        return pendingUserRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido ou expirado"));
    }

    public void delete(PendingUser pendingUser) {
        pendingUserRepository.delete(pendingUser);
    }
}
