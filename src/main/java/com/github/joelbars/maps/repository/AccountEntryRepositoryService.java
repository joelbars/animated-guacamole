package com.github.joelbars.maps.repository;

import com.github.joelbars.maps.model.AccountEntry;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AccountEntryRepositoryService extends GenericRepositoryService<AccountEntry, Long> {

    @Override
    protected void validate(AccountEntry accountEntry) {

    }
}
