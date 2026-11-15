package com.raizesnordeste.backend.api.dto;

import com.raizesnordeste.backend.domain.enums.CanalPedido;
import com.raizesnordeste.backend.domain.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoResponse(
        Long id,
        String nomeCliente,
        String nomeUnidade,
        CanalPedido canalPedido,
        StatusPedido status,
        BigDecimal valortotal,
        LocalDateTime criadoEm
) {
}
