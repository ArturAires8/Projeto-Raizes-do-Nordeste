package com.raizesnordeste.backend.api.dto;

import com.raizesnordeste.backend.domain.enums.StatusPedido;

public record PagamentoResponse(
        Long pedidoId,
        String statusPagamento,
        StatusPedido statusPedido,
        String mensagem
) {
}
