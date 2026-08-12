package br.com.nevvesdev.settlementengine.infrastructure.persistence;

import br.com.nevvesdev.settlementengine.config.IntegracaoTestBase;
import br.com.nevvesdev.settlementengine.domain.model.StatusTransacao;
import br.com.nevvesdev.settlementengine.domain.model.Transacao;
import br.com.nevvesdev.settlementengine.domain.model.Valor;
import br.com.nevvesdev.settlementengine.domain.repository.TransacaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class TransacaoRepositoryImplTest extends IntegracaoTestBase {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Test
    @DisplayName("Deve salvar e recuperar transação por ID")
    void deveSalvarERecuperarTransacaoPorId() {
        var valor = Valor.emReais(new BigDecimal("150.00"));
        var transacao = Transacao.criar("EXT-001", valor);

        var salva = transacaoRepository.salvar(transacao);
        var encontrada = transacaoRepository.buscarPorId(salva.getId());

        assertThat(encontrada).isPresent();
        assertThat(encontrada.get().getIdExterno()).isEqualTo("EXT-001");
        assertThat(encontrada.get().getValor().getQuantia()).isEqualByComparingTo("150.0000");
        assertThat(encontrada.get().getStatus()).isEqualTo(StatusTransacao.PENDENTE);
    }

    @Test
    @DisplayName("Deve recuperar transação por ID externo")
    void deveRecuperarTransacaoPorIdExterno() {
        var valor = Valor.emReais(new BigDecimal("200.00"));
        var transacao = Transacao.criar("EXT-002", valor);
        transacaoRepository.salvar(transacao);

        var encontrada = transacaoRepository.buscarPorIdExterno("EXT-002");

        assertThat(encontrada).isPresent();
        assertThat(encontrada.get().getIdExterno()).isEqualTo("EXT-002");
    }

    @Test
    @DisplayName("Deve retornar vazio para ID inexistente")
    void deveRetornarVazioParaIdInexistente() {
        var resultado = transacaoRepository.buscarPorId(UUID.randomUUID());
        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Deve verificar existência por ID externo")
    void deveVerificarExistenciaPorIdExterno() {
        var valor = Valor.emReais(new BigDecimal("300.00"));
        var transacao = Transacao.criar("EXT-003", valor);
        transacaoRepository.salvar(transacao);

        assertThat(transacaoRepository.existePorIdExterno("EXT-003")).isTrue();
        assertThat(transacaoRepository.existePorIdExterno("EXT-999")).isFalse();
    }

    @Test
    @DisplayName("Deve listar transações por status")
    void deveListarTransacoesPorStatus() {
        transacaoRepository.salvar(Transacao.criar("EXT-004", Valor.emReais(new BigDecimal("100.00"))));
        transacaoRepository.salvar(Transacao.criar("EXT-005", Valor.emReais(new BigDecimal("200.00"))));

        var pendentes = transacaoRepository.buscarPorStatus(StatusTransacao.PENDENTE);

        assertThat(pendentes).hasSizeGreaterThanOrEqualTo(2);
        assertThat(pendentes).allMatch(t -> t.getStatus() == StatusTransacao.PENDENTE);
    }
}
