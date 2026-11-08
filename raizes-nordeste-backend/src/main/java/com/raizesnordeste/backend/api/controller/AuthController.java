package com.raizesnordeste.backend.api.controller;

import com.raizesnordeste.backend.api.dto.CadastroRequest;
import com.raizesnordeste.backend.api.dto.LoginRequest;
import com.raizesnordeste.backend.api.dto.LoginResponse;
import com.raizesnordeste.backend.application.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints de cadastro e login")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Fazer login e receber token JWT")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar novo usuário")
    public LoginResponse cadastrar(@RequestBody CadastroRequest request) {
        return authService.cadastrar(request);
    }
}

