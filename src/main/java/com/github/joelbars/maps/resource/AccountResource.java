package com.github.joelbars.maps.resource;

import com.github.joelbars.maps.model.Account;
import com.github.joelbars.maps.model.AccountEntry;
import com.github.joelbars.maps.model.EntryType;
import com.github.joelbars.maps.repository.AccountRepositoryService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;


@Path("/contacorrente")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource extends GenericResource<Account, Long> {

    @Inject
    protected AccountRepositoryService service;

    @GET
    @Path("/saldo")
    public Response accountBalance(
            @QueryParam("data") LocalDate data,
            @QueryParam("inicio") LocalDate inicio,
            @QueryParam("fim") LocalDate fim) {
        Double balance = 0.0;
        if (data != null) {
            balance = this.service.calculateBalance(data);
        } else if (inicio != null && fim != null) {
            if (inicio.isAfter(fim)) {
                throw new BadRequestException("Intervalo inválido (inicio após o fim)");
            }
            balance = this.service.calculateBalanceInterval(inicio, fim);
        } else {
            throw new BadRequestException("É necessário definir \"data\" ou intervalo \"inicio\", \"fim\"");
        }
        return Response.ok(String.format("{\"saldo\":%.2f}", balance).replaceAll(",", ".")).build();
    }

    @POST
    @Path("/credito")
    @Transactional
    public Response credit(AccountEntry entry) {
        entry.setTipo(EntryType.ENTRADA);
        this.service.addEntry(entry);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @POST
    @Path("/debito")
    @Transactional
    public Response debit(AccountEntry entry) {
        entry.setTipo(EntryType.SAIDA);
        this.service.addEntry(entry);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
