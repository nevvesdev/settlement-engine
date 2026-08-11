package br.com.nevvesdev.settlementengine.application.usecase;

import br.com.nevvesdev.settlementengine.application.exception.TransacaoEmEstadoInvalidoException;
import br.com.nevvesdev.settlementengine.application.exception.TransacaoNaoEncontradaException;
import br.com.nevvesdev.settlementengine.domain.model.Liquidacao;
import br.com.nevvesdev.settlementengine.domain.model.StatusTransacao;
import br.com.nevvesdev.settlementengine.domain.model.Transacao;
import br.com.nevvesdev.settlementengine.domain.repository.LiquidacaoRepository;
import br.com.nevvesdev.settlementengine.domain.repository.TransacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProcessarLiquidacaoUseCase {

    private final TransacaoRepository transacaoRepository;
    private final LiquidacaoRepository liquidacaoRepository;

    public ProcessarLiquidacaoUseCase(
        TransacaoRepository transacaoRepository,
        LiquidacaoRepository liquidacaoRepository) {
        this.transacaoRepository = transacaoRepository;
        this.liquidacaoRepository = liquidacaoRepository;
    }

    @Transactional
    public Liquidacao executar(UUID transacaoId) {
        Transacao transacao = transacaoRepository.buscarPorId(transacaoId)
            .orElseThrow(() -> new TransacaoNaoEncontradaException(transacaoId));

        if (transacao.getStatus() != StatusTransacao.PENDENTE) {
            throw new TransacaoEmEstadoInvalidoException(transacao.getStatus(), "processar liquidação");
        }

        transacao.iniciarProcessamento();
        transacaoRepository.salvar(transacao);

        var liquidacao = Liquidacao.criar(transacao);
        liquidacao.iniciarProcessamento();
        liquidacaoRepository.salvar(liquidacao);

        try {
            liquidacao.concluir();
            transacao.liquidar();

            liquidacaoRepository.salvar(liquidacao);
            transacaoRepository.salvar(transacao);

            return liquidacao;
        } catch (Exception e) {
            liquidacao.marcarComoFalha();
            transacao.marcarComoFalha();

            liquidacaoRepository.salvar(liquidacao);
            transacaoRepository.salvar(transacao);

            throw new RuntimeException("Falha ao processar liquidação da transação: " + transacaoId, e);
        }
    }
}
