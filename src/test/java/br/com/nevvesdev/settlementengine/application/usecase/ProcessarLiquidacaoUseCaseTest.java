package br.com.nevvesdev.settlementengine.application.usecase;

import br.com.nevvesdev.settlementengine.application.exception.TransacaoEmEstadoInvalidoException;
import br.com.nevvesdev.settlementengine.application.exception.TransacaoNaoEncontradaException;
import br.com.nevvesdev.settlementengine.config.IntegracaoTestBase;
import br.com.nevvesdev.settlementengine.domain.model.StatusLiquidacao;
import br.com.nevvesdev.settlementengine.domain.model.StatusTransacao;
import br.com.nevvesdev.settlementengine.domain.repository.TransacaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
class ProcessarLiquidacaoUseCaseTest extends IntegracaoTestBase {

    @Autowired
    private ProcessarLiquidacaoUseCase processarLiquidacaoUseCase;

    @Autowired
    private RegistrarTransacaoUseCase registrarTransacaoUseCase;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Test
    @DisplayName("Deve processar liquidação com sucesso")
    void deveProcessarLiquidacaoComSucesso() {
        var transacao = registrarTransacaoUseCase.executar("EXT-LIQ-001", new BigDecimal("750.00"), "BRL");

        var liquidacao = processarLiquidacaoUseCase.executar(transacao.getId());

        assertThat(liquidacao.getId()).isNotNull();
        assertThat(liquidacao.getStatus()).isEqualTo(StatusLiquidacao.CONCLUIDA);
        assertThat(liquidacao.getLiquidadoEm()).isNotNull();

        var transacaoAtualizada = transacaoRepository.buscarPorId(transacao.getId());
        assertThat(transacaoAtualizada).isPresent();
        assertThat(transacaoAtualizada.get().getStatus()).isEqualTo(StatusTransacao.LIQUIDADA);
    }

    @Test
    @DisplayName("Deve lançar exceção para transação inexistente")
    void deveLancarExcecaoParaTransacaoInexistente() {
        var idInexistente = UUID.randomUUID();

        assertThatThrownBy(() -> processarLiquidacaoUseCase.executar(idInexistente))
            .isInstanceOf(TransacaoNaoEncontradaException.class)
            .hasMessageContaining(idInexistente.toString());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar liquidar transação já liquidada")
    void deveLancarExcecaoParaTransacaoJaLiquidada() {
        var transacao = registrarTransacaoUseCase.executar("EXT-LIQ-002", new BigDecimal("300.00"), "BRL");
        processarLiquidacaoUseCase.executar(transacao.getId());

        assertThatThrownBy(() -> processarLiquidacaoUseCase.executar(transacao.getId()))
            .isInstanceOf(TransacaoEmEstadoInvalidoException.class);
    }
}
