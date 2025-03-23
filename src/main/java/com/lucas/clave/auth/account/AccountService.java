package com.lucas.clave.auth.account;

import com.lucas.clave.auth.account.model.LoginRequest;
import com.lucas.clave.auth.account.model.LoginResponse;
import com.lucas.clave.auth.account.model.SignupRequest;
import com.lucas.clave.auth.account.model.SignupResponse;
import com.lucas.clave.auth.jwt.JwtService;
import com.lucas.clave.auth.user.UserService;
import com.lucas.clave.auth.user.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccountService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AccountService(
            UserService userService,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        return new LoginResponse(
                jwtService.generateToken(request.getEmail())
        );
    }

    public SignupResponse signup(SignupRequest request) {
        if (userService.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado.");
        }

        User createdUser = userService.create(request);

        return new SignupResponse(
                createdUser.getId(),
                createdUser.getName(),
                createdUser.getEmail()
        );
    }
}
