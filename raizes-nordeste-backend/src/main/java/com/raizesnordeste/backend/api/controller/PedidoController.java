package com.raizesnordeste.backend.api.controller;

import com.raizesnordeste.backend.api.dto.PedidoRequest;
import com.raizesnordeste.backend.api.dto.PedidoResponse;
import com.raizesnordeste.backend.application.service.PedidoService;
import com.raizesnordeste.backend.domain.enums.CanalPedido;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Endpoints para gerenciamento de pedidos")

public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar um novo pedido")
    public PedidoResponse criarPedido(@RequestBody PedidoRequest request) {
        return pedidoService.criarPedido(request);
    }

    @GetMapping
    @Operation(summary = "Listar pedidos, com filtro opcional por canal")
    public ResponseEntity<List<PedidoResponse>> listar(@RequestParam(required = false) CanalPedido canalPedido) {
        return ResponseEntity.ok(pedidoService.listarPedidos(canalPedido));
    }


}
