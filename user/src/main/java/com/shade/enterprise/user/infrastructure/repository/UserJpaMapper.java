package com.shade.enterprise.user.infrastructure.repository;

import com.shade.enterprise.user.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface UserJpaMapper {

    @Mapping(target = "lastModifiedDate", source = "auditData.lastModifiedDate")
    @Mapping(target = "lastModifiedBy", source = "auditData.lastModifiedBy")
    @Mapping(target = "createdDate", source = "auditData.createdDate")
    @Mapping(target = "createdBy", source = "auditData.createdBy")
    @Mapping(target = "security.isTwoFactorEnabled", source = "security.twoFactorEnabled")
    @Mapping(target = "security.isAccountNonLocked", source = "security.accountNonLocked")
    UserJpaEntity mapToEntity(User user);

    @Mapping(target = "auditData.createdBy", source = "createdBy")
    @Mapping(target = "auditData.createdDate", source = "createdDate")
    @Mapping(target = "auditData.lastModifiedBy", source = "lastModifiedBy")
    @Mapping(target = "security.isTwoFactorEnabled", source = "security.twoFactorEnabled")
    @Mapping(target = "security.isAccountNonLocked", source = "security.accountNonLocked")
    User mapToDomain(UserJpaEntity user);

}
