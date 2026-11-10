package com.raizesnordeste.backend.infrastructure.repository;

import com.raizesnordeste.backend.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {


}
