package com.lucas.clave.auth.account.model;

import java.util.UUID;

public record SignupResponse(UUID id, String name, String email) {
}
