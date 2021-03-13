package com.github.joelbars.maps.repository;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

public abstract class GenericRepositoryService <T extends PanacheEntityBase, Id> implements PanacheRepositoryBase<T, Id> {

    protected abstract void validate(T t);
}
