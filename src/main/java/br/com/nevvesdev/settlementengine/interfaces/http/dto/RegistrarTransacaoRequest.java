package br.com.nevvesdev.settlementengine.interfaces.http.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record RegistrarTransacaoRequest(

    @NotBlank(message = "O id externo é obrigatório")
    String idExterno,

    @NotNull(message = "O valor é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
    BigDecimal valor,

    @NotBlank(message = "A moeda é obrigatória")
    @Size(min = 3, max = 3, message = "A moeda deve ter exatamente 3 caracteres (ex: BRL, USD)")
    String moeda
) {}
