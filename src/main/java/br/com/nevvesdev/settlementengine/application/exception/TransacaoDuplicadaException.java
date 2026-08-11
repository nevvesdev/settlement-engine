package br.com.nevvesdev.settlementengine.application.exception;

public class TransacaoDuplicadaException extends RuntimeException {

    public TransacaoDuplicadaException(String idExterno) {
        super("Já existe uma transação registrada com o id externo: " + idExterno);
    }
}
