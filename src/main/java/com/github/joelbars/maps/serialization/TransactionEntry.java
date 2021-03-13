package com.github.joelbars.maps.serialization;

import com.github.joelbars.maps.model.EntryType;

import javax.json.bind.annotation.JsonbDateFormat;
import java.io.Serializable;
import java.time.LocalDate;

public class TransactionEntry implements Serializable {

    private String ativo;

    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate data;

    private Double quatidade;

    private Double valor;


    private EntryType tipo;

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

    public Double getQuatidade() {
        return quatidade;
    }

    public void setQuatidade(Double quatidade) {
        this.quatidade = quatidade;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public EntryType getTipo() {
        return tipo;
    }

    public void setTipo(EntryType tipo) {
        this.tipo = tipo;
    }
}
