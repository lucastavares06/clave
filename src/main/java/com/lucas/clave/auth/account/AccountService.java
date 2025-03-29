package com.lucas.clave.auth.account;

import com.lucas.clave.auth.account.model.*;
import com.lucas.clave.auth.email.EmailProperties;
import com.lucas.clave.auth.email.EmailService;
import com.lucas.clave.auth.email.model.EmailConfirmationSend;
import com.lucas.clave.auth.jwt.JwtService;
import com.lucas.clave.auth.user.UserService;
import com.lucas.clave.auth.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserService userService;
    private final PendingUserService pendingUserService;
    private final EmailService emailService;
    private final EmailProperties emailProperties;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String accessToken = jwtService.generateAccessToken(request.getEmail());
        String refreshToken = jwtService.generateRefreshToken(request.getEmail());

        return new LoginResponse(accessToken, refreshToken);
    }

    public SignupInitResponse signup(SignupRequest request) {
        if (userService.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado.");
        }
        if (pendingUserService.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este e-mail já está aguardando confirmação.");
        }

        var pendingUser = pendingUserService.create(request);
        var confirmUrl = emailProperties.getBaseConfirmUrl() + pendingUser.getToken();

        String body = String.format(
                "Olá %s,\n\nClique no link abaixo para confirmar seu cadastro:\n\n%s",
                pendingUser.getName(),
                confirmUrl
        );

        emailService.send(new EmailConfirmationSend(
                pendingUser.getEmail(),
                "Confirmação de cadastro - Clave",
                body
        ));

        return new SignupInitResponse("E-mail de confirmação enviado. Verifique sua caixa de entrada.");
    }

    public SignupResponse confirmAccount(String token) {
        var pendingUser = pendingUserService.findByToken(token);

        if (pendingUser.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.GONE, "Token expirado.");
        }

        SignupRequest signupRequest = new SignupRequest(
                pendingUser.getName(),
                pendingUser.getEmail(),
                pendingUser.getPassword(),
                pendingUser.getRole()
        );

        User user = userService.create(signupRequest);
        pendingUserService.delete(pendingUser);

        return new SignupResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public LoginResponse refreshToken(TokenRefreshRequest request) {
        String refreshToken = request.getRefreshToken();

        if (!jwtService.isRefreshTokenValid(refreshToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido ou expirado");
        }

        String username = jwtService.extractUsername(refreshToken);

        userService.findByEmail(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        String newAccessToken = jwtService.generateAccessToken(username);
        String newRefreshToken = jwtService.generateRefreshToken(username);

        return new LoginResponse(newAccessToken, newRefreshToken);
    }
}
