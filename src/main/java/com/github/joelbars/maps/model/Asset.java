package com.github.joelbars.maps.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "asset")
public class Asset extends PanacheEntityBase {

    @Id
    @NotBlank(message="O nome do ativo é obrigatório")
    private String nome;

    @NotNull(message = "É necessário especificar o tipo")
    @Enumerated(EnumType.STRING)
    @Column(name = "asset_type")
    private AssetType tipo;

    @NotNull(message = "A data de emissão é obrigatória")
    @JsonbDateFormat("yyyy-MM-dd")
    @Column(columnDefinition = "DATE", name = "data_emissao")
    private LocalDate dataEmissao;

    @NotNull(message = "A data de vencimento é obrigatória")
    @JsonbDateFormat("yyyy-MM-dd")
    @Column(columnDefinition = "DATE", name = "data_vencimento")
    private LocalDate dataVencimento;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public AssetType getTipo() {
        return tipo;
    }

    public void setTipo(AssetType tipo) {
        this.tipo = tipo;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

}
