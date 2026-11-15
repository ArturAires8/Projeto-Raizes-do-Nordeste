package com.raizesnordeste.backend.api.dto;

import com.raizesnordeste.backend.domain.enums.CanalPedido;

import java.util.List;

public record PedidoRequest(
        Long clienteId,
        Long unidadeId,
        CanalPedido canalPedido,
        List<ItemPedidoRequest> itens
) {
}

