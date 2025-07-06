package com.shade.enterprise.user.domain.port;

import com.shade.enterprise.user.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<User> findById(UUID id);

    User createUser(User user);

}
