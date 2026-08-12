package br.com.nevvesdev.settlementengine.interfaces.http.controller;

import br.com.nevvesdev.settlementengine.application.usecase.ProcessarLiquidacaoUseCase;
import br.com.nevvesdev.settlementengine.interfaces.http.dto.LiquidacaoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/liquidacoes")
public class LiquidacaoController {

    private final ProcessarLiquidacaoUseCase processarLiquidacaoUseCase;

    public LiquidacaoController(ProcessarLiquidacaoUseCase processarLiquidacaoUseCase) {
        this.processarLiquidacaoUseCase = processarLiquidacaoUseCase;
    }

    @PostMapping("/transacoes/{transacaoId}/processar")
    public ResponseEntity<LiquidacaoResponse> processar(@PathVariable UUID transacaoId) {
        var liquidacao = processarLiquidacaoUseCase.executar(transacaoId);
        return ResponseEntity.ok(LiquidacaoResponse.de(liquidacao));
    }
}
