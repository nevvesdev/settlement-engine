package br.com.nevvesdev.settlementengine.domain.repository;

import br.com.nevvesdev.settlementengine.domain.model.StatusTransacao;
import br.com.nevvesdev.settlementengine.domain.model.Transacao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransacaoRepository {

    Transacao salvar(Transacao transacao);

    Optional<Transacao> buscarPorId(UUID id);

    Optional<Transacao> buscarPorIdExterno(String idExterno);

    List<Transacao> buscarPorStatus(StatusTransacao status);

    boolean existePorIdExterno(String idExterno);
}
