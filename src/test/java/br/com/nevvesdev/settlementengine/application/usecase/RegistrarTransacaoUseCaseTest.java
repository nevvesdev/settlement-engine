package br.com.nevvesdev.settlementengine.application.usecase;

import br.com.nevvesdev.settlementengine.application.exception.TransacaoDuplicadaException;
import br.com.nevvesdev.settlementengine.config.IntegracaoTestBase;
import br.com.nevvesdev.settlementengine.domain.model.StatusTransacao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
class RegistrarTransacaoUseCaseTest extends IntegracaoTestBase {

    @Autowired
    private RegistrarTransacaoUseCase registrarTransacaoUseCase;

    @Test
    @DisplayName("Deve registrar transação com sucesso")
    void deveRegistrarTransacaoComSucesso() {
        var transacao = registrarTransacaoUseCase.executar("EXT-REG-001", new BigDecimal("500.00"), "BRL");

        assertThat(transacao.getId()).isNotNull();
        assertThat(transacao.getIdExterno()).isEqualTo("EXT-REG-001");
        assertThat(transacao.getValor().getQuantia()).isEqualByComparingTo("500.0000");
        assertThat(transacao.getValor().getMoeda()).isEqualTo("BRL");
        assertThat(transacao.getStatus()).isEqualTo(StatusTransacao.PENDENTE);
        assertThat(transacao.getCriadoEm()).isNotNull();
    }

    @Test
    @DisplayName("Deve lançar exceção ao registrar transação com ID externo duplicado")
    void deveLancarExcecaoParaIdExternoDuplicado() {
        registrarTransacaoUseCase.executar("EXT-REG-DUP", new BigDecimal("100.00"), "BRL");

        assertThatThrownBy(() ->
            registrarTransacaoUseCase.executar("EXT-REG-DUP", new BigDecimal("200.00"), "BRL")
        ).isInstanceOf(TransacaoDuplicadaException.class)
            .hasMessageContaining("EXT-REG-DUP");
    }

    @Test
    @DisplayName("Deve lançar exceção para valor inválido")
    void deveLancarExcecaoParaValorInvalido() {
        assertThatThrownBy(() ->
            registrarTransacaoUseCase.executar("EXT-REG-003", BigDecimal.ZERO, "BRL")
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("maior que zero");
    }

    @Test
    @DisplayName("Deve lançar exceção para moeda inválida")
    void deveLancarExcecaoParaMoedaInvalida() {
        assertThatThrownBy(() ->
            registrarTransacaoUseCase.executar("EXT-REG-004", new BigDecimal("100.00"), "BR")
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Moeda inválida");
    }
}
