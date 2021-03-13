package com.github.joelbars.maps.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "market_value")
public class MarketValue extends PanacheEntityBase {

    @EmbeddedId
    private MarketValueKey key;

    @NotNull(message = "O valor é obrigatório")
    @Column(scale = 8, name = "day_value")
    private BigDecimal dayValue;

    public MarketValueKey getKey() {
        return key;
    }

    public void setKey(MarketValueKey key) {
        this.key = key;
    }

    public BigDecimal getDayValue() {
        return dayValue;
    }

    public void setDayValue(BigDecimal dayValue) {
        this.dayValue = dayValue;
    }
}
