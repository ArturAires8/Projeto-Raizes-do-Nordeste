package com.raizesnordeste.backend.application.service;

import com.raizesnordeste.backend.api.dto.CadastroRequest;
import com.raizesnordeste.backend.api.dto.LoginRequest;
import com.raizesnordeste.backend.api.dto.LoginResponse;
import com.raizesnordeste.backend.domain.model.Usuario;
import com.raizesnordeste.backend.infrastructure.repository.UsuarioRepository;
import com.raizesnordeste.backend.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    public LoginResponse cadastrar(CadastroRequest request) {
        if (usuarioRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        Usuario usuario = Usuario.builder()
                .nome(request.nome())
                .email(request.email())
                .senhaHash(passwordEncoder.encode(request.senha()))
                .role(request.role())
                .consentimentoFidelidade(request.consentimentoFidelidade() != null && request.consentimentoFidelidade())
                .build();

        usuarioRepository.save(usuario);

        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getEmail());
        String token = jwtService.gerarToken(userDetails);
        return new LoginResponse(token, usuario.getRole().name());
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.senha())
        );

        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getEmail());
        String token = jwtService.gerarToken(userDetails);
        return new LoginResponse(token, usuario.getRole().name());
    }
}



