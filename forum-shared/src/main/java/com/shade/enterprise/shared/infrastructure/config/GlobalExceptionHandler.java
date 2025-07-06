package com.shade.enterprise.shared.infrastructure.config;

import com.arjuna.ats.jta.exceptions.RollbackException;
import io.quarkus.arc.ArcUndeclaredThrowableException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
import org.jboss.resteasy.reactive.server.UnwrapException;

import java.time.Instant;
import java.util.Map;

@ApplicationScoped
@UnwrapException({ArcUndeclaredThrowableException.class})
public class GlobalExceptionHandler {

    @Context
    private UriInfo uriInfo;

    @ServerExceptionMapper
    public Response handleConstraintViolationException(ConstraintViolationException ex) {
        return this.createResponse(400, "Data Contraint Violation", ex.getMessage());
    }

    @ServerExceptionMapper
    public Response handleNotFoundException(NotFoundException ex) {
        return this.createResponse(404, "NotFoundException", ex.getMessage());
    }

    @ServerExceptionMapper
    public Response handleIllegalArgumentException(IllegalArgumentException ex) {
        return this.createResponse(400, "IllegalArgumentException", ex.getMessage());
    }

    @ServerExceptionMapper
    public Response handleRollbackException(RollbackException ex) {
        return this.createResponse(500, "RollbackException", ex.getMessage());
    }

    private Response createResponse(int status, String error, String message) {
        return Response.status(status)
                       .entity(Map.of(
                               "error", error,
                               "message", message,
                               "timestamp", Instant.now().toString(),
                               "path", uriInfo.getPath()
                                     ))
                       .type(MediaType.APPLICATION_JSON)
                       .build();
    }

}
