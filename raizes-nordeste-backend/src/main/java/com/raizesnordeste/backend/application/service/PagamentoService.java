package com.raizesnordeste.backend.application.service;


import com.raizesnordeste.backend.api.dto.PagamentoRequest;
import com.raizesnordeste.backend.api.dto.PagamentoResponse;
import com.raizesnordeste.backend.domain.enums.StatusPedido;
import com.raizesnordeste.backend.domain.model.Pagamento;
import com.raizesnordeste.backend.domain.model.Pedido;
import com.raizesnordeste.backend.infrastructure.repository.PagamentoRepository;
import com.raizesnordeste.backend.infrastructure.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PedidoRepository pedidoRepository;
    private final PagamentoRepository pagamentoRepository;

    @Transactional
    public PagamentoResponse processarPagamento(PagamentoRequest request) {
        // 1. Busca o pedido
        Pedido pedido = pedidoRepository.findById(request.pedidoId())
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado."));

        // 2. Verifica se o pedido já foi pago
        if (pedido.getStatus() == StatusPedido.PAGO) {
            throw new IllegalArgumentException("Este pedido já foi pago.");
        }

        // 3. Define se vai aprovar ou recusar baseado no request (se for nulo, assume falso)
        boolean recusar = request.simularRecusa() != null && request.simularRecusa();

        String statusPagamento;
        String mensagem;
        String transacaoId = UUID.randomUUID().toString(); // Simula um ID de transação do gateway

        if (recusar) {
            statusPagamento = "RECUSADO";
            mensagem = "Pagamento recusado pelo gateway mock.";
            // O status do pedido continua AGUARDANDO_PAGAMENTO
        } else {
            statusPagamento = "APROVADO";
            mensagem = "Pagamento aprovado com sucesso.";
            pedido.setStatus(StatusPedido.PAGO); // Atualiza o status do pedido
            pedidoRepository.save(pedido);
        }

        // 4. Registra o pagamento no banco
        Pagamento pagamento = Pagamento.builder()
                .pedido(pedido)
                .status(statusPagamento)
                .valor(pedido.getValorTotal())
                .transacaoId(transacaoId)
                .build();
        pagamentoRepository.save(pagamento);

        // 5. Retorna a resposta
        return new PagamentoResponse(
                pedido.getId(),
                statusPagamento,
                pedido.getStatus(),
                mensagem
        );
    }

}
