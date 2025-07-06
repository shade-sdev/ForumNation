package com.shade.enterprise.user.infrastructure.repository;

import com.shade.enterprise.shared.infrastructure.repository.Auditable;
import com.shade.enterprise.shared.infrastructure.repository.ForumNationEntityListener;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "user", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(ForumNationEntityListener.class)
public class UserJpaEntity extends Auditable implements Serializable {

    @Serial
    private static final long serialVersionUID = 1651525496623958646L;

    @Id
    @Column(name = "id")
    private UUID id;

    @Embedded
    private InformationJpaEntity information;

    @Embedded
    private SecurityJpaEntity security;

    @Version
    private Long version;

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder(toBuilder = true)
    @Getter
    public static class InformationJpaEntity implements Serializable {

        @Serial
        private static final long serialVersionUID = -6130702327953061733L;

        @Column(name = "username", unique = true)
        private String username;

        @Column(name = "email", unique = true)
        private String email;

    }

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder(toBuilder = true)
    @Getter
    public static class SecurityJpaEntity implements Serializable {

        @Serial
        private static final long serialVersionUID = 2590774225845285830L;

        @Column(name = "password")
        private String password;

        @Column(name = "is_account_non_locked")
        private boolean isAccountNonLocked;

        @Column(name = "is_two_factor_enabled")
        private boolean isTwoFactorEnabled;

        @Column(name = "two_factor_secret_key")
        private String twoFactorSecretKey;

    }

}
