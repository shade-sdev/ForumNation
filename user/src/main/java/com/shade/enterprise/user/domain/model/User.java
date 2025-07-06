package com.shade.enterprise.user.domain.model;

import com.shade.enterprise.shared.domain.AuditData;
import com.shade.enterprise.shared.domain.DomainValidator;
import com.shade.enterprise.user.application.model.UserCreation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.UUID;

@Value
@EqualsAndHashCode(of = "id", callSuper = false)
@Builder(toBuilder = true)
public class User extends DomainValidator<User> {

    @NotNull
    @Default
    UUID id = UUID.randomUUID();

    @Default
    @NotNull
    @Valid
    Information information = Information.builder().build();

    @Default
    @NotNull
    @Valid
    Security security = Security.builder().build();

    @Default
    @NotNull
    @Valid
    AuditData auditData = AuditData.builder().build();

    Long version;

    public static User createUser(UserCreation userCreation) {
        return User.builder()
                   .information(Information.builder()
                                           .username(userCreation.getUsername())
                                           .email(userCreation.getEmail())
                                           .build())
                   .security(Security.builder()
                                     .password(userCreation.getPassword())
                                     .isTwoFactorEnabled(userCreation.getEnableTwoFactor())
                                     .build())
                   .build();
    }

    @Value
    @Builder(toBuilder = true)
    public static class Information {

        @NotBlank
        String username;

        @NotBlank
        @Email
        String email;

    }

    @Value
    @Builder(toBuilder = true)
    public static class Security {

        @NotBlank
        String password;

        boolean isAccountNonLocked;

        boolean isTwoFactorEnabled;

        String twoFactorSecretKey;

    }

}
