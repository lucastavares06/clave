package com.lucas.clave.auth.email.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailConfirmationSend implements EmailSend {

    private final String to;
    private final String subject;
    private final String body;
}
