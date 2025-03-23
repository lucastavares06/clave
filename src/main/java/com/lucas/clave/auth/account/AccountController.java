package com.lucas.clave.auth.account;

import com.lucas.clave.auth.account.model.LoginRequest;
import com.lucas.clave.auth.account.model.LoginResponse;
import com.lucas.clave.auth.account.model.SignupRequest;
import com.lucas.clave.auth.account.model.SignupResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return accountService.login(request);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignupResponse signup(@Valid @RequestBody SignupRequest request) {
        return accountService.signup(request);
    }
}
