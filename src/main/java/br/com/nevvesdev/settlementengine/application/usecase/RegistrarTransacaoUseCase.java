package br.com.nevvesdev.settlementengine.application.usecase;

import br.com.nevvesdev.settlementengine.application.exception.TransacaoDuplicadaException;
import br.com.nevvesdev.settlementengine.domain.model.Transacao;
import br.com.nevvesdev.settlementengine.domain.model.Valor;
import br.com.nevvesdev.settlementengine.domain.repository.TransacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class RegistrarTransacaoUseCase {

    private final TransacaoRepository transacaoRepository;

    public RegistrarTransacaoUseCase(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    @Transactional
    public Transacao executar(String idExterno, BigDecimal quantia, String moeda) {
        if (transacaoRepository.existePorIdExterno(idExterno)) {
            throw new TransacaoDuplicadaException(idExterno);
        }

        var valor = Valor.de(quantia, moeda);
        var transacao = Transacao.criar(idExterno, valor);

        return transacaoRepository.salvar(transacao);
    }
}
