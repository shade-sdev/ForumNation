package com.shade.enterprise.shared.infrastructure.config.exception.handler;

import com.arjuna.ats.jta.exceptions.RollbackException;
import com.shade.enterprise.shared.infrastructure.config.exception.model.ProblemDetail;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.time.Instant;

import static org.jboss.resteasy.reactive.RestResponse.Status.BAD_REQUEST;
import static org.jboss.resteasy.reactive.RestResponse.Status.INTERNAL_SERVER_ERROR;

@ApplicationScoped
public class GlobalExceptionHandler {

    @Context
    private UriInfo uriInfo;

    @ServerExceptionMapper(priority = Priorities.AUTHENTICATION)
    public RestResponse<ProblemDetail> handleConstraintViolationException(ConstraintViolationException ex) {
        return RestResponse.status(BAD_REQUEST, ProblemDetail.builder()
                                                             .code("DB_DATA_VIOLATION")
                                                             .detail(ex.getSQLException().getMessage())
                                                             .path(uriInfo.getPath())
                                                             .timestamp(Instant.now())
                                                             .status(Response.Status.BAD_REQUEST.name())
                                                             .build());
    }

    @ServerExceptionMapper(priority = Priorities.AUTHENTICATION)
    public RestResponse<ProblemDetail> handleConstraintViolationException(jakarta.validation.ConstraintViolationException ex) {
        return RestResponse.status(BAD_REQUEST, ProblemDetail.builder()
                                                             .code("REQUEST_PARAM_VIOLATION")
                                                             .detail(ex.getMessage())
                                                             .path(uriInfo.getPath())
                                                             .timestamp(Instant.now())
                                                             .status(Response.Status.BAD_REQUEST.name())
                                                             .build());
    }

    @ServerExceptionMapper(priority = Priorities.AUTHENTICATION)
    public RestResponse<ProblemDetail> handleRollbackException(RollbackException ex) {
        return RestResponse.status(INTERNAL_SERVER_ERROR, ProblemDetail.builder()
                                                                       .code("ROLLBACK")
                                                                       .detail(ex.getMessage())
                                                                       .path(uriInfo.getPath())
                                                                       .timestamp(Instant.now())
                                                                       .status(Response.Status.INTERNAL_SERVER_ERROR.name())
                                                                       .build());
    }

}
