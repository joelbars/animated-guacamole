package com.github.joelbars.maps.resource;

import com.github.joelbars.maps.model.EntryType;
import com.github.joelbars.maps.repository.TransactionRepositoryService;
import com.github.joelbars.maps.serialization.TransactionEntry;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/movimentacao")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionResource {

    @Inject
    protected TransactionRepositoryService service;

    @POST
    @Path("/compra")
    @Transactional
    public Response buy(TransactionEntry entry) {
        entry.setTipo(EntryType.ENTRADA);
        this.service.create(entry);
        return Response.ok().build();
    }

    @POST
    @Path("/venda")
    @Transactional
    public Response sell(TransactionEntry entry) {
        entry.setTipo(EntryType.SAIDA);
        this.service.create(entry);
        return Response.ok().build();
    }
}
