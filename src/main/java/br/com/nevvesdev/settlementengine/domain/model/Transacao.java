package br.com.nevvesdev.settlementengine.domain.model;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "transacoes", schema = "settlement")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "id_externo", nullable = false, unique = true)
    private String idExterno;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "quantia", column = @Column(name = "valor", nullable = false)),
        @AttributeOverride(name = "moeda",   column = @Column(name = "moeda", nullable = false, length = 3))
    })
    private Valor valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private StatusTransacao status;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private ZonedDateTime criadoEm;

    @Column(name = "atualizado_em", nullable = false)
    private ZonedDateTime atualizadoEm;

    protected Transacao() {}

    private Transacao(String idExterno, Valor valor) {
        this.idExterno = idExterno;
        this.valor = valor;
        this.status = StatusTransacao.PENDENTE;
        this.criadoEm = ZonedDateTime.now();
        this.atualizadoEm = ZonedDateTime.now();
    }

    public static Transacao criar(String idExterno, Valor valor) {
        if (idExterno == null || idExterno.isBlank()) {
            throw new IllegalArgumentException("O ID externo da transação não pode ser vazio");
        }
        return new Transacao(idExterno, valor);
    }

    public void iniciarProcessamento() {
        if (this.status != StatusTransacao.PENDENTE) {
            throw new IllegalStateException(
                "Transação só pode iniciar processamento a partir do status PENDENTE. Status atual: " + this.status
            );
        }
        this.status = StatusTransacao.EM_PROCESSAMENTO;
        this.atualizadoEm = ZonedDateTime.now();
    }

    public void liquidar() {
        if (this.status != StatusTransacao.EM_PROCESSAMENTO) {
            throw new IllegalStateException(
                "Transação só pode ser liquidada a partir do status EM_PROCESSAMENTO. Status atual: " + this.status
            );
        }
        this.status = StatusTransacao.LIQUIDADA;
        this.atualizadoEm = ZonedDateTime.now();
    }

    public void cancelar() {
        if (this.status == StatusTransacao.LIQUIDADA) {
            throw new IllegalStateException("Não é possível cancelar uma transação já liquidada");
        }
        this.status = StatusTransacao.CANCELADA;
        this.atualizadoEm = ZonedDateTime.now();
    }

    public void marcarComoFalha() {
        this.status = StatusTransacao.FALHA;
        this.atualizadoEm = ZonedDateTime.now();
    }

    public UUID getId() { return id; }
    public String getIdExterno() { return idExterno; }
    public Valor getValor() { return valor; }
    public StatusTransacao getStatus() { return status; }
    public ZonedDateTime getCriadoEm() { return criadoEm; }
    public ZonedDateTime getAtualizadoEm() { return atualizadoEm; }
}
