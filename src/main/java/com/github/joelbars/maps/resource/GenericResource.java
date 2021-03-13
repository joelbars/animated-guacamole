package com.github.joelbars.maps.resource;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Collection;

public abstract class GenericResource<T extends PanacheEntityBase, Id> {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    protected PanacheRepositoryBase<T, Id> repository;

    @GET
    public Collection<T> list() {
        return this.repository.listAll();
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") Id id) {
        T type = this.repository.findById(id);
        if (type == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(type).build();
        }
    }

    @POST
    @Transactional
    public Response save(@Valid T t) throws Exception {
        this.repository.persist(t);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response update(@PathParam("id") Id id, @Valid T t) throws Exception {
        T resource = this.repository.findById(id);
        if (resource == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        T type = this.repository.getEntityManager().merge(t);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("id") Id id) throws Exception {
        T resource = this.repository.findById(id);
        if (resource != null && resource.isPersistent()) {
            this.repository.deleteById(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
