package br.com.nevvesdev.settlementengine.application.usecase;

import br.com.nevvesdev.settlementengine.application.exception.TransacaoNaoEncontradaException;
import br.com.nevvesdev.settlementengine.domain.model.StatusTransacao;
import br.com.nevvesdev.settlementengine.domain.model.Transacao;
import br.com.nevvesdev.settlementengine.domain.repository.TransacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ConsultarTransacaoUseCase {

    private final TransacaoRepository transacaoRepository;

    public ConsultarTransacaoUseCase(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    @Transactional(readOnly = true)
    public Transacao buscarPorId(UUID id) {
        return transacaoRepository.buscarPorId(id)
            .orElseThrow(() -> new TransacaoNaoEncontradaException(id));
    }

    @Transactional(readOnly = true)
    public Transacao buscarPorIdExterno(String idExterno) {
        return transacaoRepository.buscarPorIdExterno(idExterno)
            .orElseThrow(() -> new TransacaoNaoEncontradaException(idExterno));
    }

    @Transactional(readOnly = true)
    public List<Transacao> listarPorStatus(StatusTransacao status) {
        return transacaoRepository.buscarPorStatus(status);
    }
}
