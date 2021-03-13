package com.github.joelbars.maps.resource;

import com.github.joelbars.maps.model.MarketValue;
import com.github.joelbars.maps.repository.MarketValueRepositoryService;
import com.github.joelbars.maps.serialization.MarketValueEntry;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/valormercado")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MarketValueResource {
    @Inject
    MarketValueRepositoryService service;

    @GET
    @Path("/{asset}")
    public Collection<MarketValueEntry> list(@PathParam("asset") String asset, @QueryParam("data") LocalDate date) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", asset);
        StringBuilder filter = new StringBuilder("key.ativo.nome = :name");
        if (date != null) {
            filter.append(" and key.data = :data");
            params.put("data", date);
        }
        return this.service.find(filter.toString(), params)
                .stream()
                .map(this.service::fromEntity).collect(Collectors.toList());
    }

    @POST
    @PUT
    @Transactional
    public Response save(@Valid MarketValueEntry t) {
        MarketValue value = this.service.toEntity(t);
        this.service.update(value);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("id") String assetId, @QueryParam("data") LocalDate data) {
        if (data == null) {
            throw new BadRequestException("A data é obrigatória");
        }
        MarketValue resource = this.service.find("key.ativo.nome = ?1 and key.data = ?2", assetId, data).firstResult();
        if (resource != null && resource.isPersistent()) {
            this.service.delete(resource);
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
