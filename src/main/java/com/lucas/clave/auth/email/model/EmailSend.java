package com.lucas.clave.auth.email.model;

public interface EmailSend {
    String getTo();
    String getSubject();
    String getBody();
}
