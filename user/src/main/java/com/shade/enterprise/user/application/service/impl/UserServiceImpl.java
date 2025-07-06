package com.shade.enterprise.user.application.service.impl;

import com.shade.enterprise.user.application.model.UserCreation;
import com.shade.enterprise.user.application.service.UserService;
import com.shade.enterprise.user.domain.model.User;
import com.shade.enterprise.user.domain.port.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final Validator validator;

    @Inject
    public UserServiceImpl(UserRepository userRepository, Validator validator) {
        this.userRepository = userRepository;
        this.validator = validator;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User createUser(UserCreation userCreation) {
        User user = User.createUser(userCreation);
        return userRepository.createUser(user.validate(user, validator));
    }

}
