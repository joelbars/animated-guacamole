package com.github.joelbars.maps.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "account_entry")
public class AccountEntry extends PanacheEntity {

    @Column(scale = 8)
    private Double valor;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private EntryType tipo;

    @JsonbDateFormat("yyyy-MM-dd")
    @Column(columnDefinition = "DATE")
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "account_id")
    public Account account;

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public EntryType getTipo() {
        return tipo;
    }

    public void setTipo(EntryType tipo) {
        this.tipo = tipo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
