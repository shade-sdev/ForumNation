package com.shade.enterprise.user.application.service.impl;

import java.util.Optional;
import java.util.UUID;

import com.shade.enterprise.user.application.model.UserCreation;
import com.shade.enterprise.user.application.service.UserService;
import com.shade.enterprise.user.domain.model.User;
import com.shade.enterprise.user.domain.port.UserRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSProducer;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final Validator validator;

    private final JMSContext jmsContext;

    private final JMSProducer jmsProducer;

    @Inject
    public UserServiceImpl(
            UserRepository userRepository,
            Validator validator,
            @Named("forumJmsContext") JMSContext jmsContext,
            @Named("forumJmsProducer") JMSProducer jmsProducer
    ) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.jmsContext = jmsContext;
        this.jmsProducer = jmsProducer;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User createUser(UserCreation userCreation) {
        //        jmsProducer.send(jmsContext.createQueue("users.queue"), "{\n" +
        //                "  \"username\": \"Shade1\",\n" +
        //                "  \"email\": \"shade@shade.ga1\",\n" +
        //                "  \"password\": \"fefef\",\n" +
        //                "  \"enableTwoFactor\": false\n" +
        //                "}");
        User user = User.createUser(userCreation);
        return userRepository.createUser(user.validate(user, validator));
    }

}
