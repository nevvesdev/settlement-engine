package br.com.nevvesdev.settlementengine.interfaces.http.dto;

import br.com.nevvesdev.settlementengine.domain.model.Liquidacao;
import br.com.nevvesdev.settlementengine.domain.model.StatusLiquidacao;

import java.time.ZonedDateTime;
import java.util.UUID;

public record LiquidacaoResponse(
    UUID id,
    UUID transacaoId,
    StatusLiquidacao status,
    ZonedDateTime liquidadoEm,
    ZonedDateTime criadoEm
) {
    public static LiquidacaoResponse de(Liquidacao liquidacao) {
        return new LiquidacaoResponse(
            liquidacao.getId(),
            liquidacao.getTransacao().getId(),
            liquidacao.getStatus(),
            liquidacao.getLiquidadoEm(),
            liquidacao.getCriadoEm()
        );
    }
}
