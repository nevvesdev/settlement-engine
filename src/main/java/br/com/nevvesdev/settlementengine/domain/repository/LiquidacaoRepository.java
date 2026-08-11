package br.com.nevvesdev.settlementengine.domain.repository;

import br.com.nevvesdev.settlementengine.domain.model.Liquidacao;
import br.com.nevvesdev.settlementengine.domain.model.StatusLiquidacao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LiquidacaoRepository {

    Liquidacao salvar(Liquidacao liquidacao);

    Optional<Liquidacao> buscarPorId(UUID id);

    List<Liquidacao> buscarPorTransacaoId(UUID transacaoId);

    List<Liquidacao> buscarPorStatus(StatusLiquidacao status);
}
