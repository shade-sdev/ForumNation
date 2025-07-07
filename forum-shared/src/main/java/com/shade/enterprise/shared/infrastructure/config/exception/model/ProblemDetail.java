package com.shade.enterprise.shared.infrastructure.config.exception.model;

import lombok.Builder;
import lombok.Value;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Value
@Builder
public class ProblemDetail implements Serializable {

    @Serial
    private static final long serialVersionUID = -1571042159768200056L;

    String code;

    String detail;

    String path;

    Instant timestamp;

    String status;

}