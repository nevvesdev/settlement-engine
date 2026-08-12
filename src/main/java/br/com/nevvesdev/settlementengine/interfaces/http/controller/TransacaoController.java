package br.com.nevvesdev.settlementengine.interfaces.http.controller;

import br.com.nevvesdev.settlementengine.application.usecase.ConsultarTransacaoUseCase;
import br.com.nevvesdev.settlementengine.application.usecase.RegistrarTransacaoUseCase;
import br.com.nevvesdev.settlementengine.domain.model.StatusTransacao;
import br.com.nevvesdev.settlementengine.interfaces.http.dto.RegistrarTransacaoRequest;
import br.com.nevvesdev.settlementengine.interfaces.http.dto.TransacaoResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transacoes")
public class TransacaoController {

    private final RegistrarTransacaoUseCase registrarTransacaoUseCase;
    private final ConsultarTransacaoUseCase consultarTransacaoUseCase;

    public TransacaoController(
        RegistrarTransacaoUseCase registrarTransacaoUseCase,
        ConsultarTransacaoUseCase consultarTransacaoUseCase) {
        this.registrarTransacaoUseCase = registrarTransacaoUseCase;
        this.consultarTransacaoUseCase = consultarTransacaoUseCase;
    }

    @PostMapping
    public ResponseEntity<TransacaoResponse> registrar(
        @Valid @RequestBody RegistrarTransacaoRequest request) {
        var transacao = registrarTransacaoUseCase.executar(
            request.idExterno(),
            request.valor(),
            request.moeda()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(TransacaoResponse.de(transacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransacaoResponse> buscarPorId(@PathVariable UUID id) {
        var transacao = consultarTransacaoUseCase.buscarPorId(id);
        return ResponseEntity.ok(TransacaoResponse.de(transacao));
    }

    @GetMapping("/externo/{idExterno}")
    public ResponseEntity<TransacaoResponse> buscarPorIdExterno(@PathVariable String idExterno) {
        var transacao = consultarTransacaoUseCase.buscarPorIdExterno(idExterno);
        return ResponseEntity.ok(TransacaoResponse.de(transacao));
    }

    @GetMapping
    public ResponseEntity<List<TransacaoResponse>> listarPorStatus(
        @RequestParam(required = false) StatusTransacao status) {
        if (status == null) {
            status = StatusTransacao.PENDENTE;
        }
        var transacoes = consultarTransacaoUseCase.listarPorStatus(status);
        return ResponseEntity.ok(transacoes.stream().map(TransacaoResponse::de).toList());
    }
}
