package br.com.nevvesdev.settlementengine.infrastructure.persistence;

import br.com.nevvesdev.settlementengine.domain.model.Liquidacao;
import br.com.nevvesdev.settlementengine.domain.model.StatusLiquidacao;
import br.com.nevvesdev.settlementengine.domain.repository.LiquidacaoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class LiquidacaoRepositoryImpl implements LiquidacaoRepository {

    private final LiquidacaoJpaRepository jpaRepository;

    public LiquidacaoRepositoryImpl(LiquidacaoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Liquidacao salvar(Liquidacao liquidacao) {
        return jpaRepository.save(liquidacao);
    }

    @Override
    public Optional<Liquidacao> buscarPorId(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Liquidacao> buscarPorTransacaoId(UUID transacaoId) {
        return jpaRepository.findByTransacaoId(transacaoId);
    }

    @Override
    public List<Liquidacao> buscarPorStatus(StatusLiquidacao status) {
        return jpaRepository.findByStatus(status);
    }
}
