package com.github.joelbars.maps.repository;

import com.github.joelbars.maps.exception.InsuficientFundsException;
import com.github.joelbars.maps.model.Account;
import com.github.joelbars.maps.model.AccountEntry;
import com.github.joelbars.maps.model.EntryType;
import org.jboss.resteasy.spi.ApplicationException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class AccountRepositoryService extends GenericRepositoryService<Account, Long> {

    public static final Long DEFAULT_ACCOUNT = 1L;

    @Inject
    AccountEntryRepositoryService entryService;

    public AccountEntry addEntry(AccountEntry entry) {
        Account account = findById(DEFAULT_ACCOUNT);
        Double valor = generateValue(entry.getTipo(), entry.getValor());
        Double balance = calculateChange(entry.getData());
        Double total =  balance + valor;
        if (total < 0) {
            throw new ApplicationException(new InsuficientFundsException());
        }
        entry.account = account;
        this.entryService.persist(entry);

        return entry;
    }

    public Double calculateChange(LocalDate date) {
        LocalDate param = Optional.ofNullable(date).orElse(LocalDate.now());
        return this.entryService.find("data >= ?1", param)
                .stream()
                .map(e -> generateValue(e.getTipo(), e.getValor()))
                .reduce(0.0, Double::sum);
    }

    public Double calculateBalance(LocalDate date) {
        LocalDate param = Optional.ofNullable(date).orElse(LocalDate.now());
        return this.entryService.find("data <= ?1", param)
                .stream()
                .map(e -> generateValue(e.getTipo(), e.getValor()))
                .reduce(0.0, Double::sum);
    }

    public Double calculateBalanceInterval(LocalDate start, LocalDate end) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("end", end);
        return this.entryService.find("data >= :start and data <= :end", params)
                .stream()
                .map(e -> generateValue(e.getTipo(), e.getValor()))
                .reduce(0.0, Double::sum);
    }
    @Override
    protected void validate(Account account) {

    }

    private Double generateValue(EntryType type, Double value) {
        return EntryType.SAIDA.equals(type) ? -value : value;
    }
}
