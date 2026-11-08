package com.raizesnordeste.backend.api.dto;

public record LoginResponse(String token, String tipo, String role) {

    public LoginResponse(String token, String role) {
        this(token, "Bearer", role);
    }
}
