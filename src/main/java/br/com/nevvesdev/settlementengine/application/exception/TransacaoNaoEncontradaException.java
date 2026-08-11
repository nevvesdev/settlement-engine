package br.com.nevvesdev.settlementengine.application.exception;

import java.util.UUID;

public class TransacaoNaoEncontradaException extends RuntimeException {

    public TransacaoNaoEncontradaException(UUID id) {
        super("Transação não encontrada com id: " + id);
    }

    public TransacaoNaoEncontradaException(String idExterno) {
        super("Transação não encontrada com id externo: " + idExterno);
    }
}
