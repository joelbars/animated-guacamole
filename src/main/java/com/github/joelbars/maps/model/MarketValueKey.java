package com.github.joelbars.maps.model;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
public class MarketValueKey implements Serializable {

    @ManyToOne
    @JoinColumn(name = "asset_id")
    @NotNull(message = "Ativo é um campo obrigatório")
    private Asset ativo;

    @JsonbDateFormat("yyyy-MM-dd")
    @Column(columnDefinition = "DATE", name = "value_date")
    @NotNull(message = "A data é obrigatória")
    private LocalDate data;

    public Asset getAtivo() {
        return ativo;
    }

    public void setAtivo(Asset ativo) {
        this.ativo = ativo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
