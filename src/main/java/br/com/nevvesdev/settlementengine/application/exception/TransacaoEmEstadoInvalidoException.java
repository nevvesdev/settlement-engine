package br.com.nevvesdev.settlementengine.application.exception;

import br.com.nevvesdev.settlementengine.domain.model.StatusTransacao;

public class TransacaoEmEstadoInvalidoException extends RuntimeException {

    public TransacaoEmEstadoInvalidoException(StatusTransacao statusAtual, String operacao) {
        super("Não é possível executar '" + operacao + "' com a transação no status: " + statusAtual);
    }
}
