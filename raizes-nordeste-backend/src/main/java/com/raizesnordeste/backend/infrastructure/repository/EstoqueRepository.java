package com.raizesnordeste.backend.infrastructure.repository;

import com.raizesnordeste.backend.domain.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    // Busca o estoque de um produto específico em uma unidade específica
    Optional<Estoque> findByUnidadeIdAndProdutoId(Long unidadeId, Long produtoId);
}
