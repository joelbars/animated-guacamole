package com.github.joelbars.maps.repository;

import com.github.joelbars.maps.exception.InvalidAssetIntervalException;
import com.github.joelbars.maps.model.Asset;
import org.jboss.resteasy.spi.ApplicationException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AssetRepositoryService extends GenericRepositoryService<Asset, String> {

    @Inject
    TransactionRepositoryService transactionService;

    @Override
    protected void validate(Asset asset) {
        if (asset.getDataEmissao().isAfter(asset.getDataVencimento())) {
            throw new ApplicationException(new InvalidAssetIntervalException());
        }
    }
}
