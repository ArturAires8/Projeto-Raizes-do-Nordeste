package com.raizesnordeste.backend.infrastructure.repository;

import com.raizesnordeste.backend.domain.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {


}
