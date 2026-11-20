package com.raizesnordeste.backend.application.service;

import com.raizesnordeste.backend.api.dto.ItemPedidoRequest;
import com.raizesnordeste.backend.api.dto.PedidoRequest;
import com.raizesnordeste.backend.api.dto.PedidoResponse;
import com.raizesnordeste.backend.domain.model.Auditoria;
import com.raizesnordeste.backend.domain.enums.CanalPedido;
import com.raizesnordeste.backend.domain.enums.StatusPedido;
import com.raizesnordeste.backend.domain.model.*;
import com.raizesnordeste.backend.infrastructure.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final UnidadeRepository unidadeRepository;
    private final ProdutoRepository produtoRepository;
    private final EstoqueRepository estoqueRepository;
    private final AuditoriaRepository auditoriaRepository;

    @Transactional
    public PedidoResponse criarPedido(PedidoRequest request) {
        // 1. Validação do Canal do Pedido
        if (request.canalPedido() == null) {
            throw new IllegalArgumentException("O canal do pedido é obrigatório.");
        }

        // 2. Buscar Cliente e Unidade
        Usuario cliente = usuarioRepository.findById(request.clienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));
        Unidade unidade = unidadeRepository.findById(request.unidadeId())
                .orElseThrow(() -> new IllegalArgumentException("Unidade não encontrada."));

        // 3. Criar o Pedido base
        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .unidade(unidade)
                .canalPedido(request.canalPedido())
                .status(StatusPedido.AGUARDANDO_PAGAMENTO)
                .valorTotal(BigDecimal.ZERO)
                .build();

        BigDecimal valorTotal = BigDecimal.ZERO;
        List<ItemPedido> itens = new ArrayList<>();

        // 4. Processar os Itens e verificar Estoque
        for (ItemPedidoRequest itemReq : request.itens()) {
            Produto produto = produtoRepository.findById(itemReq.produtoId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: ID " + itemReq.produtoId()));

            // Verifica o estoque na unidade específica
            Estoque estoque = estoqueRepository.findByUnidadeIdAndProdutoId(unidade.getId(), produto.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não disponível nesta unidade."));

            if (estoque.getQuantidade() < itemReq.quantidade()) {
                throw new IllegalArgumentException("Estoque insuficiente para o produto: " + produto.getNome());
            }

            // Dá baixa no estoque
            estoque.setQuantidade(estoque.getQuantidade() - itemReq.quantidade());
            estoqueRepository.save(estoque);

            // Cria o item do pedido
            ItemPedido item = ItemPedido.builder()
                    .pedido(pedido)
                    .produto(produto)
                    .quantidade(itemReq.quantidade())
                    .precoUnitario(produto.getPreco())
                    .build();
            itens.add(item);

            // Soma ao valor total
            valorTotal = valorTotal.add(produto.getPreco().multiply(new BigDecimal(itemReq.quantidade())));
        }

        // 5. Finaliza e salva o pedido
        pedido.setItens(itens);
        pedido.setValorTotal(valorTotal);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        // 6. Auditoria
        auditoriaRepository.save(Auditoria.builder()
                .acao("CRIACAO_PEDIDO")
                .entidade("Pedido")
                .entidadeId(pedidoSalvo.getId())
                .detalhes("Pedido criado via canal " + pedidoSalvo.getCanalPedido())
                .build());

        // 7. Retorna a resposta
        return new PedidoResponse(
                pedidoSalvo.getId(),
                pedidoSalvo.getCliente().getNome(),
                pedidoSalvo.getUnidade().getNome(),
                pedidoSalvo.getCanalPedido(),
                pedidoSalvo.getStatus(),
                pedidoSalvo.getValorTotal(),
                pedidoSalvo.getCriadoEm()
        );
    }

    // Método de listagem
    public List<PedidoResponse> listarPedidos(CanalPedido canalPedido) {
        List<Pedido> pedidos;
        if (canalPedido != null) {
            pedidos = pedidoRepository.findByCanalPedido(canalPedido);
        } else {
            pedidos = pedidoRepository.findAll();
        }

        return pedidos.stream().map(p -> new PedidoResponse(
                p.getId(),
                p.getCliente().getNome(),
                p.getUnidade().getNome(),
                p.getCanalPedido(),
                p.getStatus(),
                p.getValorTotal(),
                p.getCriadoEm()
        )).toList();
    }
}




