package br.com.nevvesdev.settlementengine.infrastructure.persistence;

import br.com.nevvesdev.settlementengine.domain.model.StatusTransacao;
import br.com.nevvesdev.settlementengine.domain.model.Transacao;
import br.com.nevvesdev.settlementengine.domain.repository.TransacaoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TransacaoRepositoryImpl implements TransacaoRepository {

    private final TransacaoJpaRepository jpaRepository;

    public TransacaoRepositoryImpl(TransacaoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Transacao salvar(Transacao transacao) {
        return jpaRepository.save(transacao);
    }

    @Override
    public Optional<Transacao> buscarPorId(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<Transacao> buscarPorIdExterno(String idExterno) {
        return jpaRepository.findByIdExterno(idExterno);
    }

    @Override
    public List<Transacao> buscarPorStatus(StatusTransacao status) {
        return jpaRepository.findByStatus(status);
    }

    @Override
    public boolean existePorIdExterno(String idExterno) {
        return jpaRepository.existsByIdExterno(idExterno);
    }
}
