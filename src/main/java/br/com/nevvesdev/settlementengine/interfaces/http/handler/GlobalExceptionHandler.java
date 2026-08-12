package br.com.nevvesdev.settlementengine.interfaces.http.handler;

import br.com.nevvesdev.settlementengine.application.exception.TransacaoDuplicadaException;
import br.com.nevvesdev.settlementengine.application.exception.TransacaoEmEstadoInvalidoException;
import br.com.nevvesdev.settlementengine.application.exception.TransacaoNaoEncontradaException;
import br.com.nevvesdev.settlementengine.interfaces.http.dto.ErroResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TransacaoNaoEncontradaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResponse handleTransacaoNaoEncontrada(TransacaoNaoEncontradaException ex) {
        return ErroResponse.de(HttpStatus.NOT_FOUND.value(), "Não Encontrado", ex.getMessage());
    }

    @ExceptionHandler(TransacaoDuplicadaException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResponse handleTransacaoDuplicada(TransacaoDuplicadaException ex) {
        return ErroResponse.de(HttpStatus.CONFLICT.value(), "Conflito", ex.getMessage());
    }

    @ExceptionHandler(TransacaoEmEstadoInvalidoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResponse handleEstadoInvalido(TransacaoEmEstadoInvalidoException ex) {
        return ErroResponse.de(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Estado Inválido", ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResponse handleArgumentoInvalido(IllegalArgumentException ex) {
        return ErroResponse.de(HttpStatus.BAD_REQUEST.value(), "Requisição Inválida", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResponse handleValidacao(MethodArgumentNotValidException ex) {
        List<String> mensagens = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage)
            .toList();
        return ErroResponse.de(HttpStatus.BAD_REQUEST.value(), "Erro de Validação", mensagens);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResponse handleErroInterno(Exception ex) {
        return ErroResponse.de(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Erro Interno",
            "Ocorreu um erro inesperado. Por favor, tente novamente."
        );
    }
}
