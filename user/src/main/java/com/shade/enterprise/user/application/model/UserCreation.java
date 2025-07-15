package com.shade.enterprise.user.application.model;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
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
