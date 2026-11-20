package com.raizesnordeste.backend.api.exception;


import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.core.AuthenticationException;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                "NAO_AUTORIZADO",
                "Credenciais inválidas (e-mail ou senha incorretos).",
                LocalDateTime.now(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error); // Retorna 401 em vez de 500
    }

    // Captura erros de regra de negócio (ex: Estoque insuficiente, E-mail já cadastrado)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                "REGRA_DE_NEGOCIO",
                ex.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error); // Retorna 409 Conflict
    }

    // Captura erros de Acesso Negado (ex: Cliente tentando acessar rota de Admin)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                "ACESSO_NEGADO",
                "Você não tem permissão para acessar este recurso.",
                LocalDateTime.now(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error); // Retorna 403
    }

    // Captura qualquer outro erro inesperado (erro 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                "ERRO_INTERNO",
                "Ocorreu um erro inesperado no servidor.",
                LocalDateTime.now(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

    }
}
