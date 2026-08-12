package br.com.nevvesdev.settlementengine.infrastructure.persistence;

import br.com.nevvesdev.settlementengine.domain.model.Liquidacao;
import br.com.nevvesdev.settlementengine.domain.model.StatusLiquidacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LiquidacaoJpaRepository extends JpaRepository<Liquidacao, UUID> {

    List<Liquidacao> findByTransacaoId(UUID transacaoId);

    List<Liquidacao> findByStatus(StatusLiquidacao status);
}
