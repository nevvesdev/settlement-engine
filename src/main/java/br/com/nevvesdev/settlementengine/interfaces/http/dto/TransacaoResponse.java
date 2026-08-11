package br.com.nevvesdev.settlementengine.interfaces.http.dto;

import br.com.nevvesdev.settlementengine.domain.model.StatusTransacao;
import br.com.nevvesdev.settlementengine.domain.model.Transacao;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

public record TransacaoResponse(
    UUID id,
    String idExterno,
    BigDecimal valor,
    String moeda,
    StatusTransacao status,
    ZonedDateTime criadoEm,
    ZonedDateTime atualizadoEm
) {
    public static TransacaoResponse de(Transacao transacao) {
        return new TransacaoResponse(
            transacao.getId(),
            transacao.getIdExterno(),
            transacao.getValor().getQuantia(),
            transacao.getValor().getMoeda(),
            transacao.getStatus(),
            transacao.getCriadoEm(),
            transacao.getAtualizadoEm()
        );
    }
}
