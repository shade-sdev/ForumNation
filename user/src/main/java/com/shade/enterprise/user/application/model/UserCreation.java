package com.shade.enterprise.user.application.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

@Value
public class UserCreation {

    @NotBlank
    @Length(min = 1, max = 255)
    String username;

    @NotBlank
    @Length(min = 1, max = 255)
    @Email
    String email;

    @NotBlank
    @Length(min = 1, max = 255)
    String password;

    Boolean enableTwoFactor;

}
