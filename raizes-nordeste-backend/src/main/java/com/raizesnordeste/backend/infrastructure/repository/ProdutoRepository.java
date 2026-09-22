package com.raizesnordeste.backend.infrastructure.repository;

import com.raizesnordeste.backend.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {


}
