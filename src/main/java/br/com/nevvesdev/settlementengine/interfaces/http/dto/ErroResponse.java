package br.com.nevvesdev.settlementengine.interfaces.http.dto;

import java.time.ZonedDateTime;
import java.util.List;

public record ErroResponse(
    int status,
    String erro,
    List<String> mensagens,
    ZonedDateTime timestamp
) {
    public static ErroResponse de(int status, String erro, String mensagem) {
        return new ErroResponse(status, erro, List.of(mensagem), ZonedDateTime.now());
    }

    public static ErroResponse de(int status, String erro, List<String> mensagens) {
        return new ErroResponse(status, erro, mensagens, ZonedDateTime.now());
    }
}
