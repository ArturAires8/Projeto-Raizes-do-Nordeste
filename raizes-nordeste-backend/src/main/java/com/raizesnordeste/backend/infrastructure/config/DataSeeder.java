package com.raizesnordeste.backend.infrastructure.config;


import com.raizesnordeste.backend.domain.model.Estoque;
import com.raizesnordeste.backend.domain.model.Produto;
import com.raizesnordeste.backend.domain.model.Unidade;
import com.raizesnordeste.backend.infrastructure.repository.EstoqueRepository;
import com.raizesnordeste.backend.infrastructure.repository.ProdutoRepository;
import com.raizesnordeste.backend.infrastructure.repository.UnidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UnidadeRepository unidadeRepository;
    private final ProdutoRepository produtoRepository;
    private final EstoqueRepository estoqueRepository;

    @Override
    public void run(String... args) throws Exception {
        if (unidadeRepository.count() == 0) {
            Unidade unidade = unidadeRepository.save(Unidade.builder()
                    .nome("Raízes Recife")
                    .cidade("Recife")
                    .endereco("Av. Boa Viagem")
                    .ativa(true)
                    .build());

            Produto produto = produtoRepository.save(Produto.builder()
                    .nome("Cuscuz com Ovo")
                    .descricao("Cuscuz nordestino com ovo frito")
                    .preco(new BigDecimal("15.00"))
                    .ativo(true)
                    .build());

            estoqueRepository.save(Estoque.builder()
                    .unidade(unidade)
                    .produto(produto)
                    .quantidade(10) // Temos 10 cuscuzes em estoque
                    .build());

            System.out.println("✅ Banco populado com dados iniciais para testes!");
        }
    }
}




