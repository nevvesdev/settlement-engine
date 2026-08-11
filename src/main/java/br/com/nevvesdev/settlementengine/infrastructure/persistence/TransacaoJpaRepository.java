package br.com.nevvesdev.settlementengine.infrastructure.persistence;

import br.com.nevvesdev.settlementengine.domain.model.StatusTransacao;
import br.com.nevvesdev.settlementengine.domain.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface TransacaoJpaRepository extends JpaRepository<Transacao, UUID> {

    Optional<Transacao> findByIdExterno(String idExterno);

    List<Transacao> findByStatus(StatusTransacao status);

    boolean existsByIdExterno(String idExterno);
}
