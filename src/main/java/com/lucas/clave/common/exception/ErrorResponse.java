package com.lucas.clave.common.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class ErrorResponse {
    private int status;
    private String message;
    private Instant timestamp;
}
