package com.github.joelbars.maps.repository;

import com.github.joelbars.maps.model.*;
import com.github.joelbars.maps.serialization.TransactionEntry;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;

@ApplicationScoped
public class TransactionRepositoryService extends GenericRepositoryService<Transaction, Long> {

    @Inject
    AssetRepositoryService assetService;

    @Inject
    AccountRepositoryService accountService;

    public void create(TransactionEntry entry) {
        Transaction tx = new Transaction();
        Account account = this.accountService.findById(1L);
        Asset asset = this.assetService.findById(entry.getAtivo());

        EntryType accEntryType = EntryType.ENTRADA.equals(entry.getTipo()) ? EntryType.SAIDA : EntryType.ENTRADA;
        AccountEntry accountEntry = new AccountEntry();
        accountEntry.account = account;
        accountEntry.setTipo(accEntryType);
        accountEntry.setDescricao(String.format("%s de ativo %s", EntryType.SAIDA.equals(accEntryType) ? "Venda" : "Compra", entry.getAtivo()));
        accountEntry.setData(LocalDate.now());

        AssetEntry in = new AssetEntry();
        in.setAsset(asset);
        in.setValue(entry.getValor());

        AssetEntry out = new AssetEntry();
        out.setAccount(account);
        out.setValue(entry.getValor());

        if (EntryType.ENTRADA.equals(entry.getTipo())) {
            AssetEntry tmp = in;
            in = out;
            out = tmp;
        }

        tx.setIn(in);
        tx.setOut(out);

        this.accountService.addEntry(accountEntry);
        this.persist(tx);
    }
    @Override
    protected void validate(Transaction transaction) {

    }
}
