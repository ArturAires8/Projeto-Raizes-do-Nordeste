package com.raizesnordeste.backend.api.dto;

import com.raizesnordeste.backend.domain.enums.Role;

public record CadastroRequest(
        String nome,
        String email,
        String senha,
        Role role,
        Boolean consentimentoFidelidade
) {}


