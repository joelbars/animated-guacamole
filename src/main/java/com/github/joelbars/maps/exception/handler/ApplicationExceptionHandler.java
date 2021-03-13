package com.github.joelbars.maps.exception.handler;

import org.jboss.resteasy.spi.ApplicationException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ApplicationExceptionHandler implements ExceptionMapper<ApplicationException> {

    @Override
    public Response toResponse(ApplicationException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(e.getCause().getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}
