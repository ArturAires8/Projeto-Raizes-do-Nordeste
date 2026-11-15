package com.raizesnordeste.backend.api.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        String error,
        String message,
        LocalDateTime timestamp,
        String path
) {
}
