package br.com.nevvesdev.settlementengine.domain.model;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "liquidacoes", schema = "settlement")
public class Liquidacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transacao_id", nullable = false)
    private Transacao transacao;

    @Column(name = "liquidado_em")
    private ZonedDateTime liquidadoEm;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private StatusLiquidacao status;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private ZonedDateTime criadoEm;

    protected Liquidacao() {}

    private Liquidacao(Transacao transacao) {
        this.transacao = transacao;
        this.status = StatusLiquidacao.PENDENTE;
        this.criadoEm = ZonedDateTime.now();
    }

    public static Liquidacao criar(Transacao transacao) {
        if (transacao == null) {
            throw new IllegalArgumentException("A transação não pode ser nula");
        }
        return new Liquidacao(transacao);
    }

    public void iniciarProcessamento() {
        if (this.status != StatusLiquidacao.PENDENTE) {
            throw new IllegalStateException(
                "Liquidação só pode iniciar processamento a partir do status PENDENTE. Status atual: " + this.status
            );
        }
        this.status = StatusLiquidacao.EM_PROCESSAMENTO;
    }

    public void concluir() {
        if (this.status != StatusLiquidacao.EM_PROCESSAMENTO) {
            throw new IllegalStateException(
                "Liquidação só pode ser concluída a partir do status EM_PROCESSAMENTO. Status atual: " + this.status
            );
        }
        this.status = StatusLiquidacao.CONCLUIDA;
        this.liquidadoEm = ZonedDateTime.now();
    }

    public void marcarComoFalha() {
        this.status = StatusLiquidacao.FALHA;
    }

    public UUID getId() { return id; }
    public Transacao getTransacao() { return transacao; }
    public ZonedDateTime getLiquidadoEm() { return liquidadoEm; }
    public StatusLiquidacao getStatus() { return status; }
    public ZonedDateTime getCriadoEm() { return criadoEm; }
}
