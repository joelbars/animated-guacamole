package com.github.joelbars.maps.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Transaction extends PanacheEntity {

    @ManyToOne
    private AssetEntry in;

    @ManyToOne
    private AssetEntry out;

    public AssetEntry getIn() {
        return in;
    }

    public void setIn(AssetEntry in) {
        this.in = in;
    }

    public AssetEntry getOut() {
        return out;
    }

    public void setOut(AssetEntry out) {
        this.out = out;
    }
}
