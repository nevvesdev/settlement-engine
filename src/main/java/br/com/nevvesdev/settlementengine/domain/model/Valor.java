package br.com.nevvesdev.settlementengine.domain.model;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Embeddable
public class Valor {

    private BigDecimal quantia;
    private String moeda;

    protected Valor() {}

    private Valor(BigDecimal quantia, String moeda) {
        this.quantia = quantia;
        this.moeda = moeda;
    }

    public static Valor de(BigDecimal quantia, String moeda) {
        validar(quantia, moeda);
        return new Valor(quantia.setScale(4, RoundingMode.HALF_UP), moeda);
    }

    public static Valor emReais(BigDecimal quantia) {
        return de(quantia, "BRL");
    }

    private static void validar(BigDecimal quantia, String moeda) {
        if (quantia == null || quantia.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("A quantia deve ser maior que zero");
        }
        if (moeda == null || moeda.isBlank() || moeda.length() != 3) {
            throw new IllegalArgumentException("Moeda inválida: deve ter exatamente 3 caracteres (ex: BRL, USD)");
        }
    }

    public BigDecimal getQuantia() {
        return quantia;
    }

    public String getMoeda() {
        return moeda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Valor valor)) return false;
        return Objects.equals(quantia, valor.quantia) &&
            Objects.equals(moeda, valor.moeda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantia, moeda);
    }

    @Override
    public String toString() {
        return quantia + " " + moeda;
    }
}
