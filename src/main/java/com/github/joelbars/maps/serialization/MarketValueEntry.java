package com.github.joelbars.maps.serialization;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

public class MarketValueEntry implements Serializable {

    @NotNull(message = "Ativo é um campo obrigatório")
    private String ativo;

    @NotNull(message = "Data é um campo obrigatório")
    private LocalDate data;

    @NotNull(message = "Valor é um campo obrigatório")
    private Double valor;

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
