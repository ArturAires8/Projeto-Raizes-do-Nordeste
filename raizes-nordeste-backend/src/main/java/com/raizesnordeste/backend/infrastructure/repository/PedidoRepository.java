package com.raizesnordeste.backend.infrastructure.repository;

import com.raizesnordeste.backend.domain.enums.CanalPedido;
import com.raizesnordeste.backend.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByCanalPedido(CanalPedido canalPedido);
}
