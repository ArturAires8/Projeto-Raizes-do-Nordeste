package com.raizesnordeste.backend.api.controller;

import com.raizesnordeste.backend.api.dto.PagamentoRequest;
import com.raizesnordeste.backend.api.dto.PagamentoResponse;
import com.raizesnordeste.backend.application.service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
@Tag(name = "Pagamentos", description = "Simulação de Gateway de Pagamento")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @PostMapping("/mock")
    @Operation(summary = "Processar pagamento mock (Aprovar ou Recusar)")
    public ResponseEntity<PagamentoResponse> processarPagamento(@RequestBody PagamentoRequest request) {
        return ResponseEntity.ok(pagamentoService.processarPagamento(request));
    }
}
