package com.shade.enterprise.shared.infrastructure.repository;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class ForumNationEntityListener {

    @PrePersist
    public void prePersist(Auditable entity) {
        LocalDateTime now = LocalDateTime.now();

        entity.setCreatedDate(now);
        entity.setCreatedBy("system");
        entity.setLastModifiedDate(now);
        entity.setLastModifiedBy("system");
    }

    @PreUpdate
    public void preUpdate(Auditable entity) {
        LocalDateTime now = LocalDateTime.now();

        entity.setLastModifiedDate(now);
        entity.setLastModifiedBy("system");
    }

}
