package com.shade.enterprise.user.infrastructure.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shade.enterprise.user.application.model.UserCreation;
import com.shade.enterprise.user.application.service.UserService;

import io.quarkus.arc.Unremovable;

import lombok.extern.jbosslog.JBossLog;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Unremovable
@JBossLog
public class UserExternalListener implements MessageListener {

    private final UserService userService;

    @Inject
    public UserExternalListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public void onMessage(Message message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            userService.createUser(mapper.readValue(message.getBody(String.class), UserCreation.class));
        } catch (JMSException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}