package com.lucas.clave.auth.account;

import com.lucas.clave.auth.account.model.*;
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
    public SignupInitResponse signup(@Valid @RequestBody SignupRequest request) {
        return accountService.signup(request);
    }

    @GetMapping("/confirm")
    public SignupResponse confirmAccount(@RequestParam String token) {
        return accountService.confirmAccount(token);
    }
}
