package com.shade.enterprise.user.application.service;

import com.shade.enterprise.user.application.model.UserCreation;
import com.shade.enterprise.user.domain.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Optional<User> findById(UUID id);

    User createUser(@NotNull @Valid UserCreation userCreation);

}
