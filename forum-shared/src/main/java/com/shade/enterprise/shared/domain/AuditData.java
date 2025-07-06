package com.shade.enterprise.shared.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Value
public class AuditData {

    LocalDateTime createdDate;

    String createdBy;

    LocalDateTime lastModifiedDate;

    String lastModifiedBy;

}
