package com.github.joelbars.maps.repository;

import com.github.joelbars.maps.model.Asset;
import com.github.joelbars.maps.model.MarketValue;
import com.github.joelbars.maps.model.MarketValueKey;
import com.github.joelbars.maps.serialization.MarketValueEntry;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.Optional;

@ApplicationScoped
public class MarketValueRepositoryService extends GenericRepositoryService<MarketValue, MarketValueKey> {

    @Override
    protected void validate(MarketValue marketValue) {

    }

    public MarketValue update(MarketValue value) {
        return getEntityManager().merge(value);
    }

    public MarketValueEntry fromEntity(MarketValue value) {
        MarketValueEntry entry = new MarketValueEntry();
        Optional<MarketValueKey> key = Optional.ofNullable(value)
                .map(MarketValue::getKey);
        entry.setAtivo(key.map(MarketValueKey::getAtivo)
                .map(Asset::getNome).orElse(""));
        entry.setData(key.map(MarketValueKey::getData).get());
        entry.setValor(Optional.ofNullable(value.getDayValue()).orElse(BigDecimal.ZERO).doubleValue());
        return entry;
    }

    public MarketValue toEntity(MarketValueEntry entry) {
        MarketValue value = new MarketValue();
        MarketValueKey key = new MarketValueKey();
        Asset ativo = new Asset();
        ativo.setNome(entry.getAtivo());
        key.setAtivo(ativo);
        key.setData(entry.getData());
        value.setDayValue(BigDecimal.valueOf(entry.getValor()));
        value.setKey(key);
        return value;
    }
}
