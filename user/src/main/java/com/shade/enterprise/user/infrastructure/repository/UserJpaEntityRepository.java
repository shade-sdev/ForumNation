package com.shade.enterprise.user.infrastructure.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class UserJpaEntityRepository implements PanacheRepositoryBase<UserJpaEntity, UUID> {
}
